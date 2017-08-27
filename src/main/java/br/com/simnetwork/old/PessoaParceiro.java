package br.com.simnetwork.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Entity
@Table(name = "Pessoa_Parceiro")
public class PessoaParceiro extends Usuario {
	
	@Column(name = "pep_id_telegram", length = 200, nullable = false)
	private String idTelegram;
	
	@Column(name = "pep_termo_aceito", nullable = false)
	private boolean termoAceito;

	public PessoaParceiro(String idTelegram, boolean termoAceito) {
		super();
		this.idTelegram = idTelegram;
		this.termoAceito = termoAceito;
	}

	public String getIdTelegram() {
		return idTelegram;
	}

	public void setIdTelegram(String idTelegram) {
		this.idTelegram = idTelegram;
	}

	public boolean isTermoAceito() {
		return termoAceito;
	}

	public void setTermoAceito(boolean termoAceito) {
		this.termoAceito = termoAceito;
	}
	
	

}
