package br.com.simnetwork.view.dialogStep;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.model.service.RotaService;
import br.com.simnetwork.model.service.UsuarioService;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;
import br.com.simnetwork.view.DialogsActivated;

public interface DialogStep {

	public UsuarioService usuarioService = App.getCon().getBean("usuarioService",UsuarioService.class);
	public RotaService rotaService = App.getCon().getBean("rotaService",RotaService.class);
	
	public void action(String BotId, Dialog dialog);

}
