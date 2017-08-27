package br.com.simnetwork.model.entity.tabela;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TabelaPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2034491976872235037L;

	@Column(name = "tabela", length = 200)
	private String tabela;
	
	@Column(name = "campo", length = 200)
	private String campo;
	
	@Column(name = "chave")
	private int chave;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabelaPK(String tabela, String campo, int chave) {
		super();
		this.tabela = tabela;
		this.campo = campo;
		this.chave = chave;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public int getChave() {
		return chave;
	}

	public void setChave(int chave) {
		this.chave = chave;
	}
	
	
	
	
}
