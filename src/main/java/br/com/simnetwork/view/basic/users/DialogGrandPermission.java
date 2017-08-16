package br.com.simnetwork.view.basic.users;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.basic.route.RouteGroup;
import br.com.simnetwork.view.basic.Dialog;

public class DialogGrandPermission extends Dialog {

	public DialogGrandPermission(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o nome do usuário que deseja dar permissão");
			answer.append("\nAbaixo usuários cadastrados\n");
			prepareKeyboard(model.showPersons(model.persons));
			return finishStep();
		}
		if(nextStep()){
			if (model.locatePerson(message) == null)
				return messageInvalid();
			else {
				addComplementString("usuario");
				answer.append("\nPressione o grupo da rota que deseja liberar");
				prepareKeyboard(model.showGroupRoutes(model.routesDenieds(person)));
				return finishStep();
			}
		}
		if(nextStep()){
			RouteGroup routeGroup = model.routeGroup.locateRouteGroupByDesc(message);
			if (routeGroup == null)
				return messageInvalid();
			else{
				addComplementString("routeGroup");
				answer.append("\nPressione a rota que deseja liberar");
				prepareKeyboard(model.showRoutes(model.routeGroup.locateRouteGroupByDesc(getComplementString("routeGroup")),model.routesDenieds(person)));
				return finishStep();
			}
			
		}
		if(nextStep()){
			if (model.locateRoute(getComplementString("routeGroup")+" - "+message) == null)
				return messageInvalid();
			else {
				addComplementString("rota");
				answer.append("Deseja dar permissão a rota " + getComplementString("routeGroup") + " - " + getComplementString("rota") + " ao usuário "
						+ getComplementString("usuario") + " ?");
				messageConfirmation();
				return finishStep();
			}
		}
		if(nextStep()){
			if (isConfirmated()) {
				model.grandPermission(model.locatePerson(getComplementString("usuario")),
				model.locateRoute(getComplementString("routeGroup")+" - "+getComplementString("rota")));
				answer.append("Permissão concedida com sucesso");
				return finishHim();
			}else{
				return finishHim();
			}
		}
		return null;
	}

}
