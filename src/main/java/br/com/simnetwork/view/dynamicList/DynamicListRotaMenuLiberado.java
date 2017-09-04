package br.com.simnetwork.view.dynamicList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.service.UsuarioService;

@Service("dynamicListRotaMenuLiberado")
@Scope("prototype")
public class DynamicListRotaMenuLiberado implements DynamicList{

	private List<String> list = new LinkedList<String>();
	private Usuario usuario;
	private String grupoRota;
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void prepareList(Object... object) {

		if (object[0] instanceof Usuario && object[1] instanceof String) {

			usuario = (Usuario) object[0];
			grupoRota = (String) object[1];

			
			for(Rota rotaBloq : usuario.getRotasPermitidas()) {
				if(rotaBloq.getRotaPK().getRotaGrupo().equals(grupoRota)) {
					list.add(rotaBloq.getRotaPK().getNome());
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
