package br.com.simnetwork.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.debug.Debug;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.service.RotaService;

@Service("dialogFactory")
public class DialogFactoryImpl implements DialogFactory {

	@Autowired
	private RotaService rotaService;
	
	@Override
	public void createDialog(String rotaGrupo, String rota, Usuario usuario) {
		
		Rota rotaTemp = rotaService.pesquisarPorPK(rotaGrupo, rota);
		Dialog dialog = App.getStaticDialogContext().getBean(rotaTemp.getBeanName(),Dialog.class);
		DialogActivedImpl.dialogs.put(usuario.getBotId(),dialog);
		
	}

	@Override
	public void createDialog(String bean, Usuario usuario) {
		Dialog dialog = App.getStaticDialogContext().getBean(bean,Dialog.class);
		DialogActivedImpl.dialogs.put(usuario.getBotId(),dialog);
	}
	

}
