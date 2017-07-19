package dialogs.basic.users;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.basic.Person;
import objects.basic.Route;

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
