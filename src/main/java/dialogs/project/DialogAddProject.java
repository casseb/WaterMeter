package dialogs.project;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Project;
import objects.ProjectType;
import objects.basic.Person;
import objects.basic.Route;

public class DialogAddProject extends Dialog{

	public DialogAddProject(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
	}

	@Override
	public Dialog action() {
		
		if(nextStep()){
			answer.append("Digite o nome do seu projeto");
			return finishStep();
		}
		
		if(nextStep()){
			addComplementString("title");
			answer.append("Digite uma breve descrição do projeto");
			return finishStep();
		}
		
		if(nextStep()){
			addComplementString("description");
			answer.append("Pressione o tipo do projeto");
			prepareKeyboard((model.showProjectTypes()));
			return finishStep();
		}
		
		if(nextStep()){
			if(!model.projectType.isProjectTypeValid(message)) return messageInvalid(); 	
			addComplementString("type",message);
			answer.append("Confirme os seguintes dados da inclusão");
			answer.append("\n");
			answer.append("Título: "+getComplementString("title"));
			answer.append("\n");
			answer.append("Descrição: "+getComplementString("description"));
			answer.append("\n");
			answer.append("Tipo: "+getComplementString("type"));
			answer.append("\n");
			messageConfirmation();
			return finishStep();
		}
		
		if(nextStep()){
			if(isConfirmated()){
				Project project = model.addProjectByTelegram(getComplementString("title"), getComplementString("description"), 
						model.projectType.locateProjectTypeByDesc(getComplementString("type")), person);
				answer.append("Projeto número: "+project.getId()+" criado com sucesso");
				return finishHim(null);
			}else{
				return finishHim(null);
			}
		}
		return null;
	}

}
