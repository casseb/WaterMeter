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

@Service("dynamicListRotaPermitido")
@Scope("prototype")
public class DynamicListRotaPermitido implements DynamicList{

	private List<String> list = new LinkedList<String>();
	private Usuario usuario;
	private String grupoRota;
	@Autowired
	private Acesso access;
	@Autowired
	private RotaService rotaService;

	@Override
	public void prepareList(Object... object) {

		if (object[0] instanceof Usuario && object[1] instanceof String) {

			usuario = (Usuario) object[0];
			grupoRota = (String) object[1];
			
			List<Rota> rotasGrupo = rotaService.listarRotaporGrupoRota(grupoRota);
			List<Rota> rotasPermitidas = usuario.getRotasPermitidas().stream().collect(Collectors.toList());
			rotasPermitidas.addAll(rotaService.listarRotasBasicas());
			
			for(Rota rotaPermitida : rotasPermitidas) {
				for(Rota rotaGrupo : rotasGrupo) {
					if(rotaPermitida.getBeanName().equals(rotaGrupo.getBeanName())) {
						list.add(rotaPermitida.getRotaPK().getNome());
					}
				}
			}
			
			
			if(usuario.getBotId().equals(access.getAdminTelegram())) {
				for(Rota rota : rotaService.listarRotaporGrupoRota(grupoRota)) {
					if(rota.getAdmin() && !rota.getInvisivel()) {
						list.add(rota.getRotaPK().getNome());
					}
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

	public String getGrupoRota() {
		return grupoRota;
	}

	public void setGrupoRota(String grupoRota) {
		this.grupoRota = grupoRota;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	

}
