package br.com.simnetwork.model.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.simnetwork.model.entity.tabela.Tabela;
import br.com.simnetwork.model.entity.tabela.TabelaPK;

public interface TabelaRepository extends CrudRepository<Tabela, TabelaPK> {

}
