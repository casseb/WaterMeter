package br.com.simnetwork.controller;

import com.mchange.v2.collection.MapEntry;
import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.LogMensagem;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.model.service.UsuarioService;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;

import org.eclipse.jetty.server.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TelegramResponse {

	
	/*
	
	Model model = (Model) ApplicationContext.getContext().getBean("model");
	Acesso access = (Acesso) ApplicationContext.getContext().getBean("access");
	
	String token = access.getTokenTelegram();
	TelegramBot bot = TelegramBotAdapter.build(token);
	
	List<Dialog>  currentDialogs = new LinkedList<>();
	String admin = access.getAdminTelegram();
	Dialog currentDialog = null;
	boolean create = true;

	*/
	
	private Map<String,Dialog> dialogsActivated = new HashMap<String,Dialog>();
	
	public TelegramResponse() {
		
	}

	public void readMessage(byte[] body) throws IOException  {
		
		
		boolean executed = false;
		boolean repeat = false;
		
    	
        try {
 	
        	String responseJSON = new String(body, "UTF-8");
        	JSONObject telegramMessage = new JSONObject(responseJSON);   
        	
        	String botId = Integer.toString(telegramMessage.getJSONObject("message").getJSONObject("chat").getInt("id"));
        	String mensagemUsuario = telegramMessage.getJSONObject("message").getString("text");
        	
        	Bot bot = App.getCon().getBean("telegramBot",Bot.class);
        	UsuarioService usuarioService = App.getCon().getBean("usuarioService",UsuarioService.class);
        	
        	for (Map.Entry<String,Dialog> dialogActivated : dialogsActivated.entrySet()) {
        		if((dialogActivated.getValue().getCurrentDialogTypeFinish().equals(DialogTypeFinish.ERRO)) ||
        		(dialogActivated.getValue().getCurrentDialogTypeFinish().equals(DialogTypeFinish.FINALIZADO))
        		) {
        			dialogsActivated.remove(botId);
        		}else {
        			if(dialogActivated.getKey().equals(botId)) {
        				Usuario usuarioTemp = App.getCon().getBean("usuarioService",UsuarioService.class).localizarUsuarioPorTelegram(botId);
        				if(usuarioTemp == null) {
        					usuarioTemp = new Usuario();
                    		usuarioTemp.setBotId(botId);
        				}
        				do {
                			DialogTypeFinish typeFinish = dialogActivated.getValue().action(usuarioTemp,mensagemUsuario);
        					if(typeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {
                				repeat = true;
                			}else {
                				repeat = false;
                			}
    					} while (repeat);
    					executed = true;
    				}
        		}
			}
        	
        	if(!executed) {
        		Usuario usuario = App.getCon().getBean("usuarioService",UsuarioService.class).validarNovoUsuario(botId);
            	if(usuario == null) {
            		usuario = new Usuario();
            		usuario.setBotId(botId);
            		Dialog dialog = App.getCon().getBean("Meus Dados - Inicio",Dialog.class);
            		
            		do {
            			DialogTypeFinish typeFinish = dialog.action(usuario,mensagemUsuario);
            			if(typeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {
            				repeat = true;
            			}else {
            				repeat = false;
            			}
					} while (repeat);
            		
            		dialogsActivated.put(botId,dialog);
            	}else {
            		Dialog dialog = App.getCon().getBean("Teste - Teste",Dialog.class);
            		dialog.action(usuario,mensagemUsuario);
            	}
        	}
        	
        	
        	/*
        	
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
						model.addMessageLog(new LogMensagem(dialog.getPerson(), message,"Enviado pelo Usuário"));
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
            		model.addMessageLog(new LogMensagem(currentDialog.getPerson(), message,"Enviado pelo Usuário"));
            		currentDialog.action();
            	}
        	}
        	
        	currentDialogs.removeAll(trash);
        	
        	*/

        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        	/*
        	bot.execute(new SendMessage(admin,ex.getMessage()));
        	model.addMessageLog(new LogMensagem(currentDialog.getPerson(), ex.getMessage(),"Erro da Aplicação"));
        	*/
        }
        
        
        
       
    }

	
	

}
