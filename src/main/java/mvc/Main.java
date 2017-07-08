package mvc;

import static spark.Spark.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

import access.AccessConfiguration;
import dialogs.Dialog;
import dialogs.DialogEditLogin;
import objects.Person;
import objects.PersonType;
import objects.Project;
import objects.ProjectType;
import objects.Route;
import objects.RouteGroup;
import objects.ScheduleMessage;

public class Main {
	
	final static Model model = new Model();
	final static AccessConfiguration access = new AccessConfiguration();
	static String token = access.getTokenTelegram();
	static TelegramBot bot = TelegramBotAdapter.build(token);
	
	
	public static void main(String[] args) {
		
		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);
				
        TelegramResponse api = new TelegramResponse(model);
        REST rest = new REST(model);
        
        post("/readMessages", (req, res) -> {
            api.readMessage(req.bodyAsBytes());
            return "Success";
        });
        
		rest.login();
		rest.getTermos();
		
		permissaoADM();
		triggerMessages();
		
    }



	private static void triggerMessages() {
		List<ScheduleMessage> scheduleMessages = model.scheduleMessages;
		for (ScheduleMessage scheduleMessage : scheduleMessages) {
			model.schedule.schedule(() -> {
					bot.execute(new SendMessage(scheduleMessage.getPerson().getIdTelegram(),scheduleMessage.getMessage()));
					model.removeScheduleMessage(scheduleMessage);
				},LocalDateTime.now().until(scheduleMessage.getHora(), ChronoUnit.MILLIS),TimeUnit.MILLISECONDS);
		}
		
	}



	private static void permissaoADM() {
		Person person = model.locateTelegramUser(access.getAdminTelegram());
		if(person == null){
			person = model.addPersonByTelegram(access.getAdminTelegram());
			person.setName("Adm");
			person.setSenha("123");
			person.setPersonType(PersonType.PARCEIRO);
			model.editPerson(person);
		}
		if(!model.havePermission(model.locateRoute("Administrativo - Dar Permissão"), person)){
			model.grandPermission(person, RouteGroup.ADMINISTRATIVO);
		}
		
	}
	
	
	
	
	
	
	
}
