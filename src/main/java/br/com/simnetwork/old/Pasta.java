package br.com.simnetwork.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Pasta")
public class Pasta {
	
	@Id
	@Column(name = "pas_id", length = 100)
	private String id;
	
	@Column(name = "pas_nome", length = 200, nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="pas_raiz", nullable = false)
	private Pasta raiz;

	public Pasta(){
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pasta getRaiz() {
		return raiz;
	}

	public void setRaiz(Pasta raiz) {
		this.raiz = raiz;
	}
	
	/*
	public Pasta(String name, Model model, Pasta root) {
		super();
		this.name = name;
		this.root = root;
		this.boxId = model.box.addFolderByTelegram(this,root);
		model.addBoxFolderObject(this);
	}
	*/

	
	
	

}
