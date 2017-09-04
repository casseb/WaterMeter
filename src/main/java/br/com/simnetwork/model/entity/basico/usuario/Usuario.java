package br.com.simnetwork.model.entity.basico.usuario;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.simnetwork.model.entity.basico.rota.Rota;

@Entity
@Table(name = "Usuario")
public class Usuario {

	



	@Id
	@Column(name = "usu_id", length = 100)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "usu_apelido", length = 100)
	private String apelido;

	@Column(name = "usu_liberado")
	private int liberado;
	
	@Column(name = "usu_id_bot", length = 45)
	private String botId;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_rota", joinColumns = { @JoinColumn(name = "usu_id") }, inverseJoinColumns = {
			@JoinColumn(name = "rot_nome"), @JoinColumn(name = "rot_grupo") })
	private Set<Rota> rotasPermitidas = new HashSet<Rota>();
	
	
	
	public Usuario() {
		super();
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	public String getApelido() {
		return apelido;
	}



	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public int getLiberado() {
		return liberado;
	}



	public void setLiberado(int liberado) {
		this.liberado = liberado;
	}



	public String getBotId() {
		return botId;
	}



	public void setBotId(String botId) {
		this.botId = botId;
	}



	public Set<Rota> getRotasPermitidas() {
		return rotasPermitidas;
	}



	public void setRotasPermitidas(Set<Rota> rotasPermitidas) {
		this.rotasPermitidas = rotasPermitidas;
	}

	

}
