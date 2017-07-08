package dialogs.project;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.Dialog;
import mvc.Model;
import objects.Person;
import objects.Project;
import objects.Route;

public class DialogEditDescProject extends Dialog {

	public DialogEditDescProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Digite a nova descrição");
			return finishStep();
		}
		if(nextStep()){
			addComplementString("desc");
			messageConfirmation();
			return finishStep();
		}
		if(nextStep()){
			if(isConfirmated()){
				Project project = model.locateProjectByString(getComplementString("id"));
				project.setDesc(getComplementString("desc"));
				model.editProject(project);
				return finishHim(null);
			}else{
				return finishHim(null);
			}
		}
		return null;
	}

}
