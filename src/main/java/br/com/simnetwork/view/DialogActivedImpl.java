package br.com.simnetwork.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.UsuarioService;

@Service("dialogActived")
public class DialogActivedImpl implements DialogsActivated {

	private Map<String, Dialog> dialogsActivated = new HashMap<String, Dialog>();
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public DialogTypeFinish getDialogStatus(String botId) {
		
		if(dialogsActivated.get(botId)!=null) {
			return dialogsActivated.get(botId).getCurrentDialogTypeFinish();
		}else {
			return null;
		}
		
	}

	@Override
	public void executeDialog(String botId, String mensagemUsuario) {
		Dialog dialog = dialogsActivated.get(botId);
		Usuario usuario = usuarioService.localizarUsuarioPorTelegram(botId);
		if(dialog!=null) {
			if(usuario==null) {
				usuario = new Usuario();
				usuario.setBotId(botId);
			}
			executeAction(dialog, usuario, mensagemUsuario, false);
		}else {
			if(dialog==null && usuario!=null) {
				dialog = App.getCon().getBean("|R|Navegação|Menu Principal|B| |", Dialog.class);
				executeAction(dialog, usuario, mensagemUsuario, true);
			}else {
				usuario = new Usuario();
				usuario.setBotId(botId);
				dialog = App.getCon().getBean("|R|Meus Dados|Editar Apelido|B| |", Dialog.class);
				executeAction(dialog, usuario, mensagemUsuario, true);
			}
		}

	}

	@Override
	public void setDialogActived(String botId, Dialog dialog) {
		this.dialogsActivated.put(botId, dialog);

	}

	@Override
	public void removeDialogActived(String id) {
		this.dialogsActivated.remove(id);
	}
	
	public void executeAction(Dialog dialog, Usuario usuario,String mensagemUsuario,boolean adicionarAtivos) {
		boolean repeat = false;
		do {
			DialogTypeFinish typeFinish = dialog.action(usuario, mensagemUsuario);
			if (typeFinish.equals(DialogTypeFinish.FINALIZADOSTEP)) {
				repeat = true;
			} else {
				repeat = false;
			}
		} while (repeat);

		if(adicionarAtivos) {
			setDialogActived(usuario.getBotId(), dialog);
		}
		
	}

	@Override
	public void prepareDialogActived() {
		
		for (Map.Entry<String, Dialog> dialogActivated : dialogsActivated.entrySet()) {
			if ((dialogActivated.getValue().getCurrentDialogTypeFinish().equals(DialogTypeFinish.ERRO))
					|| (dialogActivated.getValue().getCurrentDialogTypeFinish()
							.equals(DialogTypeFinish.FINALIZADO))) {
				removeDialogActived(dialogActivated.getKey());
			}
		}
		
	}


}
