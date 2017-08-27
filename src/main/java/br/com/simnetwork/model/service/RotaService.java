package br.com.simnetwork.model.service;

import java.util.List;
import br.com.simnetwork.model.entity.basico.rota.Rota;

public interface RotaService {
	
	public List<Rota> listarRotasBasicas();
	
	public List<Rota> listasRotasAdm();
	
	public void salvarRota(String grupo, String rota, String compl1, String compl2);

}