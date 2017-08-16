package br.com.simnetwork.view.additional.project;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.project.Project;
import br.com.simnetwork.view.basic.Dialog;

public class DialogEditTitleProject extends Dialog {

	public DialogEditTitleProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Digite o novo título");
			return finishStep();
		}
		if(nextStep()){
			addComplementString("title");
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				Project project = model.locateProjectByString(getComplementString("id"));
				project.setTitle(getComplementString("title"));
				model.editProject(project);
				return finishHim();
			}else{
				return finishHim();
			}
		}
		return null;
	}

}
