package mvc;

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

import access.AccessConfiguration;
import dialogs.basic.structure.Dialog;
import dialogs.basic.users.DialogEditLogin;
import objects.Project;
import objects.ProjectType;
import objects.basic.Person;
import objects.basic.PersonType;
import objects.basic.Route;
import objects.basic.RouteGroup;
import objects.basic.ScheduleMessage;
import objects.files.BoxFileObject;
import objects.files.BoxFolderObject;

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
		testCreatePDF();
		
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
	
	private static void testCreatePDF() {
		try {
			Document document = new Document();
			File file = new File("src/main/java/objects/basic/tempFiles/PDF_DevMedia.pdf");
			OutputStream fileOut = new FileOutputStream(file);

			Font font20 = new Font();
			font20.setSize(20);
			Font fontbold = new Font();
			fontbold.setStyle(fontbold.BOLD);

			Paragraph paragraph1 = new Paragraph();
			paragraph1.add(new Chunk("Teste1", font20));
			Paragraph paragraph2 = new Paragraph();
			paragraph2.add(new Chunk("Teste2", fontbold));
			Paragraph paragraph3 = new Paragraph();
			paragraph3.add("Teste3\n");
			
			PdfWriter.getInstance(document, fileOut);
			document.open();
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph3);
			document.close();
			
			List<String> folder = new LinkedList();
			folder.add("Pasta1");
			folder.add("Pasta2");
			BoxFolderObject currentFolder = null;
			BoxFolderObject rootFolder = null;

			for (String string : folder) {
				currentFolder = model.locateBoxFolderObjectByName(string);
				if (currentFolder == null) {
					currentFolder = new BoxFolderObject(string, model, rootFolder);
				}
				rootFolder = currentFolder;
			}
			
			model.addBoxFileObject(new BoxFileObject("Teste1",model,file,currentFolder));
			
			System.out.println("Finalizado");
			

		} catch (Exception de) {
			System.err.println(de.getMessage());
		} 

	}
	
	
	
	
	
}
