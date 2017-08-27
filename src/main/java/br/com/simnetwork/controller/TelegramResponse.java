package br.com.simnetwork.controller;

import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.view.DialogsActivated;

import org.json.JSONObject;
import java.io.*;

public class TelegramResponse {

	public TelegramResponse() {

	}

	public void readMessage(byte[] body) throws IOException {

		DialogsActivated dialogActived = App.getCon().getBean("dialogActived",DialogsActivated.class);
		

		try {

			String responseJSON = new String(body, "UTF-8");
			JSONObject telegramMessage = new JSONObject(responseJSON);

			String botId = Integer
					.toString(telegramMessage.getJSONObject("message").getJSONObject("chat").getInt("id"));
			String mensagemUsuarioString = telegramMessage.getJSONObject("message").getString("text");

			
			dialogActived.prepareDialogActived();
			
			dialogActived.executeDialog(botId, mensagemUsuarioString);
			/*
			 * 
			 * DialogGenerator dialogGenerator = new DialogGenerator(telegramMessage, bot,
			 * admin); List<Dialog> trash = new LinkedList<>(); Dialog newDialog = null;
			 * create = true;
			 * 
			 * String message = "Sem texto";
			 * if(telegramMessage.getJSONObject("message").has("text")){ message =
			 * telegramMessage.getJSONObject("message").getString("text"); }
			 * 
			 * String idTelegram = null;
			 * if(telegramMessage.getJSONObject("message").getJSONObject("chat").has("id")){
			 * idTelegram =
			 * Integer.toString(telegramMessage.getJSONObject("message").getJSONObject(
			 * "chat").getInt("id")); }
			 * 
			 * 
			 * 
			 * for (Dialog dialog : currentDialogs) { dialog.setMessage(message);
			 * dialog.quit(); if(dialog.isFinish()){ trash.add(dialog); }else{
			 * if(dialog.getPerson().getIdTelegram().equals(idTelegram)){ if(message !=
			 * null){ if(message.equals("Voltar")){ dialog.backStep(); } }
			 * if(dialog.isNeedAFile()){
			 * if(telegramMessage.getJSONObject("message").has("document")){
			 * if(dialog.fileName != null){ dialog.setBoxFile(
			 * telegramMessage.getJSONObject("message").getJSONObject("document").getString(
			 * "file_id"), dialog.fileName ); dialog.fileName = null; }else{
			 * dialog.setBoxFile(
			 * telegramMessage.getJSONObject("message").getJSONObject("document").getString(
			 * "file_id"),
			 * telegramMessage.getJSONObject("message").getJSONObject("document").getString(
			 * "file_name") ); } }
			 * if(telegramMessage.getJSONObject("message").has("photo")){ if(dialog.fileName
			 * != null){ dialog.setBoxFile(
			 * telegramMessage.getJSONObject("message").getJSONArray("photo").getJSONObject(
			 * 2).getString("file_id"), dialog.fileName+".jpg" ); dialog.fileName = null;
			 * }else{ dialog.setBoxFile(
			 * telegramMessage.getJSONObject("message").getJSONArray("photo").getJSONObject(
			 * 2).getString("file_id"),
			 * telegramMessage.getJSONObject("message").getJSONArray("photo").getJSONObject(
			 * 2).getString("file_id")+".jpg" ); } }
			 * if(telegramMessage.getJSONObject("message").has("video")){ if(dialog.fileName
			 * != null){ dialog.setBoxFile(
			 * telegramMessage.getJSONObject("message").getJSONObject("video").getString(
			 * "file_id"), dialog.fileName+".avi" ); dialog.fileName = null; }else{
			 * dialog.setBoxFile(
			 * telegramMessage.getJSONObject("message").getJSONObject("video").getString(
			 * "file_id"),
			 * telegramMessage.getJSONObject("message").getJSONObject("video").getString(
			 * "file_id")+".avi" ); }
			 * 
			 * } dialog.setNeedAFile(false); }else{ dialog.resetBoxFile(); }
			 * model.addMessageLog(new LogMensagem(dialog.getPerson(),
			 * message,"Enviado pelo Usuário")); newDialog = dialog.action(); create =
			 * false; } } }
			 * 
			 * if(newDialog != null){ currentDialogs.add(newDialog); newDialog.action(); }
			 * 
			 * 
			 * if(create){ currentDialog = dialogGenerator.create();
			 * if(currentDialog!=null){ currentDialogs.add(currentDialog);
			 * model.addMessageLog(new LogMensagem(currentDialog.getPerson(),
			 * message,"Enviado pelo Usuário")); currentDialog.action(); } }
			 * 
			 * currentDialogs.removeAll(trash);
			 * 
			 */

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			/*
			 * bot.execute(new SendMessage(admin,ex.getMessage())); model.addMessageLog(new
			 * LogMensagem(currentDialog.getPerson(), ex.getMessage(),"Erro da Aplicação"));
			 */
		}

	}

}
