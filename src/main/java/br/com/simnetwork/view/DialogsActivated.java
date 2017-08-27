package br.com.simnetwork.view;

public interface DialogsActivated {
	
	public void prepareDialogActived(String mensagemUsuario);
	
	public DialogTypeFinish getDialogStatus(String botId);
	
	public void setDialogActived(String botId, Dialog dialog);
	
	public void removeDialogActived(String id);

	public void executeDialog(String botId, String mensagemUsuario);
	

}
