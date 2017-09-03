package br.com.simnetwork.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.debug.Debug;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.UsuarioService;

@Service("dialogActived")
public class DialogActivedImpl implements DialogsActivated {

	public static Map<String, Dialog> dialogs = new HashMap<String, Dialog>();
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private Debug debug;

	@Override
	public void executeDialog(String botId, String mensagemUsuario) {

		Dialog dialog = DialogActivedImpl.dialogs.get(botId);
		Usuario usuario = usuarioService.localizarUsuarioPorTelegram(botId);

		if (dialog != null) {
			debug.setMessage("DialogActived: Dialogo ativo já existente");
			if (usuario == null) {
				debug.setMessage(
						"DialogActived: Usuário não existe ,mas um diálogo dele sim, iniciado instancia de um novo");
				usuario = new Usuario();
				usuario.setBotId(botId);
			}
			executeAction(usuario, mensagemUsuario);
		} else {
			if (dialog == null && usuario != null) {
				debug.setMessage("DialogActived: Nenhum diálogo ativo e usuário existe, iniciando menu");
				dialog = App.getStaticDialogContext().getBean("|R|Navegação|Menu Principal|B| |I|", Dialog.class);
				DialogActivedImpl.dialogs.put(botId, dialog);
				executeAction(usuario, mensagemUsuario);
			} else {
				debug.setMessage("DialogActived: Usuário não existe, iniciando rota de boas vindas");
				usuario = new Usuario();
				usuario.setBotId(botId);
				dialog = App.getStaticDialogContext().getBean("|R|Meus Dados|Editar Apelido|B| | |", Dialog.class);
				DialogActivedImpl.dialogs.put(botId, dialog);
				executeAction(usuario, mensagemUsuario);
			}
		}

	}

	@Override
	public void setDialogActived(String botId, Dialog dialog) {
		DialogActivedImpl.dialogs.put(botId, dialog);

	}

	@Override
	public void removeDialogActived(String id) {
		if (DialogActivedImpl.dialogs.containsKey(id)) {
			debug.setMessage("DialogActived: Removendo rota de id: " + id);
			DialogActivedImpl.dialogs.remove(id);
		}
	}

	public void executeAction(Usuario usuario, String mensagemUsuario) {
		boolean repetir = false;
		do{
			Dialog dialog = DialogActivedImpl.dialogs.get(usuario.getBotId());
			dialog.setMensagemUsuario(mensagemUsuario);
			dialog.getSteps().get(dialog.getCurrentStep()).action(usuario.getBotId(),dialog);
			dialog = DialogActivedImpl.dialogs.get(usuario.getBotId());
			repetir = dialog.action(usuario.getBotId(),mensagemUsuario);
		}
		while(repetir);
		
	}

	@Override
	public void prepareDialogActived(String mensagemUsuario) {

		for (Map.Entry<String, Dialog> dialogActivated : DialogActivedImpl.dialogs.entrySet()) {
			if ((dialogActivated.getValue().getCurrentDialogTypeFinish().equals(DialogTypeFinish.ERRO))
					|| (dialogActivated.getValue().getCurrentDialogTypeFinish().equals(DialogTypeFinish.FINALIZADO))
					|| mensagemUsuario.equals("Menu")) {
				removeDialogActived(dialogActivated.getKey());
			}
		}

	}

	

}
