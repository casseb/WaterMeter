package br.com.simnetwork.view.dialogStep;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.view.DialogTypeFinish;
import br.com.simnetwork.view.dynamicList.DynamicList;

@Service("stepSetList")
public class DialogStepSetList implements DialogStep {

	public String mensagemBot;
	public String chaveDado;
	public List<String> listaEstatica = new LinkedList<>();
	public DynamicList listaDinamica;
	public String dependenciasListaDinamica;

	@SuppressWarnings("static-access")
	@Override
	public Object action(Usuario usuario, String mensagemUsuario,DialogTypeFinish currentDialogTypeFinish, JSONObject complement) {

		try {
			
			if(currentDialogTypeFinish.equals(DialogTypeFinish.INICIOSTEP) ||
					currentDialogTypeFinish.equals(DialogTypeFinish.CONTEUDOINVALIDO)) {
				
				if(!listaEstatica.isEmpty()) {
					bot.prepareKeyboard(listaEstatica);
				}else {
					if(dependenciasListaDinamica.equals("listGrupoRota")) {
						listaDinamica.prepareList(usuario);
					}
					if(dependenciasListaDinamica.equals("listRota")) {
						listaDinamica.prepareList(usuario,complement.get("grupoRota"));
					}
					bot.prepareKeyboard(listaDinamica.getList());
				}
				
				
				bot.sendMessage(usuario, mensagemBot);
				return DialogTypeFinish.AGUARDANDODADO;
			}
			
			if(currentDialogTypeFinish.equals(DialogTypeFinish.AGUARDANDODADO)) {
				
				if(!listaEstatica.contains(mensagemUsuario) && !listaDinamica.getList().contains(mensagemUsuario)) {
					return DialogTypeFinish.CONTEUDOINVALIDO; 
				}
				
				JSONObject retorno = new JSONObject();
				
				retorno.put(chaveDado, mensagemUsuario);
				
				return retorno;
				
			}
			
			return currentDialogTypeFinish.ERRO;
			

		} catch (Exception e) {
			Acesso access = App.getCon().getBean("access", Acesso.class);
			if (usuario.getApelido() != null) {
				bot.sendMessage(access.getAdminTelegram(),
						"Ocorreu o seguinte erro para o usuário: " + usuario.getApelido() + "&#010;" + e.getMessage());
			} else {
				bot.sendMessage(access.getAdminTelegram(),
						"Ocorreu o seguinte erro (não foi possivel saber o usuário): " + e.getMessage());
			}
			return DialogTypeFinish.ERRO;

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
