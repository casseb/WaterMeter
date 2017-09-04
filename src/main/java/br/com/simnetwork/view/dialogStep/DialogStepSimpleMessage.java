package br.com.simnetwork.view.dialogStep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;
import br.com.simnetwork.view.dynamicMessage.DynamicMessage;

@Service("stepSimpleMessage")
@Scope("prototype")
public class DialogStepSimpleMessage implements DialogStep {

	public String mensagemBot;
	@Autowired
	Bot bot;
	private DynamicMessage dynamicMessage;
	public String dependenciasMensagemDinamica;

	@Override
	public void action(String botId, Dialog dialog) {

		try {
			if (dynamicMessage != null) {
				if (dependenciasMensagemDinamica.equals("usuario")) {
					dynamicMessage.prepareMessage(
							usuarioService.localizarUsuarioPorApelido(dialog.getComplements().getString("usuario")));
				}
				bot.sendMessage(botId, dynamicMessage.getMessage());
			} else {
				bot.sendMessage(botId, mensagemBot);
			}

			dialog.setCurrentTypeFinish(DialogTypeFinish.FINALIZADOSTEP);

		} catch (Exception e) {
			Acesso access = App.getCon().getBean("access", Acesso.class);
			if (usuarioService.localizarUsuarioPorTelegram(botId) != null) {
				bot.sendMessage(botId,
						"Ocorreu algo inesperado, peço que tente novamente e contate o administrador do sistema.");
				bot.sendMessage(access.getAdminTelegram(), "Ocorreu o seguinte erro para o usuário: "
						+ usuarioService.localizarUsuarioPorTelegram(botId).getApelido() + " \n" + e.getMessage());
			} else {
				bot.sendMessage(access.getAdminTelegram(),
						"Ocorreu o seguinte erro (não foi possivel saber o usuário): " + e.getMessage());
			}

		}

	}

	public DynamicMessage getDynamicMessage() {
		return dynamicMessage;
	}

	public void setDynamicMessage(DynamicMessage dynamicMessage) {
		this.dynamicMessage = dynamicMessage;
	}

	public String getMensagemBot() {
		return mensagemBot;
	}

	public void setMensagemBot(String mensagemBot) {
		this.mensagemBot = mensagemBot;
	}

	public String getDependenciasMensagemDinamica() {
		return dependenciasMensagemDinamica;
	}

	public void setDependenciasMensagemDinamica(String dependenciasMensagemDinamica) {
		this.dependenciasMensagemDinamica = dependenciasMensagemDinamica;
	}

}
