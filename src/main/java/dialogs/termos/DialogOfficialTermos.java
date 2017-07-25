package dialogs.termos;

import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Termo;
import objects.basic.Person;
import objects.basic.Route;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class DialogOfficialTermos extends Dialog {

	public DialogOfficialTermos(TelegramBot bot, Person person, Route route, Model model, String message) {
		super(bot, person, route, model, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog action() {
		List<Termo> todos = model.locateUnofficialTermos();
		List<Termo> criados = new LinkedList();
		List<Termo> modificados = new LinkedList();
		List<Termo> deletados = new LinkedList();

		for (Termo termo : todos) {
			if (!termo.isDeletado()) {
				if (termo.isCriado()) {
					criados.add(termo);
				}
				if (termo.isModificado()) {
					modificados.add(termo);
				}
			} else {
				deletados.add(termo);
			}
		}

		if (nextStep()) {

			if (!todos.isEmpty()) {
				answer.append("Abaixo todas as modificações realizadas:\n\n");

				if (!criados.isEmpty()) {
					answer.append("Termos Criados:\n\n");
					for (Termo termo : criados) {
						answer.append(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n");
					}
				}

				if (!modificados.isEmpty()) {
					answer.append("Termos Modificados:\n\n");
					for (Termo termo : modificados) {
						answer.append("Antes:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
								+ " - " + termo.getDescricao() + "\n");
						answer.append("Depois:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
								+ " - " + termo.getDescricaoTemp() + "\n\n");
					}
				}

				if (!deletados.isEmpty()) {
					answer.append("Termos Deletados:\n\n");
					for (Termo termo : deletados) {
						answer.append(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n");
					}
				}

				messageConfirmation();

				return finishStep();
			} else {
				answer.append("Nenhuma alteração aguardando aprovação\n");
				return finishHim();
			}

		}

		if (nextStep()) {
			if (isConfirmated()) {

				//Gerando pdf sobre as alterações ocorridas
				answer.append("Segue pdf sobre alterações realizadas\n");

				List<Chunk> chunks = new LinkedList<>();

				chunks.add(new Chunk("Relatório de alterações dos termos\n", fontTitle));
				chunks.add(new Chunk("Gerado as " + LocalDateTime.now().toString() + "\n\n\n\n", fontSubTitle));

				if (!criados.isEmpty()) {
					chunks.add(new Chunk("Termos Criados\n\n", fontSubTitle));
					for (Termo termo : criados) {
						chunks.add(new Chunk(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n", fontSimpleText));
					}
				}

				if (!modificados.isEmpty()) {
					chunks.add(new Chunk("Termos Modificados:\n\n", fontSubTitle));
					for (Termo termo : modificados) {
						chunks.add(new Chunk("Antes:" + termo.getTopico().descricao + " -- "
								+ termo.getCodigoParagrafo() + " - " + termo.getDescricao() + "\n", fontSimpleText));
						chunks.add(
								new Chunk("Depois:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
										+ " - " + termo.getDescricaoTemp() + "\n\n", fontSimpleText));
					}
				}

				if (!deletados.isEmpty()) {
					chunks.add(new Chunk("Termos Deletados:\n\n", fontSubTitle));
					for (Termo termo : deletados) {
						chunks.add(new Chunk(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n", fontSimpleText));
					}
				
				createPDF("Alterações Termos " + LocalDateTime.now().toString(), chunks, "Termos", "Alterações");
					
				//Atualizando no banco os status dos termos
				for (Termo termo : criados) {
					termo.setOficial(true);
					termo.setModificado(false);
					termo.setDeletado(false);
					model.editTermo(termo);
				}

				for (Termo termo : modificados) {
					termo.setDescricao(termo.getDescricaoTemp());
					termo.setOficial(true);
					termo.setModificado(false);
					termo.setDeletado(false);
					model.editTermo(termo);
				}

				for (Termo termo : deletados) {
					model.deleteTermo(termo);
				}

				//Avisando todos os Parceiros
				sendMessages("Prezado, Acabou de ser gerado uma nova versão dos termos da iniciativa, peço que "
							+ "entre na rota Termos -> Aceitar para conferir as mudanças e aceitar as alterações realizadas",
							model.persons);
				
				//Deixando todos os aceites como false para forçar o novo aceite dos termos para todos os usuários
				for (Person person : model.persons) {
					person.setTermoAceito(false);
					model.editPerson(person);
				}
				
					return finishHim();
				} else {
					return finishHim();
				}
			}
		}
		return null;
	}
}
