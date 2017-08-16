package br.com.simnetwork.model.basic.person;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.competencia.Competencia;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Person")
public class Person {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = true)
	private String apelido;
	
	@Column(nullable = true)
	private String nomeCompleto;
	
	@Column(nullable = true)
	private String cfp;
	
	@Column(nullable = true)
	private String nomeFantasia;
	
	@Column(nullable = true)
	private String razaoSocial;
	
	@Column(nullable = true)
	private String cnpj;
	
	@Column(nullable = true)
	private Timestamp dataNascimento;
	
	@ManyToOne
	@JoinColumn(name="id_endereco")
	private Endereco endereco;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Person_Contato", joinColumns = { @JoinColumn(name = "id_Person") }, inverseJoinColumns = { @JoinColumn(name = "id_Contato") })
	private Set<Route> contatos = new HashSet<Route>();

	@Column(nullable = true)
	private String idTelegram;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Person_Route", joinColumns = { @JoinColumn(name = "id_Person") }, inverseJoinColumns = { @JoinColumn(name = "id_Route") })
	private Set<Route> rotasPermitidas = new HashSet<Route>();
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private PersonTipo personType;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private PersonFinalidade finalidade;
	
	@Column(nullable = true)
	private boolean active = true;
	
	@Column(nullable = true)
	private boolean termoAceito = false;
	
	@Column(nullable = true)
	private boolean liberadoCompra = true;
	
	@Column(nullable = true)
	private Timestamp dataUltimoContato;
	
	@ManyToOne
	@JoinColumn(name="id_Representante_Atual")
	private Person representanteAtual;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Person_Competencia", joinColumns = { @JoinColumn(name = "id_Person") }, inverseJoinColumns = { @JoinColumn(name = "id_Competencia") })
	private Set<Competencia> competencias = new HashSet<Competencia>();
	
	@Column(nullable = true)
	private Double valorAReceber;
	
	@ManyToOne
	@JoinColumn(name="id_DadosBancarios")
	private DadosBancarios dadosBancarios;
	
	public Person() {
		super();
	}
	
	public Person(String idTelegram) {
		super();
		this.idTelegram = idTelegram;
	}
	
	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCfp() {
		return cfp;
	}

	public void setCfp(String cfp) {
		this.cfp = cfp;
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

	public LocalDateTime getDataNascimento() {
		if (dataNascimento != null) {
			return dataNascimento.toLocalDateTime();
		}
		return null;
	}

	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = Timestamp.valueOf(dataNascimento);
	}

	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	public PersonFinalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(PersonFinalidade finalidade) {
		this.finalidade = finalidade;
	}

	public boolean isLiberadoCompra() {
		return liberadoCompra;
	}

	public void setLiberadoCompra(boolean liberadoCompra) {
		this.liberadoCompra = liberadoCompra;
	}

	public LocalDateTime getDataUltimoContato() {
		if (dataUltimoContato != null) {
			return dataUltimoContato.toLocalDateTime();
		}
		return null;
	}

	public void setDataUltimoContato(LocalDateTime dataUltimoContato) {
		this.dataUltimoContato = Timestamp.valueOf(dataUltimoContato);
	}

	
	public Person getRepresentanteAtual() {
		return representanteAtual;
	}

	public void setRepresentanteAtual(Person representanteAtual) {
		this.representanteAtual = representanteAtual;
	}
	

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public PersonTipo getPersonType() {
		return personType;
	}

	public void setPersonType(PersonTipo personType) {
		this.personType = personType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdTelegram() {
		return idTelegram;
	}

	public void setIdTelegram(String idTelegram) {
		this.idTelegram = idTelegram;
	}


	public Set<Route> getContatos() {
		return contatos;
	}

	public void setContatos(Set<Route> contatos) {
		this.contatos = contatos;
	}

	public Set<Route> getRotasPermitidas() {
		return rotasPermitidas;
	}

	public void setRotasPermitidas(Set<Route> rotasPermitidas) {
		this.rotasPermitidas = rotasPermitidas;
	}

	public boolean isTermoAceito() {
		return termoAceito;
	}

	public void setTermoAceito(boolean termoAceito) {
		this.termoAceito = termoAceito;
	}

	public Double getValorAReceber() {
		return valorAReceber;
	}

	public void setValorAReceber(Double valorAReceber) {
		this.valorAReceber = valorAReceber;
	}

	public DadosBancarios getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(DadosBancarios dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public void setDataNascimento(Timestamp dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setDataUltimoContato(Timestamp dataUltimoContato) {
		this.dataUltimoContato = dataUltimoContato;
	}

	public Set<Competencia> getCompetencias() {
		return competencias;
	}

	public void setCompetencias(Set<Competencia> competencias) {
		this.competencias = competencias;
	}
	
	

}
