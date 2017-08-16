package br.com.simnetwork.view.additional.project;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

public class DialogRemoveProject extends Dialog{

	public DialogRemoveProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o Id do Projeto que deseja remover do sistema");
			prepareKeyboard(model.showProjects(model.locateAllProjects()));
			return finishStep();
		}
		if(nextStep()){
			if(model.locateProjectByString(message) == null) return messageInvalid();
			addComplementString("id");
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				model.removeProject(model.locateProjectByString(getComplementString("id")));
				answer.append("Projeto removido com sucesso");
				return finishHim();
			}else{
				return finishHim();
			}
		}
		return null;
		
	}

}
