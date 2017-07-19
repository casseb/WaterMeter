package objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
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
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private TermoTopico topico;
	
	@Column(nullable = true)
	private String descricao;
	
	@Column(nullable = true)
	private Timestamp dataCriacao;
	
	@Column(nullable = true)
	private int codigoParagrafo;

	@Column(nullable = true)
	private boolean oficial;
	
	@Column(nullable = true)
	private boolean deletado;

	
	public Termo() {
		super();
	}
	
	public Termo(TermoTopico topico, String descricao) {
		super();
		this.topico = topico;
		this.descricao = descricao;
		setDataCriacao(LocalDateTime.now());
		this.codigoParagrafo = topico.getUltimoParagrafo()+1;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public TermoTopico getTopico() {
		return topico;
	}


	public void setTopico(TermoTopico topico) {
		this.topico = topico;
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


	public int getCodigoParagrafo() {
		return codigoParagrafo;
	}


	public void setCodigoParagrafo(int codigoParagrafo) {
		this.codigoParagrafo = codigoParagrafo;
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
	
	
	
	
	

}
