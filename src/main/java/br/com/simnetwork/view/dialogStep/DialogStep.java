package br.com.simnetwork.view.dialogStep;

import org.json.JSONObject;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.DialogTypeFinish;

public interface DialogStep {

	public Bot bot = App.getCon().getBean("telegramBot",Bot.class);
	
	public Object action(Usuario usuario, String mensagemUsuario, DialogTypeFinish currentDialogTypeFinish, JSONObject complements);

}
