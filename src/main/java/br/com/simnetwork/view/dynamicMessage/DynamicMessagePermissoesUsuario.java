package br.com.simnetwork.view.dynamicMessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.service.RotaService;
import br.com.simnetwork.model.service.UsuarioService;

@Service("dynamicMessagePermissoesUsuario")
@Scope("prototype")
public class DynamicMessagePermissoesUsuario implements DynamicMessage{

	private StringBuilder message = new StringBuilder();
	private Usuario usuario;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RotaService rotaService;
	
	@Override
	public void prepareMessage(Object... object) {
		
		if (object[0] instanceof Usuario) {

			usuario = (Usuario) object[0];
			
			if(!usuario.getRotasPermitidas().isEmpty()) {
				message.append("Rotas Disponíveis:\n\n\n");
				for(Rota rotaDisponivel : usuario.getRotasPermitidas()) {
					message.append("-"+rotaDisponivel.getRotaPK().getRotaGrupo()+" : "+rotaDisponivel.getRotaPK().getNome()+"\n");
				}
			}
			
			if(!usuarioService.listarRotasBloqueadas(usuario).isEmpty()) {
				message.append("\n\n\nRotas Bloqueadas:\n\n\n");
				for(Rota rotaBloqueada : usuarioService.listarRotasBloqueadas(usuario)) {
					message.append("-"+rotaBloqueada.getRotaPK().getRotaGrupo()+" : "+rotaBloqueada.getRotaPK().getNome()+"\n");
				}
			}
			
			message.append("\n\n\nRotas Básicas:\n\n\n");
			for (Rota rotaBasica : rotaService.listarRotasBasicas()) {
				message.append("-"+rotaBasica.getRotaPK().getRotaGrupo()+" : "+rotaBasica.getRotaPK().getNome()+"\n");
			}
			
			
			
		}
		
	}

	@Override
	public String getMessage() {
		return this.message.toString();
	}

	
	
}
