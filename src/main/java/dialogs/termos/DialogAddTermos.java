package dialogs.termos;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Termo;
import objects.TermoTopico;
import objects.basic.Person;
import objects.basic.Route;

public class DialogAddTermos extends Dialog {

	public DialogAddTermos(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		
		//Lista de enum TermoTopico completa
		List<String> keyboard = new LinkedList<>();
		for (TermoTopico topico : model.termoTopico.values()) {
			keyboard.add(topico.descricao);
		}
		
		if(nextStep()) {
			answer.append("Selecione o tópico do novo termo");
			
			prepareKeyboard(keyboard, 2);
			
			return finishStep();		
		}
		
		if(nextStep()) {
			if(keyboard.contains(message) || isValid("topico")) {
				addComplementString("topico");
				
				answer.append("Digite o texto do novo termo");
				
				return finishStep();
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			addComplementString("descricao");
			answer.append("Confirma os seguintes dados?\n\n");
			answer.append("Tópico: "	+getComplementString("topico")		+"\n");
			answer.append("Descrição: "	+getComplementString("descricao")	+"\n");
			
			messageConfirmation();
			
			return finishStep();
		}
		
		if(nextStep()) {
			if(isConfirmated()) {
				Termo termo = new Termo(model.termoTopico.locateTermoTopicoByString(getComplementString("topico")),getComplementString("descricao"));
				model.addTermo(termo);
				return finishHim();
			}else {
				return finishHim();
			}
		}
		return null;
	}

}
