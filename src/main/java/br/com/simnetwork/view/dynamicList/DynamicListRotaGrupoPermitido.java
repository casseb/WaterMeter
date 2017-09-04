package br.com.simnetwork.view.dynamicList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.service.RotaService;

@Service("dynamicListRotaGrupoPermitido")
@Scope("prototype")
public class DynamicListRotaGrupoPermitido implements DynamicList {

	private List<String> list = new LinkedList<String>();
	private Usuario usuario;
	@Autowired
	private Acesso access;
	@Autowired
	private RotaService rotaService;

	@Override
	public void prepareList(Object... object) {

		if (object[0] instanceof Usuario) {

			usuario = (Usuario) object[0];
			List<Rota> rotas = usuario.getRotasPermitidas().stream().collect(Collectors.toList());
			
			for (Rota rota : rotas) {
				list.add(rota.getRotaPK().getRotaGrupo());
			}
			
			for (Rota rota : rotaService.listarRotasBasicas()) {
				if(!rota.getInvisivel()) {
					list.add(rota.getRotaPK().getRotaGrupo());
				}	
			}
			
			if(usuario.getBotId().equals(access.getAdminTelegram())) {
				for (Rota rota : rotaService.listarRotasAdm()) {
					list.add(rota.getRotaPK().getRotaGrupo());
				}
			}
			
			
			list = new LinkedList<String>(new HashSet<>(list));

		}

	}

	@Override
	public List<String> getList() {
		return this.list;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	
}
