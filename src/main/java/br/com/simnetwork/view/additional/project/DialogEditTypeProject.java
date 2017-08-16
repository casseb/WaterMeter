package br.com.simnetwork.view.additional.project;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.project.Project;
import br.com.simnetwork.view.basic.Dialog;

public class DialogEditTypeProject extends Dialog {

	public DialogEditTypeProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o novo tipo");
			prepareKeyboard(model.showProjectTypes());
			return finishStep();
		}
		if(nextStep()){
			if(model.projectType.locateProjectTypeByDesc(message) == null) return messageInvalid(); 
			addComplementString("type");
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				Project project = model.locateProjectByString(getComplementString("id"));
				project.setType(model.projectType.locateProjectTypeByDesc(getComplementString("type")));
				model.editProject(project);
				return finishHim(null);
			}else{
				return finishHim(null);
			}
		}
		return null;
	}

}
