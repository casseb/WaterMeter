package dialogs.basic.users;

import java.util.LinkedList;
import java.util.List;

import org.omg.CORBA.DynAnyPackage.Invalid;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.basic.Person;
import objects.basic.Route;

public class DialogMinhasCompetencias extends Dialog {

	public DialogMinhasCompetencias(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	List<String> escolhas = new LinkedList<>();
	
	@Override
	public Dialog action() {
				
		if(nextStep()) {
			answer.append("Deseja Adicionar ou remover uma competência?");
			escolhas.add("Adicionar");
			escolhas.add("Remover");
			prepareKeyboard(escolhas);
			return finishStep();
		}
		
		if(nextStep()) {
			if(escolhas.contains(message)) {
				if(message.equals("Adicionar")) {
					return finishHim(new DialogAddMinhasCompetencias(bot, person, route, model, message));
				}
				if(message.equals("Remover")) {
					return finishHim(new DialogRemoveMinhasCompetencias(bot, person, route, model, message));
				}
			}else {
				return messageInvalid();
			}
		}
		return null;
	}

}
