package br.com.simnetwork.view.additional.project;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.project.Project;
import br.com.simnetwork.view.basic.Dialog;

public class DialogEditStatusProject extends Dialog {

	public DialogEditStatusProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o novo status");
			prepareKeyboard(model.showProjectStatus());
			return finishStep();
		}
		if(nextStep()){
			if(model.projectStatus.locateProjectStatusByString(message)==null) return messageInvalid(); 
			addComplementString("status");
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				Project project = model.locateProjectByString(getComplementString("id"));
				project.setStatus(model.projectStatus.locateProjectStatusByDesc(getComplementString("status")));
				model.editProject(project);
				return finishHim(null);
			}else{
				return finishHim(null);
			}
		}
		return null;
	}

}
