package br.com.simnetwork.view;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public interface DialogFactory {

	public boolean createDialog(String rotaGrupo, String rota, Usuario usuario);
	
}
