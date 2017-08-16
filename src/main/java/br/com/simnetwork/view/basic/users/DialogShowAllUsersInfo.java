package br.com.simnetwork.view.basic.users;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

public class DialogShowAllUsersInfo extends Dialog {

	public DialogShowAllUsersInfo(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		
		if(nextStep()){
			answer.append("Pressione o usuário que deseja ver os dados");
			prepareKeyboard(model.showPersons(model.persons));
			return finishStep();
		}
		if(nextStep()){
			Person person = model.locatePerson(message);
			answer.append(model.showUserDataWithoutPassword(person));
			return finishHim();
		}
		
		return null;
	}

}
