package dialogs.termos;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Termo;
import objects.TermoTopico;
import objects.basic.Person;
import objects.basic.Route;

public class DialogDeleteTermos extends Dialog {

	public DialogDeleteTermos(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		List<String> keyboard = new LinkedList<>();
		for (TermoTopico topico : model.termoTopico.values()) {
			if(model.showTermosByTopico(topico).size()!=0) {
				keyboard.add(topico.descricao);
			}
		}
		
		if(nextStep()) {
			answer.append("Selecione o tópico do termo que será deletado");
			
			prepareKeyboard(keyboard, 2);
			
			return finishStep();		
		}
		
		if(nextStep()) {
			if(keyboard.contains(message)|| isValid("topico")) {
				addComplementString("topico");
				
				answer.append("Selecione o Paragrafo que deseja deletar");
				
				prepareKeyboard(model.showTermosByTopico(model.termoTopico.locateTermoTopicoByString(getComplementString("topico"))));
				
				return finishStep();
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			if(model.showTermosByTopico(model.termoTopico.locateTermoTopicoByString(getComplementString("topico"))).contains(message)
					|| isValid("descricao")) {
				
				addComplementString("descricao");
				
				Termo termo = model.locateTermosByTopicoDesc(model.termoTopico.locateTermoTopicoByString(getComplementString("topico")),
						getComplementString("descricao"));
				
				answer.append("Confirma a exclusão do seguinte parágrafo?\n\n");
				answer.append("Tópico: "					+getComplementString("topico")											+"\n");
				if(termo.isOficial()) {
					answer.append("Descrição Oficial: "			+getComplementString("descricaoAntiga")								+"\n");
				}else {
					answer.append("Descrição Inicial: "			+getComplementString("descricaoAntiga")								+"\n");
				}
				answer.append("Descrição em Homologação: "	+termo.getCodigoParagrafo()+" - "+termo.getDescricaoTemp()				+"\n");
				
				messageConfirmation();
				
				return finishStep();
				
			}else {
				return messageInvalid();
			}
		}
		
		Termo termo = model.locateTermosByTopicoDesc(model.termoTopico.locateTermoTopicoByString(getComplementString("topico")),
				getComplementString("descricao"));
		
		
		if(nextStep()) {
			if(isConfirmated()) {
				termo.setDeletado(true);
				model.editTermo(termo);
				return finishHim();
			}else {
				return finishHim();
			}
		}
		
		return null;
	}

}
