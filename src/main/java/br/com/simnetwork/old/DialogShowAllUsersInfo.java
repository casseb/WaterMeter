package br.com.simnetwork.old;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class DialogShowAllUsersInfo extends Dialog {

	public DialogShowAllUsersInfo(TelegramBot bot, Usuario person, Rota route, Model model, String message) {
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
			Usuario person = model.locatePerson(message);
			answer.append(model.showUserDataWithoutPassword(person));
			return finishHim();
		}
		
		return null;
	}

}
