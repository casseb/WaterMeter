package br.com.simnetwork.view.basic.users;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ChatAction;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.basic.route.RouteGroup;
import br.com.simnetwork.view.basic.Dialog;
import br.com.simnetwork.view.basic.DialogGenerator;

public class DialogCommands extends Dialog {

	public DialogCommands(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Escolha a categoria");
			prepareKeyboard(model.showGroupRoutes(person.getRotasPermitidas()));
			return finishStep(false);
		}
		if(nextStep()){
			addComplementString("routeGroup");
			answer.append("Escolha a rota desejada");
			prepareKeyboard(model.showRoutes(model.routeGroup.locateRouteGroupByDesc(message),person.getRotasPermitidas()));
			
			return finishStep();
		}
		if(nextStep()){
			DialogGenerator dialogGenerator = new DialogGenerator(message, bot, admin, person);
			Dialog dialog = dialogGenerator.create(getComplementString("routeGroup"));
			answer.append("Escolhido rota "+getComplementString("routeGroup")+" - "+message);
			return finishHim(dialog);
		}
		return null;
	}

}
