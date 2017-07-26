package dialogs.termos;

import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Termo;
import objects.TermoTopico;
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
		List<Termo> todos = model.locateAllTermos();
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


			if (!criados.isEmpty() && !modificados.isEmpty() && !deletados.isEmpty()) {
				answer.append("Abaixo todas as modificações realizadas:");

				if (!criados.isEmpty()) {
					answer.append("\n\nTermos Criados:\n\n");
					for (Termo termo : criados) {
						answer.append(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n");
					}
				}

				if (!modificados.isEmpty()) {
					answer.append("\n\nTermos Modificados:\n\n");
					for (Termo termo : modificados) {
						answer.append("Antes:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
								+ " - " + termo.getDescricao() + "\n");
						answer.append("Depois:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
								+ " - " + termo.getDescricaoTemp() + "\n\n");
					}
				}

				if (!deletados.isEmpty()) {
					answer.append("\n\nTermos Deletados:\n\n");
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

				// Gerando pdf sobre as alterações ocorridas

				List<Chunk> chunks = new LinkedList<>();

				chunks.add(new Chunk("Relatório de alterações dos termos\n", fontTitle));
				chunks.add(new Chunk("Gerado as " + LocalDateTime.now().toString() + "\n\n", fontSubTitle));

				if (!criados.isEmpty()) {
					chunks.add(new Chunk("\n\nTermos Criados\n\n", fontSubTitle));
					for (Termo termo : criados) {
						chunks.add(new Chunk(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n", fontSimpleText));
					}
				}

				if (!modificados.isEmpty()) {
					chunks.add(new Chunk("\n\nTermos Modificados:\n\n", fontSubTitle));
					for (Termo termo : modificados) {
						chunks.add(new Chunk("Antes:" + termo.getTopico().descricao + " -- "
								+ termo.getCodigoParagrafo() + " - " + termo.getDescricao() + "\n", fontSimpleText));
						chunks.add(
								new Chunk("Depois:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
										+ " - " + termo.getDescricaoTemp() + "\n\n", fontSimpleText));
					}
				}

				if (!deletados.isEmpty()) {
					chunks.add(new Chunk("\n\nTermos Deletados:\n\n", fontSubTitle));
					for (Termo termo : deletados) {
						chunks.add(new Chunk(termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo() + " - "
								+ termo.getDescricao() + "\n", fontSimpleText));
					}
				}

				createPDF("Alterações Termos " + LocalDateTime.now().toString(), chunks, "Termos", "Alterações");

				// Atualizando no banco os status dos termos
				for (Termo termo : criados) {
					termo.setOficial(true);
					termo.setCriado(false);
					termo.setModificado(false);
					termo.setDeletado(false);
					model.editTermo(termo);
					model.plusEstructureTermo();
				}

				for (Termo termo : modificados) {
					termo.setDescricao(termo.getDescricaoTemp());
					termo.setOficial(true);
					termo.setCriado(false);
					termo.setModificado(false);
					termo.setDeletado(false);
					model.editTermo(termo);
					model.plusModifiedTermo();
				}

				for (Termo termo : deletados) {
					model.deleteTermo(termo);
					model.plusEstructureTermo();
				}

				// Gerando pdf dos termos oficiais

				chunks = new LinkedList<>();

				chunks.add(new Chunk("Termos da Iniciativa Sim Network\n", fontTitle));
				chunks.add(new Chunk("Versão: " + model.administracao.versaoTermo() + "\n", fontSubTitle));

				for (TermoTopico termoTopico : model.termoTopico.values()) {
					model.enumerateTopicos(termoTopico);
					List<Termo> termos = new LinkedList<>();
					termos.addAll(model.locateTermosByTopicoOficiais(termoTopico));
					termos.removeAll(model.locateUnofficialTermos());
					if (termos.size() != 0) {
						chunks.add(new Chunk("\n\n" + termoTopico.descricao + "\n\n", fontSubTitle));
						for (Termo termo : termos) {
							chunks.add(new Chunk("\n" + termo.getCodigoParagrafo(), fontBold));
							chunks.add(new Chunk(termo.getDescricao() + "\n", fontSimpleText));
						}
					}
				}

				createPDF("Termos", chunks, "Termos");
				createPDF("Termos - " + model.administracao.versaoTermo(), chunks, "Termos", "Versões");

				// Avisando todos os Parceiros
				sendMessages("Prezado, Acabou de ser gerado uma nova versão dos termos da iniciativa, peço que "
						+ "entre na rota Termos -> Aceitar para conferir as mudanças e aceitar as alterações realizadas",
						model.persons);

				// Deixando todos os aceites como false para forçar o novo aceite dos termos
				// para todos os usuários
				for (Person person : model.persons) {
					person.setTermoAceito(false);
					model.editPerson(person);
				}

				return finishHim();

			} else {
				return finishHim();
			}

		}return null;
}

}
