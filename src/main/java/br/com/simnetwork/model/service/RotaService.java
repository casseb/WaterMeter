package br.com.simnetwork.model.service;

import java.util.List;
import java.util.Set;

import br.com.simnetwork.model.entity.basico.rota.Rota;

public interface RotaService {
	
	public List<Rota> listarRotasBasicas();
	
	public List<Rota> listasRotasAdm();

}
