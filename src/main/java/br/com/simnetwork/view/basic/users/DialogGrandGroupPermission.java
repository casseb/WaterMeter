package br.com.simnetwork.view.basic.users;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.basic.route.RouteGroup;
import br.com.simnetwork.view.basic.Dialog;

public class DialogGrandGroupPermission extends Dialog {

	public DialogGrandGroupPermission(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if (nextStep()) {
			answer.append("Pressione o nome do usuário que deseja dar permissão");
			answer.append("\nAbaixo usuários cadastrados\n");
			prepareKeyboard(model.showPersons(model.persons));
			return finishStep();
		}
		if (nextStep()) {
			if (model.locatePerson(message) == null)
				return messageInvalid();
			else {
				addComplementString("usuario");
				answer.append("\nPressione o grupo da rota que deseja liberar");
				prepareKeyboard(model.showGroupRoutes(model.routesDenieds(person)));
				return finishStep();
			}
		}
		if (nextStep()) {
			RouteGroup routeGroup = model.routeGroup.locateRouteGroupByDesc(message);
			if (routeGroup == null)
				return messageInvalid();
			else {
				addComplementString("routeGroup");
				answer.append("Deseja dar permissão ao menu " + getComplementString("routeGroup") + " ao usuário "
						+ getComplementString("usuario") + " ?");
				messageConfirmation();
				return finishStep();
			}
		}
		if (nextStep()) {
			if (isConfirmated()) {
				model.grandPermission(person,
						model.routeGroup.locateRouteGroupByDesc(getComplementString("routeGroup")));
				answer.append("Permissão concedida com sucesso");
				return finishHim();
			} else {
					return finishHim();
			}
		}
		return null;
	}

}
