package dialogs.competencia;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Competencia;
import objects.CompetenciaTipo;
import objects.basic.Person;
import objects.basic.Route;

public class DialogEditCompetencia extends Dialog {

	public DialogEditCompetencia(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	List<String> competenciasTipo = new LinkedList<>();
	List<String> competencias = new LinkedList<>();
	Competencia competenciaTarget = null;
	
	@Override
	public Dialog action() {
				
		if(nextStep()) {

			
			for(CompetenciaTipo competenciaTipo : model.competenciaTipo.values()) {
				if(!model.locateCompetenciaByCompetenciaTipo(competenciaTipo).isEmpty()) {
					competenciasTipo.add(competenciaTipo.descricao);
				}
			}
			
			if(!competenciasTipo.isEmpty()) {
				answer.append("Selecione o tipo da competência que deseja editar");
				prepareKeyboard(competenciasTipo);
			}else {
				answer.append("Nenhuma competência para editar");
				return finishHim();
			}
			
			return finishStep();
			
		}
		
		if(nextStep()) {
			if(competenciasTipo.contains(message) || isValid("tipo")) {
				addComplementString("tipo");
				
				answer.append("Selecione qual competência deseja editar");
				
				for(Competencia competencia : model.locateCompetenciaByCompetenciaTipo(model.competenciaTipo.locateCompetenciaTipoByString(message,model))) {
					competencias.add(competencia.getDescricao());
				}
				
				prepareKeyboard(competencias);
				
				return finishStep();
				
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			if(competencias.contains(message) || isValid("competencia")) {
				addComplementString("competencia");
				competenciaTarget = model.locateCompetenciaByTipoDesc(model.competenciaTipo.locateCompetenciaTipoByString("tipo",model),message);
				
				answer.append("Deseja editar descrição?\n");
				answer.append("Descrição atual: " + competenciaTarget.getDescricao());
				
				return finishStep(competenciaTarget.getDescricao());
				
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			addComplementString("novaDescricao");
			
			answer.append("Deseja editar valor unitário?\n");
			answer.append("Valor atual: " + competenciaTarget.getValor());
			
			return finishStep(competenciaTarget.getValor()+"");
			
		}
		
		if(nextStep()) {
			addComplementString("novoValor");
			Double novoValor;
			try {
				novoValor = Double.parseDouble(getComplementString("novoValor"));
			} catch (Exception e) {
				return messageInvalid();
			}
			
			answer.append("Confirma os seguintes dados?\n\n");
			answer.append("Descrição: " + getComplementString("novaDescricao") + "\n");
			answer.append("Valor unitário: " + getComplementString("novoValor") + "\n");
			
			messageConfirmation();
			
			return finishStep();

		}
		
		if (nextStep()) {
			if (isConfirmated()) {
				competenciaTarget.setDescricao(getComplementString("novaDescricao"));
				competenciaTarget.setValor(Double.parseDouble(getComplementString("novoValor")));
				model.editCompetencia(competenciaTarget);
				return finishHim();
			} else {
				return finishHim();
			}
		}
		return null;
	}

}
