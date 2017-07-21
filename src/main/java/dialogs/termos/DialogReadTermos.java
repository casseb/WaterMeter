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

public class DialogReadTermos extends Dialog {

	public DialogReadTermos(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		List<String> keyboard = new LinkedList<>();
		for (TermoTopico topico : model.termoTopico.values()) {
			if (model.showTermosByTopico(topico).size() != 0) {
				keyboard.add(topico.descricao);
			}
		}

		if (nextStep()) {
			answer.append("Selecione o tópico do termo que deseja ler");

			prepareKeyboard(keyboard, 2);

			return finishStep();
		}

		if (nextStep()) {
			if (keyboard.contains(message)) {

				List<Termo> termos = model.locateTermosByTopicoOficiais(model.termoTopico.locateTermoTopicoByString(message));
				if(termos.size()!=0) {
					for (Termo termo : termos) {
						answer.append(termo.getCodigoParagrafo()+" - "+termo.getDescricao()+"\n");
					}
				}else {
					answer.append("Não há paragrafos oficiais");
				}
				
				return finishHim();
			} else {
				return messageInvalid();
			}
		}

		return null;
	}

}
