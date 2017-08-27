package br.com.simnetwork.model.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.simnetwork.model.entity.acesso.Acesso;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.rota.RotaPK;
import br.com.simnetwork.model.entity.framework.App;
import br.com.simnetwork.model.repository.RotaRepository;

@Service("rotaService")
public class RotaServiceImpl implements RotaService {

	@Autowired
	private RotaRepository rotaRepo;
	@Autowired
	private UsuarioService usuarioService;

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

	@Override
	@Transactional
	public void salvarRota(String grupo, String rota, String compl1, String compl2) {
		
		boolean replicarTodos = false;
		boolean replicarAdm = false;
		
		if (grupo != null && rota != null) {
			RotaPK rotapk = new RotaPK();
			rotapk.setNome(rota);
			rotapk.setRotaGrupo(grupo);
			Rota rotaNova = new Rota();
			rotaNova.setRotaPK(rotapk);

			if (!rotaRepo.exists(rotapk)) {

				List<String> complements = new LinkedList<>();
				if (compl1 != null) {
					complements.add(compl1);
				}
				if (compl2 != null) {
					complements.add(compl2);
				}

				if (complements.contains("B")) {
					rotaNova.setBasico(1);
					replicarTodos = true;
				} else {
					rotaNova.setBasico(0);
				}
				if (complements.contains("A")) {
					rotaNova.setAdmin(1);
					replicarAdm = true;
				} else {
					rotaNova.setAdmin(0);
				}
				
				rotaRepo.save(rotaNova);
				
				if(replicarTodos) {
					usuarioService.darPermissaoTodos(rotaNova);
				}
				
				if(replicarAdm){
					Acesso acesso = App.getCon().getBean("access",Acesso.class);
					usuarioService.darPermissao(usuarioService.localizarUsuarioPorTelegram(acesso.getAdminTelegram()), rotaNova);
				}

			}

		}

	}

}
