package br.com.simnetwork.model.entity.basico.rota;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class RotaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2294147366319057733L;

	@Column(name = "rot_nome", length = 200)
	private String nome;
	
	@Column(name = "rot_grupo", length = 200)
	private String rotaGrupo;

	public RotaPK() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	public String getRotaGrupo() {
		return rotaGrupo;
	}

	public void setRotaGrupo(String rotaGrupo) {
		this.rotaGrupo = rotaGrupo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((rotaGrupo == null) ? 0 : rotaGrupo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RotaPK other = (RotaPK) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (rotaGrupo != other.rotaGrupo)
			return false;
		return true;
	}
	
	
}
