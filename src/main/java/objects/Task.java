package objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import objects.basic.Person;

public class Task {
	
	private Person creator;
	private Person resp;
	private String Description;
	private Project project;
	private LocalDateTime created;
	private LocalDateTime deadline;

}
