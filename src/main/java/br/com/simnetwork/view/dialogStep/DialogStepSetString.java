package br.com.simnetwork.view.dialogStep;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.basico.validacao.Validacao;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;

@Service("stepSetString")
@Scope(value = "prototype")
public class DialogStepSetString implements DialogStep {

	public String mensagemBot;
	public List<Validacao> validacoes = new LinkedList<>();
	public String chaveDado;
	@Autowired
	private Bot bot;

	@SuppressWarnings("static-access")
	@Override
	public void action(String botId, Dialog dialog) {

		try {
			
			if (dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.AGUARDANDODADO)) {
				for (Validacao validacao : validacoes) {
					if (!validacao.eValido(dialog.getMensagemUsuario())) {
						bot.sendMessage(botId, validacao.getInvalidMessage());
						dialog.setCurrentTypeFinish(DialogTypeFinish.CONTEUDOINVALIDO);
					}
				}

				dialog.getComplements().put(chaveDado, dialog.getMensagemUsuario());

			}

			if (dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.INICIOSTEP)
					|| dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.CONTEUDOINVALIDO)) {
				bot.sendMessage(botId, mensagemBot);
				dialog.setCurrentTypeFinish(DialogTypeFinish.AGUARDANDODADO);
			}

			dialog.action(botId, dialog.getMensagemUsuario());

		} catch (Exception e) {
			Acesso access = App.getCon().getBean("access", Acesso.class);
			if (usuarioService.localizarUsuarioPorTelegram(botId) != null) {
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

	public List<Validacao> getValidacoes() {
		return validacoes;
	}

	public void setValidacoes(List<Validacao> validacoes) {
		this.validacoes = validacoes;
	}

	public String getChaveDado() {
		return chaveDado;
	}

	public void setChaveDado(String chaveDado) {
		this.chaveDado = chaveDado;
	}

}
