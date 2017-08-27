package br.com.simnetwork.old;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import br.com.simnetwork.model.Model;
import br.com.simnetwork.model.entity.basico.rota.Rota;
import br.com.simnetwork.model.entity.basico.usuario.Usuario;

public class DialogReadTermos extends Dialog {

	public DialogReadTermos(TelegramBot bot, Usuario person, Rota route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		List<String> keyboard = new LinkedList<>();
		for (TermoTopico topico : model.termoTopico.values()) {
			if (model.showTermosByTopico(topico).size() != 0) {
				keyboard.add(topico.descricao);
			}
		}

		if (nextStep()) {
			answer.append("Selecione o tópico do termo que deseja ler");
			sendFile(model.box.getFileByBox(model.locateBoxFileObjectByName("Termos - " + model.administracao.versaoTermo()+".pdf", "Termos", "Versões")));
			sendFile(model.box.getFileByBox(model.locateBoxFileObjectByName(model.administracao.getArquivoUltimaAlteracaoTermos()+".pdf", "Termos", "Alterações")));
			prepareKeyboard(keyboard, 2);

			return finishStep();
		}

		if (nextStep()) {
			if (keyboard.contains(message)) {

				List<Termo> termos = model.locateTermosByTopicoOficiais(model.termoTopico.locateTermoTopicoByString(message));
				if(termos.size()!=0) {
					for (Termo termo : termos) {
						answer.append(termo.getCodigoParagrafo()+" - "+termo.getDescricao()+"\n");
					}
				}else {
					answer.append("Não há paragrafos oficiais");
				}
				
				return finishHim();
			} else {
				return messageInvalid();
			}
		}

		return null;
	}

}
