package dialogs.client;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogAddClient extends Dialog {

	public DialogAddClient(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Digite o nome do cliente que deseja cadastrar");
			return finishStep();
		}
		if(nextStep()){
			addComplementString("name");
			answer.append("Será adicionado o cliente "+getComplementString("name"));
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				Person person = new Person();
				person.setName(getComplementString("name"));
				model.addClient(person);
				return finishHim(null);
			}else{
				return finishHim(null);
			}
		}
		return null;
	}

}
