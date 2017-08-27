package br.com.simnetwork.view;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public interface Dialog {
	
	public DialogTypeFinish action(Usuario usuario, String mensagemUsuario);
	
	public DialogTypeFinish getCurrentDialogTypeFinish();

}
