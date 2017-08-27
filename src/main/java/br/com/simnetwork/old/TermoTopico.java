package br.com.simnetwork.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Termo_Topico")
public class TermoTopico {

	@Id
	@Column(name = "tet_nome")
	private String nome;
	
	
	
	public TermoTopico() {
		super();
	}

	public TermoTopico(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
