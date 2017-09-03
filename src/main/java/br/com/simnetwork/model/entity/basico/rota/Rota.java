package br.com.simnetwork.model.entity.basico.rota;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "Rota")
public class Rota implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1755517836854014198L;

	@EmbeddedId
	private RotaPK rotaPK;
	
	@Column(name = "rot_basico")
	private int basico;
	
	@Column(name = "rot_admin")
	private int admin;
	
	@Column(name = "rot_invisivel")
	private int invisivel;
	
	public boolean getInvisivel() {
		if(this.invisivel==1) {
			return true;
		}else {
			return false;
		}
		
	}

	public void setInvisivel(boolean invisivel) {
		if(invisivel) {
			 this.invisivel = 1;
		}else {
			this.invisivel =  0;
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public boolean getBasico() {
		if(this.basico==1) {
			return true;
		}else {
			return false;
		}
	}

	public void setBasico(boolean basico) {
		if(basico) {
			 this.basico = 1;
		}else {
			this.basico =  0;
		}
	}

	public boolean getAdmin() {
		if(this.admin==1) {
			return true;
		}else {
			return false;
		}
	}

	public void setAdmin(boolean admin) {
		if(admin) {
			 this.admin = 1;
		}else {
			this.admin =  0;
		}
	}
	
	public String getBeanName() {
		String basico;
		String admin;
		String invisivel;
		if(this.basico == 1) {
			basico = "B";
		}else {
			basico = " ";
		}
		
		if(this.admin == 1) {
			admin = "A";
		}else {
			admin = " ";
		}
		
		if(this.invisivel == 1) {
			invisivel = "I";
		}else {
			invisivel = " ";
		}
		
		return "|R|"+this.rotaPK.getRotaGrupo()+"|"+this.rotaPK.getNome()+"|"+basico+"|"+admin+"|"+invisivel+"|";
	}

}
