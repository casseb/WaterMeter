package objects;

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
@Table(name = "BoxFolder")
public class BoxFolderObject {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String boxId;
	
	@ManyToOne
	@JoinColumn(name="id_root")
	private BoxFolderObject root;

	public BoxFolderObject(){
		super();
	}
	
	public BoxFolderObject(String name, Model model, BoxFolderObject root) {
		super();
		this.name = name;
		this.root = root;
		this.boxId = model.box.addFolderByTelegram(this,root);
		model.addBoxFolderObject(this);
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


	public String getBoxLink() {
		return boxId;
	}

	public void setBoxLink(String boxLink) {
		this.boxId = boxLink;
	}
	
	

}
