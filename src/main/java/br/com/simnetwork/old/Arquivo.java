package br.com.simnetwork.old;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Arquivo")
public class Arquivo {
	
	@Id
	@Column(name = "arq_id", length = 100)
	private String id;
	
	@Column(name = "arq_nome", length = 200, nullable = false)
	private String nome;
	
	@Transient
	private String telegramLink;
	
	@ManyToOne
	@JoinColumn(name="pas_id")
	private Pasta pasta;

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	public Arquivo(){
		super();
	}
	
	/*
	public Arquivo(String name, String telegramLink, Model model, BoxFolderObject boxFolderObject) {
		super();
		this.name = name;
		this.telegramLink = telegramLink;
		this.boxId = model.box.addFileByTelegram(this,boxFolderObject);
		this.boxFolderObject = boxFolderObject;
		model.addBoxFileObject(this);
	}
	
	
	public Arquivo(String name, Model model, File file, BoxFolderObject boxFolderObject) {
		
		super();
		this.name = name;
		this.telegramLink = null;
		this.boxId = model.box.addFile(file,name,boxFolderObject);
		this.boxFolderObject = boxFolderObject;
		model.addBoxFileObject(this);
		System.out.println("inserindo BoxFileObject");
	}
	*/

	

	public String getName() {
		return nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.nome = name;
	}

	public String getTelegramLink() {
		return telegramLink;
	}

	public void setTelegramLink(String link) {
		this.telegramLink = link;
	}
	
}
