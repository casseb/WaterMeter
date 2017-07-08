package dialogs;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogEditLogin extends Dialog {

	public DialogEditLogin(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Digite o usuário que deseja utilizar");
			return finishStep();
		}
		if(nextStep()){
			addComplementString("usuario");
			answer.append("Digite a senha desejada");
			return finishStep();
		}
		if(nextStep()){
			addComplementString("senha");
			person.setName(getComplementString("usuario"));
			person.setSenha(getComplementString("senha"));
			model.editUsernamePassword(person);
			answer.append("Edição realizada com sucesso!!!");
			return finishHim(null);
		}
		return null;

	}

}
