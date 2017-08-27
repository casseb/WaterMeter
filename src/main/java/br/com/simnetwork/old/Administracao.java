package br.com.simnetwork.old;

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
	@Column(name = "adm_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "adm_versao_principal", nullable = false)
	private int versaoPrincipalTermos;
	@Column(name = "adm_versao_estrutura_termos", nullable = false)
	private int versaoEstruturaTermos;
	@Column(name = "adm_versao_modificacoes_termos", nullable = false)
	private int versaoModificacoesTermos;
	@Column(name = "adm_arquivo_ultima_alteracao_termos", nullable = true)
	private String arquivoUltimaAlteracaoTermos;
	@Column(name = "adm_fator_conversao_inic", nullable = false)
	private double fatorConversaoInic;
	
	public double getFatorConversaoInic() {
		return fatorConversaoInic;
	}

	public void setFatorConversaoInic(double fatorConversaoInic) {
		this.fatorConversaoInic = fatorConversaoInic;
	}

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
