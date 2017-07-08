package dialogs.basic;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogActiveUser extends Dialog {

	public DialogActiveUser(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione qual usuário deseja ativar");
			List<Person> persons = model.persons;
			for (Person person : persons) {
				if(person.isActive()){
					persons.remove(person);
				}
			}
			prepareKeyboard(model.showPersons(persons));
			return finishStep();
		}
		
		if(nextStep()){
			Person person = model.locatePerson(message);
			if(person == null)
				return messageInvalid();
			else{
				person.setActive(true);
				model.editPerson(person);
				answer.append("Ativado com sucesso");
				return finishHim();
			}
		}
		return null;
	}

}
