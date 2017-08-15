package dialogs.basic.users;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.basic.Person;
import objects.basic.Route;

public class DialogEditApelido extends Dialog {

	public DialogEditApelido(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Como deseja ser chamado?");
			return finishStep();
		}
		if(nextStep()){
			if(!model.persons.contains(model.locatePerson(message))) {
				addComplementString("usuario");
				person.setApelido(getComplementString("usuario"));
				model.editUsernamePassword(person);
				answer.append("Edição realizada com sucesso!!!");
			}else {
				answer.append("Já existe um usuário utilizando este nome\n");
				return messageInvalid();
			}
			return finishHim();
		}
		return null;

	}

}
