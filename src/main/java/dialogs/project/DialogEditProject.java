package dialogs.project;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.basic.Person;
import objects.basic.Route;

public class DialogEditProject extends Dialog{

	public DialogEditProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()){
			answer.append("Pressione o projeto que deseja editar");
			prepareKeyboard((model.showProjects(model.locateAllProjects())));
			return finishStep();
		}
		
		/*
		if(nextStep()){
			if(model.locateProjectByString(message) == null) return messageInvalid();
			addComplementString("id");
			answer.append("Pressione o que deseja editar");
			subMenus.add("Título");
			subMenus.add("Descrição");
			subMenus.add("Tipo");
			subMenus.add("Status");
			prepareKeyboard(subMenus);
			return finishStep();
		}
		if(nextStep()){
			if(!isSubMenuValid(message)) return messageInvalid();
			if(message.equals("Título")){
				Dialog newDialog = new DialogEditTitleProject(bot, person, model.locateRoute("Editar Projeto"), model, message);
				newDialog.addComplementString("id",getComplementString("id"));
				answer.append("Editando Título");
				return finishHim(newDialog);
			}
			if(message.equals("Descrição")){
				Dialog newDialog = new DialogEditDescProject(bot, person, model.locateRoute("Editar Projeto"), model, message);
				newDialog.addComplementString("id",getComplementString("id"));
				answer.append("Editando Descrição");
				return finishHim(newDialog);
			}
			if(message.equals("Tipo")){
				Dialog newDialog = new DialogEditTypeProject(bot, person, model.locateRoute("Editar Projeto"), model, message);
				newDialog.addComplementString("id",getComplementString("id"));
				answer.append("Editando Tipo");
				return finishHim(newDialog);
			}
			if(message.equals("Status")){
				Dialog newDialog = new DialogEditStatusProject(bot, person, model.locateRoute("Editar Projeto"), model, message);
				newDialog.addComplementString("id",getComplementString("id"));
				answer.append("Editando Status");
				return finishHim(newDialog);
			}
			
			
		}
		*/
		
		return null;
	}

}
