package br.com.simnetwork.model.entity.basico.rota;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "Rota")
public class Rota implements Serializable{
	
	@EmbeddedId
	private RotaPK rotaPK;
	
	@Column(name = "rot_basico")
	private int basico;
	
	@Column(name = "rot_admin")
	private int admin;
	
	public Rota() {
		super();
	}

	//@EmbeddedId
	public RotaPK getRotaPK() {
		return rotaPK;
	}

	public void setRotaPK(RotaPK rotaPK) {
		this.rotaPK = rotaPK;
	}

	public int getBasico() {
		return basico;
	}

	public void setBasico(int basico) {
		this.basico = basico;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	

}
