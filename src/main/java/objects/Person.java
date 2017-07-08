package objects;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Person")
public class Person {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = true)
	private String senha;

	@Column(nullable = true)
	private String name;

	@Column(nullable = true)
	private String idTelegram;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Person_Route", joinColumns = { @JoinColumn(name = "id_Person") }, inverseJoinColumns = { @JoinColumn(name = "id_Route") })
	private List<Route> rotasPermitidas = new LinkedList<Route>();
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private PersonType personType;
	
	@Column
	private boolean active = true;
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public PersonType getPersonType() {
		return personType;
	}

	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}

	public Person() {
		super();
	}
	
	public Person(String idTelegram) {
		super();
		this.idTelegram = idTelegram;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdTelegram() {
		return idTelegram;
	}

	public void setIdTelegram(String idTelegram) {
		this.idTelegram = idTelegram;
	}

	
	public List<Route> getRotasPermitidas() {
		return rotasPermitidas;
	}

	public void setRotasPermitidas(List<Route> rotasPermitidas) {
		this.rotasPermitidas = rotasPermitidas;
	}
	
	

}
