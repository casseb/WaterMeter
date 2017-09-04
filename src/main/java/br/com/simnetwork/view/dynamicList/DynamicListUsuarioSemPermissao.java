package br.com.simnetwork.view.dynamicList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.service.UsuarioService;

@Service("dynamicListUsuarioSemPermissao")
@Scope("prototype")
public class DynamicListUsuarioSemPermissao implements DynamicList{

	private List<String> list = new LinkedList<String>();
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public void prepareList(Object... object) {

		for(Usuario usuario : usuarioService.localizarUsuarioComPermissoesDisponiveis()) {
			list.add(usuario.getApelido());
		}
		
		
		list = new LinkedList<String>(new HashSet<>(list));
	}

	@Override
	public List<String> getList() {
		return this.list;
	}

}
