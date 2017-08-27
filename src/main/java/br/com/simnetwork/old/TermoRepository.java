package br.com.simnetwork.old;

import org.springframework.data.repository.CrudRepository;

public interface TermoRepository extends CrudRepository<Termo, TermoPK> {
	
	//public Termo findTop1ByTermoPKTermoTopicoOrderByTermoPKParagrafo(TermoTopico termoTopico);

}
