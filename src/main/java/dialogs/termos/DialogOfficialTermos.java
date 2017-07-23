package dialogs.termos;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;

import dialogs.basic.structure.Dialog;
import mvc.Model;
import objects.Termo;
import objects.basic.Person;
import objects.basic.Route;
import java.io.FileOutputStream;
import java.io.IOException;

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
					for (Termo termo : criados) {
						answer.append("Antes:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
								+ " - " + termo.getDescricao() + "\n");
						answer.append("Depois:" + termo.getTopico().descricao + " -- " + termo.getCodigoParagrafo()
								+ " - " + termo.getDescricaoTemp() + "\n\n");
					}
				}

				if (!deletados.isEmpty()) {
					answer.append("Termos Deletados:\n\n");
					for (Termo termo : criados) {
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
				
			}
		}

				/*
				Document document = new Document();
				
				try {
					PdfWriter.getInstance(document, new FileOutputStream("E:/Temp/PDF_DevMedia.pdf"));
					document.open();
					
					document.add(new Paragraph("Gerando PDF - Java"));
				} catch (DocumentException de) {
					System.err.println(de.getMessage());
				} catch (IOException ioe) {
					System.err.println(ioe.getMessage());
				}
				document.close();

			} else {
				return finishHim();
			}
			
			}
			
			
			*/
		
		return null;
			}

}
