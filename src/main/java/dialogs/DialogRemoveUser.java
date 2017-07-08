package dialogs;

import com.pengrad.telegrambot.TelegramBot;

import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogRemoveUser extends Dialog{

	public DialogRemoveUser(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o usuário que deseja remover do sistema");
			prepareKeyboard(model.showPersons(model.persons));
			return finishStep();
		}
		if(nextStep()){
			if(model.locatePerson(message)==null) return messageInvalid();
			addComplementString("usuario");
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				model.removePerson(model.locatePerson(getComplementString("usuario")));
				answer.append("Usuário removido com sucesso");
				return finishHim();
			}else{
				return finishHim();
			}
		}
		return null;
	}

}
