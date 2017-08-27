package br.com.simnetwork.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.repository.RotaRepository;
import br.com.simnetwork.model.service.RotaService;

@Service("dialogFactory")
public class DialogFactoryImpl implements DialogFactory {

	@Autowired
	private RotaService rotaService;
	
	@Override
	public boolean createDialog(String rotaGrupo, String rota, Usuario usuario) {
		
		Rota rotaTemp = rotaService.pesquisarPorPK(rotaGrupo, rota);
		
		if(rota == null) {
			return false;
		}
		
		DialogsActivated dialogsActivated = App.getCon().getBean("dialogActived",DialogsActivated.class);
		Dialog dialog = App.getCon().getBean(rotaTemp.getBeanName(),Dialog.class);
		
		dialogsActivated.removeDialogActived(usuario.getBotId());
		dialogsActivated.setDialogActived(usuario.getBotId(),dialog);
		dialogsActivated.executeDialog(usuario.getBotId()," ");
		
		return true;

	}

}
