package br.com.simnetwork.old;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class DialogEditNome extends Dialog {

	public DialogEditNome(TelegramBot bot, Usuario person, Rota route, Model model, String message) {
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
