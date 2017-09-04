package br.com.simnetwork.view.dialogStep;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.debug.Debug;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.Bot;
import br.com.simnetwork.view.Dialog;
import br.com.simnetwork.view.DialogTypeFinish;
import br.com.simnetwork.view.dynamicList.DynamicList;

@Service("stepSetList")
@Scope(value = "prototype")
public class DialogStepSetList implements DialogStep {

	public String mensagemBot;
	public String chaveDado;
	public List<String> listaEstatica = new LinkedList<String>();
	public DynamicList listaDinamica;
	public String dependenciasListaDinamica;
	@Autowired
	private Debug debug;
	@Autowired
	private Bot bot;

	@Override
	public void action(String botId, Dialog dialog) {

		try {

			if (dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.AGUARDANDODADO)) {

				if (listaDinamica != null) {
					if (!listaDinamica.getList().contains(dialog.getMensagemUsuario())) {
						dialog.setCurrentTypeFinish(DialogTypeFinish.CONTEUDOINVALIDO);
					}
				}

				if (!listaEstatica.isEmpty() && !listaEstatica.contains(dialog.getMensagemUsuario())) {
					dialog.setCurrentTypeFinish(DialogTypeFinish.CONTEUDOINVALIDO);
				}

				if (!dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.CONTEUDOINVALIDO)) {
					dialog.getComplements().put(chaveDado, dialog.getMensagemUsuario());
					dialog.setCurrentTypeFinish(DialogTypeFinish.FINALIZADOSTEP);
				} else {
					bot.sendMessage(botId,
							"Foi utilizado uma opção não presente na lista, peço que utilize uma das opções abaixo.");
				}

			}

			if (dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.INICIOSTEP)
					|| dialog.getCurrentDialogTypeFinish().equals(DialogTypeFinish.CONTEUDOINVALIDO)) {

				if (!listaEstatica.isEmpty()) {
					debug.setMessage("DialogStepSetList: Preparando lista estática");
					bot.prepareKeyboard(listaEstatica);
					bot.sendMessage(botId, mensagemBot);
					dialog.setCurrentTypeFinish(DialogTypeFinish.AGUARDANDODADO);
				} else {
					if (dependenciasListaDinamica.equals("listGrupoRota")) {
						listaDinamica.prepareList(usuarioService.localizarUsuarioPorTelegram(botId));
					}
					if (dependenciasListaDinamica.equals("listGrupoRotaOutroUsuario")) {
						listaDinamica.prepareList(usuarioService
								.localizarUsuarioPorApelido(dialog.getComplements().getString("usuario")));
					}
					if (dependenciasListaDinamica.equals("listRota")) {
						listaDinamica.prepareList(usuarioService.localizarUsuarioPorTelegram(botId),
								dialog.getComplements().get("grupoRota"));
					}
					if (dependenciasListaDinamica.equals("listRotaOutroUsuario")) {
						listaDinamica.prepareList(
								usuarioService.localizarUsuarioPorApelido(dialog.getComplements().getString("usuario")),
								dialog.getComplements().get("grupoRota"));
					}
					if (dependenciasListaDinamica.equals("semAtributo")) {
						listaDinamica.prepareList();
					}

					if (listaDinamica.getList().isEmpty()) {
						bot.sendMessage(botId, "Nenhuma opção disponível");
						dialog.setCurrentTypeFinish(DialogTypeFinish.FINALIZADO);
					} else {
						bot.prepareKeyboard(listaDinamica.getList());
						bot.sendMessage(botId, mensagemBot);
						dialog.setCurrentTypeFinish(DialogTypeFinish.AGUARDANDODADO);
					}

				}

			}
		}

		catch (Exception e) {
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

	public String getMensagemBot() {
		return mensagemBot;
	}

	public void setMensagemBot(String mensagemBot) {
		this.mensagemBot = mensagemBot;
	}

	public String getChaveDado() {
		return chaveDado;
	}

	public void setChaveDado(String chaveDado) {
		this.chaveDado = chaveDado;
	}

	public List<String> getLista() {
		return listaEstatica;
	}

	public void setLista(List<String> lista) {
		this.listaEstatica = lista;
	}

	public List<String> getListaEstatica() {
		return listaEstatica;
	}

	public void setListaEstatica(List<String> listaEstatica) {
		this.listaEstatica = listaEstatica;
	}

	public DynamicList getListaDinamica() {
		return listaDinamica;
	}

	public void setListaDinamica(DynamicList listaDinamica) {
		this.listaDinamica = listaDinamica;
	}

	public String getDependenciasListaDinamica() {
		return dependenciasListaDinamica;
	}

	public void setDependenciasListaDinamica(String dependenciasListaDinamica) {
		this.dependenciasListaDinamica = dependenciasListaDinamica;
	}

}
