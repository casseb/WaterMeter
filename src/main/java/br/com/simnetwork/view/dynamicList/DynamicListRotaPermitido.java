package br.com.simnetwork.view.dynamicList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Service("dynamicListRotaPermitido")
@Scope("prototype")
public class DynamicListRotaPermitido implements DynamicList{

	private List<String> list = new LinkedList<String>();
	private Usuario usuario;
	private String grupoRota;

	@Override
	public void prepareList(Object... object) {

		if (object[0] instanceof Usuario && object[1] instanceof String) {

			usuario = (Usuario) object[0];
			grupoRota = (String) object[1];
			
			List<Rota> rotas = usuario.getRotasPermitidas().stream().collect(Collectors.toList());
			
			for (Rota rota : rotas) {
				if(rota.getRotaPK().getRotaGrupo().equals(grupoRota) && !rota.getInvisivel()) {
					list.add(rota.getRotaPK().getNome());
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
