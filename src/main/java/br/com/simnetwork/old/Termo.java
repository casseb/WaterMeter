package br.com.simnetwork.old;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Termo")
public class Termo {
	
	@EmbeddedId
	private TermoPK termopk;
	
	@Column(name = "ter_descricao", length = 500, nullable = false)
	private String descricao;
	
	@Column(name = "ter_descricao_temp", length = 500, nullable = false)
	private String descricaoTemp;
	
	@Column(name = "ter_data_criacao", nullable = false)
	private Timestamp dataCriacao;

	@Column(name = "ter_oficial", nullable = false)
	private boolean oficial;
	
	@Column(name = "ter_criado", nullable = false)
	private boolean criado;
	
	@Column(name = "ter_modificado", nullable = false)
	private boolean modificado;
	
	@Column(name = "ter_deletado", nullable = false)
	private boolean deletado;

	
	public Termo() {
		super();
	}
	

	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public LocalDateTime getDataCriacao() {
		if (dataCriacao != null) {
			return dataCriacao.toLocalDateTime();
		}
		return null;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = Timestamp.valueOf(dataCriacao);
	}


	public boolean isOficial() {
		return oficial;
	}


	public void setOficial(boolean oficial) {
		this.oficial = oficial;
	}


	public boolean isDeletado() {
		return deletado;
	}


	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	public String getDescricaoTemp() {
		return descricaoTemp;
	}

	public void setDescricaoTemp(String descricaoTemp) {
		this.descricaoTemp = descricaoTemp;
	}

	public boolean isCriado() {
		return criado;
	}

	public void setCriado(boolean criado) {
		this.criado = criado;
	}

	public boolean isModificado() {
		return modificado;
	}

	public void setModificado(boolean modificado) {
		this.modificado = modificado;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public TermoPK getTermopk() {
		return termopk;
	}


	public void setTermopk(TermoPK termopk) {
		this.termopk = termopk;
	}
	
	
	
	
	

}
