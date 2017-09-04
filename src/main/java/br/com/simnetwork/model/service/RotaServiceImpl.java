package br.com.simnetwork.model.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.simnetwork.model.entity.basico.Utils;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.rota.RotaPK;
import br.com.simnetwork.model.entity.framework.App;
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
	public List<Rota> listarRotasAdm() {
		return rotaRepo.findByAdmin(1);
	}

	@Override
	@Transactional
	public Rota AtualizarRotaByBean(String grupo, String rota, String compl1, String compl2, String compl3) {

		if (grupo != null && rota != null) {

			RotaPK rotapk = new RotaPK();
			rotapk.setNome(rota);
			rotapk.setRotaGrupo(grupo);

			Rota rotaNova;
			if (rotaRepo.exists(rotapk)) {
				rotaNova = rotaRepo.findOne(rotapk);
			} else {
				rotaNova = new Rota();
			}

			rotaNova.setRotaPK(rotapk);

			List<String> complements = new LinkedList<>();
			if (compl1 != null) {
				complements.add(compl1);
			}
			if (compl2 != null) {
				complements.add(compl2);
			}
			if (compl3 != null) {
				complements.add(compl3);
			}

			if (complements.contains("B")) {
				rotaNova.setBasico(true);
			} else {
				rotaNova.setBasico(false);
			}
			if (complements.contains("A")) {
				rotaNova.setAdmin(true);
			} else {
				rotaNova.setAdmin(false);
			}
			if (complements.contains("I")) {
				rotaNova.setInvisivel(true);
			} else {
				rotaNova.setInvisivel(false);
			}
			
			this.salvarRota(rotaNova);
			
			return rotaNova;

		}
		
		return null;

	}

	@Override
	@Transactional
	public Rota pesquisarPorPK(String rotaGrupo, String rota) {
		RotaPK rotaPK = new RotaPK();
		rotaPK.setRotaGrupo(rotaGrupo);
		rotaPK.setNome(rota);
		return rotaRepo.findOne(rotaPK);
	}
	
	
	
	@Override
	@Transactional
	public void sincronizarRotas() {

		List<String> rotaBeans = new LinkedList<>();
		for (String beanString : App.getDianamicDialogContext().getBeanDefinitionNames()) {
			if (beanString.contains("|R|")) {
				rotaBeans.add(beanString);
			}
		}
		
		for (String beanString : App.getStaticDialogContext().getBeanDefinitionNames()) {
			if (beanString.contains("|R|")) {
				rotaBeans.add(beanString);
			}
		}

		for (String rotaBean : rotaBeans) {
			List<String> parts = Utils.extractLetterFor(rotaBean, "|");
			
			this.salvarRota(this.AtualizarRotaByBean(parts.get(2), parts.get(3), parts.get(4), parts.get(5), parts.get(6)));
		}
		
		for (Rota rota : rotaRepo.findAll()) {
			if(!rotaBeans.contains(rota.getBeanName())) {
				rota.setInvisivel(true);
				rotaRepo.save(rota);
			}
		}

	}

	@Override
	@Transactional
	public void salvarRota(Rota rota) {
		
		rotaRepo.save(rota);
		
	}

	@Override
	public Rota localizarRotaByBean(String bean) {
		
		List<String> parts = Utils.extractLetterFor(bean, "|");
		
		RotaPK rotapk = new RotaPK();
		rotapk.setNome(parts.get(3));
		rotapk.setRotaGrupo(parts.get(2));

		if (rotaRepo.exists(rotapk)) {
			return rotaRepo.findOne(rotapk);
		} else {
			return null;
		}
		
	}

	@Override
	@Transactional
	public List<Rota> listarRotasVisiveisMenu() {
		List<Rota> resultado = new LinkedList<>();
		for(Rota rota : rotaRepo.findAll()) {
			if(!rota.getAdmin() && !rota.getBasico() && !rota.getInvisivel()) {
				resultado.add(rota);
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public List<Rota> listarRotasInvisiveisMenu() {
		List<Rota> resultado = new LinkedList<>();
		for(Rota rota : rotaRepo.findAll()) {
			if(rota.getAdmin() || rota.getBasico() || rota.getInvisivel()) {
				resultado.add(rota);
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public List<Rota> listarRotaporGrupoRota(String rotaGrupo) {
		return rotaRepo.findByRotaPKRotaGrupo(rotaGrupo);
	}


}
