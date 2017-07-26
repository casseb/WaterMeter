package dialogs.termos;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.basic.Person;
import objects.basic.Route;

public class DialogAcceptTermo extends Dialog {

	public DialogAcceptTermo(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()) {
			if(!person.isTermoAceito()) {
				answer.append("Deseja aceitar os termos vigentes?");
				sendFile(model.box.getFileByBox(model.locateBoxFileObjectByName("Termos.pdf", "Termos")));
				messageConfirmation();
				return finishStep();
			}else {
				answer.append("Já foi realizado o aceite dos termos mais atualizados");
				return finishHim();
			}
		}
		
		if(nextStep()) {
			if(isConfirmated()) {
				person.setTermoAceito(true);
				model.editPerson(person);
				return finishHim();
			}else {
				return finishHim();
			}
		}
		return null;
	}

}
