package br.com.simnetwork.model.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.repository.RotaRepository;

@Service("rotaService")
public class RotaServiceImpl implements RotaService {

	@Autowired
	private RotaRepository rotaRepo;
	
	@Override
	@Transactional
	public List<Rota> listarRotasBasicas() {
		
		return rotaRepo.findByBasico(1);
	}

	@Override
	@Transactional
	public List<Rota> listasRotasAdm() {
		return rotaRepo.findByAdmin(1);
	}

}
