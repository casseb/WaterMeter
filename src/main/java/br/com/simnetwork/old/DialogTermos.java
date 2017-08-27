package br.com.simnetwork.old;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class DialogTermos extends Dialog {

	public DialogTermos(TelegramBot bot, Usuario person, Rota route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		answer.append("http://simnetwork.com.br/#/termos");
		return finishHim(null);
	}


}
