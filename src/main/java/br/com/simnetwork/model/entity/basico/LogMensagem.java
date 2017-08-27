package br.com.simnetwork.model.entity.basico;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.com.simnetwork.model.entity.basico.usuario.Usuario;
import br.com.simnetwork.old.Estado;

@Entity
@Table(name = "MensagemLog")
public class LogMensagem {

	@Id
	@Column(name = "menlog_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="usu_id")
	private Usuario usuario;

	@Column(name = "menlog_mensagem", nullable = false)
	private String mensagem;

	@Column(name = "menlog_data", nullable = false)
	private Timestamp data;
	
	@Column(name = "menlog_origem", length = 100, nullable = false)
	@Enumerated(EnumType.STRING)
	private LogMensagemOrigem origem;


	public LogMensagem(Usuario pessoa, String mensagem, LogMensagemOrigem origem) {
		super();
		this.usuario = pessoa;
		this.mensagem = mensagem;
		setData(LocalDateTime.now());
		this.origem = origem;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Usuario getPessoa() {
		return usuario;
	}


	public void setPessoa(Usuario pessoa) {
		this.usuario = pessoa;
	}


	public String getMensagem() {
		return mensagem;
	}


	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	public LocalDateTime getData() {
		if (data != null) {
			return data.toLocalDateTime();
		}
		return null;
	}

	public void setData(LocalDateTime data) {
		this.data = Timestamp.valueOf(data);
	}


	public LogMensagemOrigem getOrigem() {
		return origem;
	}


	public void setOrigem(LogMensagemOrigem origem) {
		this.origem = origem;
	}

	
}
