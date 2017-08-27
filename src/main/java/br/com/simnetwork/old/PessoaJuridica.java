package br.com.simnetwork.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Entity
@Table(name = "Pessoa_Juridica")
public class PessoaJuridica extends Usuario {
	
	@Column(name = "pej_nome_fantasia", length = 200, nullable = true)
	private String nomeFantasia;
	
	@Column(name = "pej_razao_social", length = 200, nullable = true)
	private String razaoSocial;
	
	@Column(name = "pej_cnpj", length = 14, nullable = true)
	private String cnpj;

	public PessoaJuridica() {
		super();
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	

}
