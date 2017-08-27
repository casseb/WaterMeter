package br.com.simnetwork.old;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class DialogActiveUser extends Dialog {

	public DialogActiveUser(TelegramBot bot, Usuario person, Rota route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione qual usuário deseja ativar");
			objects.addAll(model.persons);
			for (Usuario person : model.persons) {
				if(person.isActive()){
					trash.add(person);
				}
			}
			emptyTrash();
			prepareKeyboard(model.showPersons(objects));
			return finishStep();
		}
		
		if(nextStep()){
			Usuario person = model.locatePerson(message);
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
