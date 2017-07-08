package dialogs.information;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.request.SendMessage;

import dialogs.basic.Dialog;
import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogTermos extends Dialog {

	public DialogTermos(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		answer.append("http://simnetwork.com.br/#/termos");
		return finishHim(null);
	}


}
