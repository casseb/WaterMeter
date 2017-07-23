package objects.files;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mvc.Model;

@Entity
@Table(name = "BoxFile")
public class BoxFileObject {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String telegramLink;
	
	@Column(nullable = true)
	private String boxId;
	
	@ManyToOne
	@JoinColumn(name="id_boxFolder")
	private BoxFolderObject boxFolderObject;

	public BoxFolderObject getBoxFolderObject() {
		return boxFolderObject;
	}

	public void setBoxFolderObject(BoxFolderObject boxFolderObject) {
		this.boxFolderObject = boxFolderObject;
	}

	public BoxFileObject(){
		super();
	}
	
	public BoxFileObject(String name, String telegramLink, Model model, BoxFolderObject boxFolderObject) {
		super();
		this.name = name;
		this.telegramLink = telegramLink;
		this.boxId = model.box.addFileByTelegram(this,boxFolderObject);
		this.boxFolderObject = boxFolderObject;
		model.addBoxFileObject(this);
	}
	
	public BoxFileObject(String name, Model model, File file, BoxFolderObject boxFolderObject) {
		super();
		this.name = name;
		this.telegramLink = null;
		this.boxId = model.box.addFile(file,name,boxFolderObject);
		this.boxFolderObject = boxFolderObject;
		model.addBoxFileObject(this);
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelegramLink() {
		return telegramLink;
	}

	public void setTelegramLink(String link) {
		this.telegramLink = link;
	}

	public String getBoxLink() {
		return boxId;
	}

	public void setBoxLink(String boxLink) {
		this.boxId = boxLink;
	}
	
	

}
