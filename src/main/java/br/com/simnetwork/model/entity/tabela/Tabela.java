package br.com.simnetwork.model.entity.tabela;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Tabela")
public class Tabela {
	
	@EmbeddedId
	private TabelaPK tabelaPk;
	
	@Column(name = "unico")
	private int unico;
	
	@Column(name = "conteudo_txt", length = 500)
	private String conteudoTxt;
	
	@Column(name = "conteudo_int")
	private int conteudoInt;
	
	@Column(name = "conteudo_double")
	private Double conteudoDouble;
	
	@Column(name = "conteudo_data")
	private Timestamp data;
	
	@Column(name = "conteudo_boolean", length = 1)
	private String conteudoBoolean;

	public Tabela() {
		super();
	}

	public TabelaPK getTabelaPk() {
		return tabelaPk;
	}

	public void setTabelaPk(TabelaPK tabelaPk) {
		this.tabelaPk = tabelaPk;
	}

	public int getUnico() {
		return unico;
	}

	public void setUnico(int unico) {
		this.unico = unico;
	}

	public String getConteudoTxt() {
		return conteudoTxt;
	}

	public void setConteudoTxt(String conteudoTxt) {
		this.conteudoTxt = conteudoTxt;
	}

	public int getConteudoInt() {
		return conteudoInt;
	}

	public void setConteudoInt(int conteudoInt) {
		this.conteudoInt = conteudoInt;
	}

	public Double getConteudoDouble() {
		return conteudoDouble;
	}

	public void setConteudoDouble(Double conteudoDouble) {
		this.conteudoDouble = conteudoDouble;
	}
	
	public LocalDateTime getDataCriacao() {
		if (data != null) {
			return data.toLocalDateTime();
		}
		return null;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.data = Timestamp.valueOf(dataCriacao);
	}
	
	public String getConteudoBoolean() {
		return conteudoBoolean;
	}

	public void setConteudoBoolean(String conteudoBoolean) {
		this.conteudoBoolean = conteudoBoolean;
	}
	
}
