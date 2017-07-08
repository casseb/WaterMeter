package dialogs.basic;

import com.pengrad.telegrambot.TelegramBot;

import mvc.Model;
import objects.Person;
import objects.Route;

public class DialogShowUserInfo extends Dialog {

	public DialogShowUserInfo(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		answer.append(model.showUserData(person));
		return finishHim(null);
	}

}
