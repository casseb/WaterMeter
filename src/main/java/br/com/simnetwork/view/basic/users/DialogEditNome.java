package br.com.simnetwork.view.basic.users;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

public class DialogEditNome extends Dialog {

	public DialogEditNome(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()) {
			answer.append("Preencha seu nome completo");
			
			return finishStep();
		}
		
		if(nextStep()) {
			addComplementString("nome");
			
			answer.append("Confirma os seguintes dados?\n\n");
			answer.append("Nome Completo: "+getComplementString("nome"));
			
			messageConfirmation();
			
			return finishStep();
		}
		
		if(nextStep()) {
			if(isConfirmated()) {
				person.setNomeCompleto(getComplementString("nome"));
				model.editPerson(person);
				answer.append("Edição concluída com sucesso");
				return finishHim();
			}else {
				return finishHim();
			}
		}
		return null;
	}

}
