package dialogs.basic.users;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Person;
import objects.Route;
import objects.RouteGroup;

public class DialogRevokeGroupPermission extends Dialog {

	public DialogRevokeGroupPermission(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if (nextStep()) {
			answer.append("Pressione o nome do usuário que deseja retirar permissão");
			answer.append("\nAbaixo usuários cadastrados\n");
			prepareKeyboard(model.showPersons(model.persons));
			return finishStep();
		}
		if (nextStep()) {
			if (model.locatePerson(message) == null)
				return messageInvalid();
			else {
				addComplementString("usuario");
				answer.append("\nPressione o grupo da rota que deseja bloquear");
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
				answer.append("Deseja remover permissão ao menu " + getComplementString("routeGroup") + " ao usuário "
						+ getComplementString("usuario") + " ?");
				messageConfirmation();
				return finishStep();
			}
		}
		if (nextStep()) {
			if (isConfirmated()) {
				model.revokePermission(person,
						model.routeGroup.locateRouteGroupByDesc(getComplementString("routeGroup")));
				answer.append("Permissão removida com sucesso");
				return finishHim();
			} else {
					return finishHim();
			}
		}
		return null;
	}
	
}
