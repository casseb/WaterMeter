package br.com.simnetwork.view.dialogStep;

import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.RotaService;
import br.com.simnetwork.model.service.UsuarioService;
import br.com.simnetwork.view.Dialog;

public interface DialogStep {

	public UsuarioService usuarioService = App.getCon().getBean("usuarioService",UsuarioService.class);
	public RotaService rotaService = App.getCon().getBean("rotaService",RotaService.class);
	
	public void action(String BotId, Dialog dialog);

}
