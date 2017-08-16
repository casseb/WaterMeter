package br.com.simnetwork;

import static spark.Spark.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.access.AccessConfiguration;
import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.ScheduleMessage;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.person.PersonTipo;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.basic.route.RouteGroup;
import br.com.simnetwork.model.files.BoxFileObject;
import br.com.simnetwork.model.files.BoxFolderObject;
import br.com.simnetwork.model.project.Project;
import br.com.simnetwork.model.project.ProjectType;
import br.com.simnetwork.spring.ApplicationContext;
import br.com.simnetwork.view.basic.Dialog;
import br.com.simnetwork.view.basic.users.DialogEditApelido;

public class Main {
	
	final static Model model = (Model) ApplicationContext.getContext().getBean("model");
	final static AccessConfiguration access = (AccessConfiguration) ApplicationContext.getContext().getBean("access");
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
				
        TelegramResponse api = new TelegramResponse();
        REST rest = new REST(model);
        
        post("/readMessages", (req, res) -> {
            api.readMessage(req.bodyAsBytes());
            return "Success";
        });
        
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
			person.setApelido("Adm");
			person.setPersonType(PersonTipo.PARCEIRO);
			model.editPerson(person);
		}
		if(!model.havePermission(model.locateRoute("Administrativo - Dar Permissão"), person)){
			model.grandPermission(person, RouteGroup.ADMINISTRATIVO);
		}
		
	}
	
}
