package br.com.simnetwork.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Contato")
public class Contato {
	
	@Id
	@Column(name = "con_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "con_tipo", length = 100, nullable = false)
	@Enumerated(EnumType.STRING)
	private ContatoTipo tipo;
	
	@Column(name = "con_conteudo", length = 100, nullable = false)
	private String conteudo;
	
	public Contato() {
		super();
	}

	public Contato(ContatoTipo tipo, String conteudo) {
		super();
		this.tipo = tipo;
		this.conteudo = conteudo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ContatoTipo getTipo() {
		return tipo;
	}

	public void setTipo(ContatoTipo tipo) {
		this.tipo = tipo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	

}
