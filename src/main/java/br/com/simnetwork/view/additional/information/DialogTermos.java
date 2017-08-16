package br.com.simnetwork.view.additional.information;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.view.basic.Dialog;

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
