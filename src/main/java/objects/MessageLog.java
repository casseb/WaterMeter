package objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "MessageLog")
public class MessageLog {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	
	@ManyToOne
	@JoinColumn(name = "id_person")
	private Person person;

	@Column(nullable = true)
	private String message;

	@Column(nullable = true)
	private Timestamp hora;
	
	@Column(nullable = true)
	private String triggerLog;

	public MessageLog() {
		super();
	}

	public MessageLog(Person person, String message, String trigger) {
		super();
		this.person = person;
		this.message = message;
		setHora(LocalDateTime.now());
		this.triggerLog = trigger;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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
