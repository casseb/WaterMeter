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
@Table(name = "Dados_Bancarios")
public class DadosBancarios implements Serializable{

	@Id
	@ManyToOne
	@JoinColumn(name="pes_apelido")
	private Usuario pessoa;
	
	@Column(name = "dab_banco", length = 3, nullable = false)
	@Enumerated(EnumType.STRING)
	private Banco banco;
	
	@Column(name = "dab_agencia", length = 10, nullable = false)
	private String agencia;
	
	@Column(name = "dab_conta_corrente", length = 10, nullable = false)
	private String contaCorrente;
	
	@Column(name = "dab_nome_titular", length = 200, nullable = false)
	private String nomeTitular;
	
	@Column(name = "dab_cpf_cnpj", length = 13, nullable = false)
	private String cpf;

	public DadosBancarios(Banco banco, String agencia, String contaCorrente, String nomeTitular, String cpf) {
		super();
		this.banco = banco;
		this.agencia = agencia;
		this.contaCorrente = contaCorrente;
		this.nomeTitular = nomeTitular;
		this.cpf = cpf;
	}

	public DadosBancarios() {
		super();
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	 
	
	
}
