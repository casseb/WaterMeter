package br.com.simnetwork.model.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.rota.RotaPK;

public interface RotaRepository extends CrudRepository<Rota, RotaPK>{
	
	public List<Rota> findByBasico(int basico);
	
	public List<Rota> findByAdmin(int admin);
	
	public List<Rota> findByRotaPKRotaGrupo(String rotaGrupo);

}
