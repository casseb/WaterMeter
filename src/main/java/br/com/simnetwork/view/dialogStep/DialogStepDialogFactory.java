package br.com.simnetwork.view.dialogStep;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.debug.Debug;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.model.service.RotaService;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogFactory;
import br.com.simnetwork.view.DialogTypeFinish;

@Service("stepDialogFactory")
@Scope(value = "prototype")
public class DialogStepDialogFactory implements DialogStep {

	@Autowired
	DialogFactory dialogFactory;
	@Autowired
	RotaService rotaService;
	@Autowired
	Debug debug;

	private String rotaBean;

	private Map<String, String> opcoes = new HashMap<String, String>();
	private String condicao;
	@Autowired
	private Bot bot;

	@Override
	public void action(String botId, Dialog dialog) {

		try {

			String rotaGrupo;
			String rota;

			if (rotaBean != null) {
				debug.setMessage("DialogStepDialogFactory: Chamando rota estática");
				rotaGrupo = rotaService.localizarRotaByBean(rotaBean).getRotaPK().getRotaGrupo();
				rota = rotaService.localizarRotaByBean(rotaBean).getRotaPK().getNome();

			} else if (!opcoes.isEmpty()) {
				rotaGrupo = null;
				rota = null;

			} else {

				rotaGrupo = dialog.getComplements().getString("grupoRota");
				rota = dialog.getComplements().getString("rota");

			}

			dialog.setCurrentTypeFinish(DialogTypeFinish.FINALIZADOSTEP);

			if (rotaGrupo == null && rota == null) {
				dialogFactory.createDialog(opcoes.get(dialog.getComplements().get(condicao)),
						usuarioService.localizarUsuarioPorTelegram(botId));
			} else {
				dialogFactory.createDialog(rotaGrupo, rota, usuarioService.localizarUsuarioPorTelegram(botId));
			}

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

	public String getRotaBean() {
		return rotaBean;
	}

	public void setRotaBean(String rotaBean) {
		this.rotaBean = rotaBean;
	}

	public DialogFactory getDialogFactory() {
		return dialogFactory;
	}

	public void setDialogFactory(DialogFactory dialogFactory) {
		this.dialogFactory = dialogFactory;
	}

	public RotaService getRotaService() {
		return rotaService;
	}

	public void setRotaService(RotaService rotaService) {
		this.rotaService = rotaService;
	}

	public Map<String, String> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(Map<String, String> opcoes) {
		this.opcoes = opcoes;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

}
