package objects.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DadosBancarios")
public class DadosBancarios {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Banco banco;
	
	@Column(nullable = true)
	private String agencia;
	
	@Column(nullable = true)
	private String contaCorrente;
	
	@Column(nullable = true)
	private String nomeTitular;
	
	@Column(nullable = true)
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
