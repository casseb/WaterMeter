package br.com.simnetwork.model.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.simnetwork.model.entity.basico.LogMensagem;

public interface MessageLogRepository extends CrudRepository<LogMensagem, Integer> {

}
