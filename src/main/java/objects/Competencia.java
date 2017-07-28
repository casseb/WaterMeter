package objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Competencia")
public class Competencia {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true)
	private String Descricao;
	
	@Column(nullable = true)
	private Double valor;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private CompetenciaTipo tipo;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private CompetenciaUN un;

	public Competencia(String descricao, Double valor, CompetenciaTipo tipo, CompetenciaUN un) {
		super();
		Descricao = descricao;
		this.valor = valor;
		this.tipo = tipo;
		this.un = un;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public CompetenciaTipo getTipo() {
		return tipo;
	}

	public void setTipo(CompetenciaTipo tipo) {
		this.tipo = tipo;
	}

	public CompetenciaUN getUn() {
		return un;
	}

	public void setUn(CompetenciaUN un) {
		this.un = un;
	}
	
	

}
