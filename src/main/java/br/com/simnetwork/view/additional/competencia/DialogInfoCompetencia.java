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

public class DialogInfoCompetencia extends Dialog {

	public DialogInfoCompetencia(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	List<String> competenciasTipo = new LinkedList<>();

	@Override
	public Dialog action() {
		if (nextStep()) {

			for (CompetenciaTipo competenciaTipo : CompetenciaTipo.values()) {
				if (!model.locateCompetenciaByCompetenciaTipo(competenciaTipo).isEmpty()) {
					competenciasTipo.add(competenciaTipo.descricao);
				}
			}

			if (!competenciasTipo.isEmpty()) {
				answer.append("Selecione o tipo da competências que deseja consultar");
				prepareKeyboard(competenciasTipo);
			} else {
				answer.append("Nenhuma competência para consultar");
				return finishHim();
			}

			return finishStep();

		}
		
		if(nextStep()) {
			if(competenciasTipo.contains(message) || isValid("tipo")) {
				addComplementString("tipo");
				
				answer.append("Abaixo características das competências da área");
				
				for(Competencia competencia : model.locateCompetenciaByCompetenciaTipo(model.competenciaTipo.locateCompetenciaTipoByString(message,model))) {
					answer.append("Tipo: "	+	competencia.getTipo().descricao	+	"\n");
					answer.append("Descrição: "	+	competencia.getDescricao()	+	"\n");
					answer.append("Valor pelo serviço prestado (R$): "	+	competencia.valorFormatado()+ " " +	competencia.getUn().descricao	+	"\n");
					answer.append("Valor pelo serviço prestado (Inicoins): "	+	competencia.valorInicFormatado()+ " " +	competencia.getUn().descricao	+	"\n\n");
				}
				
				
				return finishHim();
				
			}else {
				return messageInvalid();
			}
		}
		
		return null;
	}

}