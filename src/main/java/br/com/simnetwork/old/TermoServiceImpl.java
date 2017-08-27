package br.com.simnetwork.old;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class TermoServiceImpl implements TermoService {

	@Autowired
	private TermoTopicoRepository termoTopicoRepo;
	
	@Autowired
	private TermoRepository termoRepo;
	
	@Override
	@Transactional
	public void AdicionarTermos(TermoTopico termoTopico, String descricao) {
		Termo termo = new Termo();
		TermoPK termoPK = new TermoPK(termoTopico,1);
		termo.setTermopk(termoPK);
		termoRepo.save(termo);
		//System.out.println(termoRepo.findTop1ByTermoPKTermoTopicoOrderByTermoPKParagrafo(termoTopico));

	}

	@Override
	@Transactional
	public void AdicionarTermoTopico(String termoTopico) {
		TermoTopico topico = new TermoTopico();
		topico.setNome(termoTopico);
		termoTopicoRepo.save(topico);
	}

}
