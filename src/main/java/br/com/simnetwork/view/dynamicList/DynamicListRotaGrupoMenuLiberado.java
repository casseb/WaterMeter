package br.com.simnetwork.view.dynamicList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Service("dynamicListRotaGrupoMenuLiberado")
@Scope("prototype")
public class DynamicListRotaGrupoMenuLiberado implements DynamicList {

	private List<String> list = new LinkedList<String>();
	private Usuario usuario;
	@Override
	public void prepareList(Object... object) {

		if (object[0] instanceof Usuario) {

			usuario = (Usuario) object[0];
			
			for(Rota rotaBloq : usuario.getRotasPermitidas()) {
				list.add(rotaBloq.getRotaPK().getRotaGrupo());
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
