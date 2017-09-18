package objects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Project")
public class Project {	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	private String title;
	
	@Column(nullable = true, name = "description")
	private String desc;
	
	@Column(nullable = true, name = "projectType")
	@Enumerated(EnumType.STRING)
	private ProjectType type;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING) 
	private ProjectStatus status;
	
	@ManyToOne
	@JoinColumn(name="id_client")
	private Person client;
	
	@ManyToOne
	@JoinColumn(name="id_salesman")
	private Person salesman;
	
	@ManyToOne
	@JoinColumn(name="id_creator")
	private Person creator;
	
	@Column(nullable = true)
	private float salePrice;
	

	//private Product product;
	//private List<Worker> workers;
	
	public Project() {
		super();
	}

	public Project(String title, String description,ProjectType projectType, Person creator) {
		this.title = title;
		this.desc = description;
		this.creator = creator;
		this.salesman = creator;
		this.type = projectType;
		this.status = status.IDEIA;
	}

	
	public String toString(){
		return this.id+" - "+this.title;
	}
	

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
	}

	public Person getClient() {
		return client;
	}

	public void setClient(Person client) {
		this.client = client;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public Person getSalesman() {
		return salesman;
	}

	public void setSalesman(Person salesman) {
		this.salesman = salesman;
	}
	

}
