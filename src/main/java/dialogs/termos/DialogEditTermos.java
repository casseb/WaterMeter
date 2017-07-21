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

public class DialogEditTermos extends Dialog {

	public DialogEditTermos(TelegramBot bot, Person person, Route route, Model model, String message) {
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
			answer.append("Selecione o tópico do termo que será editado");
			
			prepareKeyboard(keyboard, 2);
			
			return finishStep();		
		}
		
		if(nextStep()) {
			if(keyboard.contains(message) || isValid("topico")) {
				addComplementString("topico");
				
				answer.append("Selecione o Paragrafo que deseja editar");
				
				prepareKeyboard(model.showTermosByTopico(model.termoTopico.locateTermoTopicoByString(getComplementString("topico"))));
				
				return finishStep();
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			if(model.showTermosByTopico(model.termoTopico.locateTermoTopicoByString(getComplementString("topico"))).contains(message)
					|| isValid("descricaoAntiga")) {
				
				addComplementString("descricaoAntiga");
				
				Termo termo = model.locateTermosByTopicoDesc(model.termoTopico.locateTermoTopicoByString(getComplementString("topico")),
						getComplementString("descricaoAntiga"));
				
				if(termo.isOficial()) {
					answer.append("Descrição Oficial: "+message															+"\n");	
				}else {
					answer.append("Descrição Inicial: "+message															+"\n");	
				}
				
				answer.append("Descrição em Homologação: "+termo.getCodigoParagrafo()+" - "+termo.getDescricaoTemp()	+"\n");
				answer.append("\nDigite a nova descrição"																+"\n");
				
				return finishStep();
				
			}else {
				return messageInvalid();
			}
		}
		
		Termo termo = model.locateTermosByTopicoDesc(model.termoTopico.locateTermoTopicoByString(getComplementString("topico")),
				getComplementString("descricaoAntiga"));
		
		
		if(nextStep()) {
			addComplementString("descricaoNova");
			
			answer.append("Confirma os seguintes dados?\n\n");
			answer.append("Tópico: "					+getComplementString("topico")											+"\n");
			if(termo.isOficial()) {
				answer.append("Descrição Oficial: "			+getComplementString("descricaoAntiga")								+"\n");
			}else {
				answer.append("Descrição Inicial: "			+getComplementString("descricaoAntiga")								+"\n");
			}
			
			answer.append("Descrição em Homologação: "	+termo.getCodigoParagrafo()+" - "+termo.getDescricaoTemp()				+"\n");
			answer.append("Descrição Nova: "			+termo.getCodigoParagrafo()+" - "+getComplementString("descricaoNova")	+"\n");
			
			messageConfirmation();
			
			return finishStep();
		}
		
		if(nextStep()) {
			if(isConfirmated()) {
				termo.setDescricaoTemp(getComplementString("descricaoNova"));
				model.editTermo(termo);
				return finishHim();
			}else {
				return finishHim();
			}
		}
		
		return null;
	}

}
