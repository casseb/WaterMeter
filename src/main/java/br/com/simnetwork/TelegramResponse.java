package br.com.simnetwork;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;

import br.com.simnetwork.access.AccessConfiguration;
import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.MessageLog;
import br.com.simnetwork.spring.ApplicationContext;
import br.com.simnetwork.view.basic.Dialog;
import br.com.simnetwork.view.basic.DialogGenerator;
import br.com.simnetwork.view.basic.users.DialogCommands;

import org.eclipse.jetty.server.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TelegramResponse {

	Model model = (Model) ApplicationContext.getContext().getBean("model");
	AccessConfiguration access = (AccessConfiguration) ApplicationContext.getContext().getBean("access");
	
	String token = access.getTokenTelegram();
	TelegramBot bot = TelegramBotAdapter.build(token);
	
	List<Dialog>  currentDialogs = new LinkedList<>();
	String admin = access.getAdminTelegram();
	Dialog currentDialog = null;
	boolean create = true;

	public TelegramResponse() {
		
	}

	public void readMessage(byte[] body) throws IOException  {

    	
        try {
 	
        	String responseJSON = new String(body, "UTF-8");
        	JSONObject telegramMessage = new JSONObject(responseJSON);       	
        	DialogGenerator dialogGenerator = new DialogGenerator(telegramMessage, bot, admin);
        	List<Dialog> trash = new LinkedList<>();
        	Dialog newDialog = null;
        	create = true;
        	
        	String message = "Sem texto";
        	if(telegramMessage.getJSONObject("message").has("text")){
        		message = telegramMessage.getJSONObject("message").getString("text");
        	}
        	
        	String idTelegram = null;
        	if(telegramMessage.getJSONObject("message").getJSONObject("chat").has("id")){
        		idTelegram = Integer.toString(telegramMessage.getJSONObject("message").getJSONObject("chat").getInt("id"));
        	}
        	
        	
        	
        	for (Dialog dialog : currentDialogs) {
        		dialog.setMessage(message);
            	dialog.quit();
        		if(dialog.isFinish()){
					trash.add(dialog);
				}else{
					if(dialog.getPerson().getIdTelegram().equals(idTelegram)){
	        			if(message != null){
	        				if(message.equals("Voltar")){
		        				dialog.backStep();
		        			}
	        			}
						if(dialog.isNeedAFile()){
		        			if(telegramMessage.getJSONObject("message").has("document")){
								if(dialog.fileName != null){
									dialog.setBoxFile(
											telegramMessage.getJSONObject("message").getJSONObject("document").getString("file_id"),
											dialog.fileName
											);
									dialog.fileName = null;
								}else{
									dialog.setBoxFile(
											telegramMessage.getJSONObject("message").getJSONObject("document").getString("file_id"),
											telegramMessage.getJSONObject("message").getJSONObject("document").getString("file_name")
											);
								}	
							}
		        			if(telegramMessage.getJSONObject("message").has("photo")){
		        				if(dialog.fileName != null){
		        					dialog.setBoxFile(
				        					telegramMessage.getJSONObject("message").getJSONArray("photo").getJSONObject(2).getString("file_id"),
				        					dialog.fileName+".jpg"
											);
									dialog.fileName = null;	
		        				}else{
		        					dialog.setBoxFile(
		        					telegramMessage.getJSONObject("message").getJSONArray("photo").getJSONObject(2).getString("file_id"),
									telegramMessage.getJSONObject("message").getJSONArray("photo").getJSONObject(2).getString("file_id")+".jpg"
									);
		        				}
							}
		        			if(telegramMessage.getJSONObject("message").has("video")){
		        				if(dialog.fileName != null){
		        					dialog.setBoxFile(
											telegramMessage.getJSONObject("message").getJSONObject("video").getString("file_id"),
											dialog.fileName+".avi"
		        							);
									dialog.fileName = null;	
		        				}else{
		        					dialog.setBoxFile(
											telegramMessage.getJSONObject("message").getJSONObject("video").getString("file_id"),
											telegramMessage.getJSONObject("message").getJSONObject("video").getString("file_id")+".avi"
											);
		        				}
		        				
							}
		        			dialog.setNeedAFile(false);
						}else{
							dialog.resetBoxFile();
						}
						model.addMessageLog(new MessageLog(dialog.getPerson(), message,"Enviado pelo Usuário"));
						newDialog = dialog.action();
						create = false;
					}	
				}
        	}

        	if(newDialog != null){
				currentDialogs.add(newDialog);
				newDialog.action();
			}
        	
        	
        	if(create){
        		currentDialog = dialogGenerator.create();
            	if(currentDialog!=null){
            		currentDialogs.add(currentDialog);
            		model.addMessageLog(new MessageLog(currentDialog.getPerson(), message,"Enviado pelo Usuário"));
            		currentDialog.action();
            	}
        	}
        	
        	currentDialogs.removeAll(trash);
        	
        	

        } catch (Exception ex) {
        	bot.execute(new SendMessage(admin,ex.getMessage()));
        	model.addMessageLog(new MessageLog(currentDialog.getPerson(), ex.getMessage(),"Erro da Aplicação"));
        }
        
        
        
       
    }

	
	

}
