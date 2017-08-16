package br.com.simnetwork.view.basic.users;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

public class DialogActiveUser extends Dialog {

	public DialogActiveUser(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione qual usuário deseja ativar");
			objects.addAll(model.persons);
			for (Person person : model.persons) {
				if(person.isActive()){
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
				person.setActive(true);
				model.editPerson(person);
				answer.append("Ativado com sucesso");
				return finishHim();
			}
		}
		return null;
	}

}
