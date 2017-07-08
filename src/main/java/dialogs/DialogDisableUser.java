package dialogs;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogDisableUser extends Dialog {

	public DialogDisableUser(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione qual usuário deseja desativar");
			List<Person> persons = model.persons;
			for (Person person : persons) {
				if(!person.isActive()){
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
				person.setActive(false);
				model.editPerson(person);
				answer.append("Desativado com sucesso");
				return finishHim();
			}
		}
		return null;
	}

}
