package br.com.simnetwork.old;

import java.io.Serializable;

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

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Entity
@Table(name = "Endereco")
public class Endereco implements Serializable{
	
	@Id
	@ManyToOne
	@JoinColumn(name="pes_apelido")
	private Usuario pessoa;
	
	@Column(name = "end_rua", length = 200, nullable = false)
	private String rua;
	
	@Column(name = "end_numero", length = 10, nullable = false)
	private String numero;
	
	@Column(name = "end_complemento", length = 200,  nullable = true)
	private String complemento;
	
	@Column(name = "end_bairro", length = 200, nullable = false)
	private String bairro;
	
	@Column(name = "end_cidade", length = 200, nullable = false)
	private String cidade;
	
	@Column(name = "end_uf", length = 2, nullable = false)
	@Enumerated(EnumType.STRING)
	private Estado uf;
	
	@Column(name = "end_cep", length = 8, nullable = false)
	private String cep;

	public Endereco(String rua, String numero, String complemento, String bairro, String cidade, Estado uf,
			String cep) {
		super();
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
	}

	public Endereco() {
		super();
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getUf() {
		return uf;
	}

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	} 
	
	

}
