package br.com.simnetwork.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.simnetwork.model.entity.basico.rota.RotaPK;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

	public Usuario findByBotId(String botId);
	
	public Usuario findByApelido(String apelido);
	
	public List<Usuario> findByRotasPermitidasRotaPK(RotaPK rota);
	
}
