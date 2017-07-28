package objects;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Administracao")
public class Administracao {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	private int versaoPrincipalTermos;
	@Column(nullable = true)
	private int versaoEstruturaTermos;
	@Column(nullable = true)
	private int versaoModificacoesTermos;
	@Column(nullable = true)
	private String arquivoUltimaAlteracaoTermos;
	@Column(nullable = true)
	private double fatorConversaoInic;
	
	public Administracao() {
		super();
		this.versaoPrincipalTermos = 5;
		this.versaoEstruturaTermos = 0;
		this.versaoModificacoesTermos = 0;
		this.fatorConversaoInic = 0.1;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersaoPrincipalTermos() {
		return versaoPrincipalTermos;
	}
	public int getVersaoEstruturaTermos() {
		return versaoEstruturaTermos;
	}
	public void plusVersaoEstruturaTermos() {
		this.versaoEstruturaTermos++;
	}
	public int getVersaoModificacoesTermos() {
		return versaoModificacoesTermos;
	}
	public void plusVersaoModificacoesTermos() {
		this.versaoModificacoesTermos++;
	}
	
	public String versaoTermo() {
		return versaoPrincipalTermos+"."+versaoEstruturaTermos+"."+versaoModificacoesTermos;
	}

	public String getArquivoUltimaAlteracaoTermos() {
		return arquivoUltimaAlteracaoTermos;
	}

	public void setArquivoUltimaAlteracaoTermos(String arquivoUltimaAlteracaoTermos) {
		this.arquivoUltimaAlteracaoTermos = arquivoUltimaAlteracaoTermos;
	}
	
	
}
