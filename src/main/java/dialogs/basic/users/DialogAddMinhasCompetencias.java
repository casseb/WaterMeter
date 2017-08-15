package dialogs.basic.users;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Competencia;
import objects.CompetenciaTipo;
import objects.basic.Person;
import objects.basic.Route;

public class DialogAddMinhasCompetencias extends Dialog {

	public DialogAddMinhasCompetencias(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	List<CompetenciaTipo> competenciasTipos = new LinkedList<>();
	List<String> competenciasTipoS = new LinkedList<>();
	
	List<Competencia> competencias = new LinkedList<>();
	List<String> competenciasS = new LinkedList<>();
	
	List<String> escolhas = new LinkedList<>();
	List<Competencia> competenciasParaAdicionar = new LinkedList<>();
	int index = 1;
	
	@Override
	public Dialog action() {
		
		if(nextStep()) {
			answer.append("Selecione qual tipo de competência que deseja adicionar ao seu perfil: \n");
			List<Competencia> competencias = new LinkedList<>();
			for(CompetenciaTipo competenciaTipo : CompetenciaTipo.values()) {
				if(model.locateCompetenciaByCompetenciaTipo(competenciaTipo).size()!=0) {
					competencias.addAll(model.locateCompetenciaByCompetenciaTipo(competenciaTipo));
					competencias.removeAll(person.getCompetencias());
					competencias.removeAll(competenciasParaAdicionar);
					if(competencias.size()!=0){
						competenciasTipos.add(competenciaTipo);
					}
				}
			}
			
			for(CompetenciaTipo competenciaTipo : competenciasTipos) {
				competenciasTipoS.add(competenciaTipo.descricao);
			}
			
			
			if(competenciasTipoS.size()!=0) {
				prepareKeyboard(competenciasTipoS);
				return finishStep();
			}else {
				answer.append("Nenhuma competência para adicionar");
				return finishHim();
			}
			
		}
		
		if(nextStep()) {
			if(competenciasTipoS.contains(message) || isValid("competenciaTipo" + index)) {
				addComplementString("competenciaTipo" + index);
				
				answer.append("Selecione a competência que deseja adicionar");
				
				competencias.addAll(model.locateCompetenciaByCompetenciaTipo(model.competenciaTipo.locateCompetenciaTipoByString(getComplementString("competenciaTipo" + index), model)));
				competencias.removeAll(person.getCompetencias());
				competencias.removeAll(competenciasParaAdicionar);
				
				for(Competencia competencia : competencias) {
					competenciasS.add(competencia.getDescricao());
				}
				
				prepareKeyboard(competenciasS);
				
				return finishStep();
				
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			if(competenciasS.contains(message) || isValid("competencia" + index)) {
				addComplementString("competencia" + index);
				
				competenciasParaAdicionar.add(model.locateCompetenciaByTipoDesc(model.competenciaTipo.locateCompetenciaTipoByString(getComplementString("competenciaTipo" + index), model), getComplementString("competencia" + index)));
				
				answer.append("Deseja Selecionar mais competências ou Efetivar a edição?");
				
				escolhas = new LinkedList<>();
				escolhas.add("Selecionar Mais");
				escolhas.add("Efetivar");
				
				prepareKeyboard(escolhas);
				
				return finishStep();
				
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			if(escolhas.contains(message)) {
				if(message.equals("Selecionar Mais")) {
					
					index++;
					
					competenciasTipos = new LinkedList<>();
					competenciasTipoS = new LinkedList<>();
					
					competencias = new LinkedList<>();
					competenciasS = new LinkedList<>();
					
					escolhas = new LinkedList<>();
					competenciasParaAdicionar = new LinkedList<>();
					
					return finishStep(1);
				}
				if(message.equals("Efetivar")) {
					answer.append("Deseja adicionar as seguintes competência ao seu perfil? \n\n");
					
					for (Competencia competencia : competenciasParaAdicionar) {
						answer.append(competencia.getTipo().descricao+" - "+competencia.getDescricao()+"\n");
					}
					
					messageConfirmation();
					
					return finishStep();
				}
			}else {
				return messageInvalid();
			}
		}
		
		if(nextStep()) {
			if(isConfirmated()) {
				Set<Competencia> competenciasFinais = new HashSet<>();
				competenciasFinais.addAll(person.getCompetencias());
				competenciasFinais.addAll(competenciasParaAdicionar);
				person.setCompetencias(competenciasFinais);
				
				model.editPerson(person);
				
				return finishHim();
				
			}else {
				return finishHim();
			}
		}
		return null;
	}

}
