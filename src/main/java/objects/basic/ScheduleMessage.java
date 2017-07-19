package objects.basic;

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

@Entity
@Table(name = "ScheduleMessage")
public class ScheduleMessage {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_person")
	private Person person;
	
	@Column(nullable = true)
	private String message;
	
	@Column(nullable = true)
	private Timestamp hora;
	
	public ScheduleMessage(){
		super();
	}
	
	public ScheduleMessage(Person person, String message, LocalDateTime hora) {
		super();
		this.person = person;
		this.message = message;
		setHora(hora);
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
