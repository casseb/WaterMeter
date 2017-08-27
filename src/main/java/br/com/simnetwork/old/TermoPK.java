package br.com.simnetwork.old;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TermoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8204418862853674024L;
	
	@ManyToOne
	@JoinColumn(name="tet_nome")
	private TermoTopico termoTopico;
	
	@Column(name = "ter_paragrafo")
	private int paragrafo;

	public TermoTopico getTermoTopico() {
		return termoTopico;
	}

	public void setTermoTopico(TermoTopico termoTopico) {
		this.termoTopico = termoTopico;
	}

	public int getParagrafo() {
		return paragrafo;
	}

	public void setParagrafo(int paragrafo) {
		this.paragrafo = paragrafo;
	}

	public TermoPK(TermoTopico termoTopico, int paragrafo) {
		super();
		this.termoTopico = termoTopico;
		this.paragrafo = paragrafo;
	}
	
	
	

}
