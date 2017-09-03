package br.com.simnetwork.view;

import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.view.dialogStep.DialogStep;

public interface Dialog {
	
	public boolean action(String botId, String mensagemUsuario);
	
	public DialogTypeFinish getCurrentDialogTypeFinish();
	
	public JSONObject getComplements();
	
	public void setComplements(JSONObject complements);
	
	public void cleanSteps();
	
	public void setCurrentTypeFinish(DialogTypeFinish currentTypeFinish);
	
	public String getMensagemUsuario();
	
	public Map<Integer, DialogStep> getSteps();
	
	public int getCurrentStep();
	
	public void setMensagemUsuario(String mensagemUsuario);
	
}
