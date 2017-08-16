package br.com.simnetwork.view.additional.project;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

public class DialogShowProject extends Dialog {

	public DialogShowProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o Id do projeto que deseja mais detalhes");
			prepareKeyboard((model.showProjects(model.locateAllProjects())));
			return finishStep();
		}
		if(nextStep()){
			if(model.locateProjectByString(message)==null) return messageInvalid();
			answer.append(model.showProject(model.locateProjectByString(message)));
			return finishHim(null);
		}
		return null;
	}

}
