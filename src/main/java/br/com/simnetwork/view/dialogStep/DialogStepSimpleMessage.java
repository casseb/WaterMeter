package br.com.simnetwork.view.dialogStep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;

@Service("stepSimpleMessage")
public class DialogStepSimpleMessage implements DialogStep {
	
	public String mensagemBot;
	@Autowired
	Bot bot;

	
	@Override
	public void action(String botId,Dialog dialog) {
		
		try {
			bot.sendMessage(botId, mensagemBot);
			dialog.setCurrentTypeFinish(DialogTypeFinish.FINALIZADOSTEP);
			
		} catch (Exception e) {
			Acesso access = App.getCon().getBean("access", Acesso.class);
			if (usuarioService.localizarUsuarioPorTelegram(botId) != null) {
				bot.sendMessage(botId, "Ocorreu algo inesperado, peço que tente novamente e contate o administrador do sistema.");
				bot.sendMessage(access.getAdminTelegram(), "Ocorreu o seguinte erro para o usuário: "
						+ usuarioService.localizarUsuarioPorTelegram(botId).getApelido() + " \n" + e.getMessage());
			} else {
				bot.sendMessage(access.getAdminTelegram(),
						"Ocorreu o seguinte erro (não foi possivel saber o usuário): " + e.getMessage());
			}
			
		}
		
	}


	public String getMensagemBot() {
		return mensagemBot;
	}

	public void setMensagemBot(String mensagemBot) {
		this.mensagemBot = mensagemBot;
	}
	
	

}
