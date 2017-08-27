package br.com.simnetwork.view.dialogStep;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.view.DialogFactory;
import br.com.simnetwork.view.DialogTypeFinish;

@Service("stepDialogFactory")
public class DialogStepDialogFactory implements DialogStep {

	@Autowired
	DialogFactory dialogFactory;
	
	@Override
	public Object action(Usuario usuario, String mensagemUsuario, DialogTypeFinish currentDialogTypeFinish,
			JSONObject complements) {
		
		String rotaGrupo = complements.getString("grupoRota");
		String rota = complements.getString("rota");
		
		dialogFactory.createDialog(rotaGrupo, rota, usuario);
		
		return DialogTypeFinish.FINALIZADO;
		
	}

}
