package br.com.simnetwork.view.basic.users;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

public class DialogDisableUser extends Dialog {

	public DialogDisableUser(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione qual usuário deseja desativar");
			objects.addAll(model.persons);
			for (Person person : model.persons) {
				if(!person.isActive()){
					trash.add(person);
				}
			}
			emptyTrash();
			prepareKeyboard(model.showPersons(objects));
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
