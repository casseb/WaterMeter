package br.com.simnetwork.old;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;

@Entity
@Table(name = "ScheduleMessage")
public class MensagemAgendada {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_person")
	private PessoaParceiro pessoa;
	
	@Column(nullable = true)
	private String message;
	
	@Column(nullable = true)
	private Timestamp hora;
	
	public MensagemAgendada(){
		super();
	}
	
	public MensagemAgendada(PessoaParceiro pessoa, String message, LocalDateTime hora) {
		super();
		this.pessoa = pessoa;
		this.message = message;
		setHora(hora);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PessoaParceiro getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaParceiro pessoa) {
		this.pessoa = pessoa;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getHora() {
		if (hora != null) {
			return hora.toLocalDateTime();
		}
		return null;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = Timestamp.valueOf(hora);
	}
	
}
