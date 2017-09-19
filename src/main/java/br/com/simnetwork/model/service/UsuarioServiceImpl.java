package br.com.simnetwork.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.Usuario;
import br.com.simnetwork.model.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Override
	public void salvar(String usuario, String senha) {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setLogin(usuario);
		novoUsuario.setSenha(senha);
		usuarioRepo.save(novoUsuario);
		
	}
	
	

}
