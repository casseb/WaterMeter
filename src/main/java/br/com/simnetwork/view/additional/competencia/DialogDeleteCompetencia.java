package br.com.simnetwork.view.additional.competencia;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.basic.person.Person;
import br.com.simnetwork.model.basic.route.Route;
import br.com.simnetwork.model.competencia.Competencia;
import br.com.simnetwork.model.competencia.CompetenciaTipo;
import br.com.simnetwork.view.basic.Dialog;

public class DialogDeleteCompetencia extends Dialog{

	public DialogDeleteCompetencia(TelegramBot bot, Person person, Route route, Model model, String message) {
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
				answer.append("Selecione o tipo da competência que deseja deletar");
				prepareKeyboard(competenciasTipo);
			}else {
				answer.append("Nenhuma competência para deletar");
				return finishHim();
			}
			
			return finishStep();
			
		}
		
		if(nextStep()) {
			if(competenciasTipo.contains(message) || isValid("tipo")) {
				addComplementString("tipo");
				
				answer.append("Selecione qual competência deseja deletar");
				
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
				
				answer.append("Confirma a exclusão da seguinte competência?\n\n");
				
				competenciaTarget = model.locateCompetenciaByTipoDesc(model.competenciaTipo.locateCompetenciaTipoByString("tipo",model),message);
				answer.append("Tipo: "+competenciaTarget.getTipo().descricao+"\n");
				answer.append("Descrição: "+competenciaTarget.getDescricao()+"\n");
				
				messageConfirmation();
				
				return finishStep();
				
			}else {
				return messageInvalid();
			}
		}
		
		if (nextStep()) {
			if (isConfirmated()) {
				model.deleteCompetencia(competenciaTarget);
				return finishHim();
			} else {
				return finishHim();
			}
		}
		return null;
	}

}
