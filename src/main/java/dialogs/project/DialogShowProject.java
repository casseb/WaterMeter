package dialogs.project;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.basic.Person;
import objects.basic.Route;

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
