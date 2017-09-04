package br.com.simnetwork.view.dynamicList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.model.service.UsuarioService;

@Service("dynamicListUsuarioSemPermissao")
public class DynamicListUsuarioSemPermissao implements DynamicList{

	private List<String> list = new LinkedList<String>();
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public void prepareList(Object... object) {

		for(Usuario usuario : usuarioService.localizarUsuarioComPermissoesDisponiveis()) {
			list.add(usuario.getApelido());
		}
		
	}

	@Override
	public List<String> getList() {
		return this.list;
	}

}
