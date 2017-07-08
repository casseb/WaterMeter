package dialogs.project;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.Dialog;
import mvc.Model;
import objects.Person;
import objects.Project;
import objects.Route;

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
