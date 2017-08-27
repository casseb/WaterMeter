package br.com.simnetwork.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

	public Usuario findByBotId(String botId);
	
	public Usuario findByApelido(String apelido);
	
}
