package br.com.simnetwork.view.dialogStep;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;

@Service("stepConfirmation")
@Scope(value = "prototype")
public class DialogStepConfimation implements DialogStep {

	public StringBuilder mensagemBot = new StringBuilder();
	private Map<String, String> labels = new TreeMap<String, String>();
	@Autowired
	private Bot bot;

	@Override
	public void action(String botId, Dialog dialog) {

		try {

			if (dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.AGUARDANDODADO)) {

				if (dialog.getMensagemUsuario().equals("Sim")) {
					if (dialog.getCustomTable().equals("Usuario")) {
						usuarioService.criarUsuario(botId, dialog.getComplements().getString("usu_apelido"));
						
					}
					if (dialog.getCustomTable().equals("UsuarioRota")) {
						usuarioService.darPermissao(usuarioService.localizarUsuarioPorApelido(dialog.getComplements().getString("usuario")),
								rotaService.pesquisarPorPK(dialog.getComplements().getString("grupoRota"),
										dialog.getComplements().getString("rota")));
					}
					bot.sendMessage(botId, "Cadastro realizado com sucesso");
					dialog.setCurrentTypeFinish(DialogTypeFinish.CONFIRMADO);
				} else {
					dialog.setCurrentTypeFinish(DialogTypeFinish.CANCELADO);
				}

			}

			if (dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.INICIOSTEP)
					|| dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.CONTEUDOINVALIDO)) {

				mensagemBot.append("Deseja confirmar a gravação dos seguintes dados?\n\n");

				for (String complementKey : dialog.getComplements().keySet()) {
					mensagemBot.append(
							labels.get(complementKey) + " : " + dialog.getComplements().getString(complementKey)+"\n");
				}

				List<String> keyboard = new LinkedList<>();
				keyboard.add("Sim");
				keyboard.add("Não");

				bot.prepareKeyboard(keyboard);

				bot.sendMessage(botId, mensagemBot.toString());
				dialog.setCurrentTypeFinish(DialogTypeFinish.AGUARDANDODADO);
			}

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

	public StringBuilder getMensagemBot() {
		return mensagemBot;
	}

	public void setMensagemBot(StringBuilder mensagemBot) {
		this.mensagemBot = mensagemBot;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

}
