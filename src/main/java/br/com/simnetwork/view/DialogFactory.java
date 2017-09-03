package br.com.simnetwork.view;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public interface DialogFactory {

	public void createDialog(String rotaGrupo, String rota, Usuario usuario);
	
	public void createDialog(String bean, Usuario usuario);
	
}
