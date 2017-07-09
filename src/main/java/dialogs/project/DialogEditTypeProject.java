package dialogs.project;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Person;
import objects.Project;
import objects.Route;

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
