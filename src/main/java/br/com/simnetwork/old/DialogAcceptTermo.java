package br.com.simnetwork.old;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class DialogAcceptTermo extends Dialog {

	public DialogAcceptTermo(TelegramBot bot, Usuario person, Rota route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		if(nextStep()) {
			if(!person.isTermoAceito()) {
				answer.append("Deseja aceitar os termos vigentes?");
				sendFile(model.box.getFileByBox(model.locateBoxFileObjectByName("Termos - " + model.administracao.versaoTermo()+".pdf", "Termos", "Versões")));
				sendFile(model.box.getFileByBox(model.locateBoxFileObjectByName(model.administracao.getArquivoUltimaAlteracaoTermos()+".pdf", "Termos", "Alterações")));
				messageConfirmation();
				return finishStep();
			}else {
				answer.append("Já foi realizado o aceite dos termos mais atualizados");
				return finishHim();
			}
		}
		
		if(nextStep()) {
			if(isConfirmated()) {
				person.setTermoAceito(true);
				model.editPerson(person);
				return finishHim();
			}else {
				return finishHim();
			}
		}
		return null;
	}

}
