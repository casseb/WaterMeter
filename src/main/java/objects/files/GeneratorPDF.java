package objects.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import access.AccessConfiguration;
import mvc.Model;

public class GeneratorPDF {

	public static void main(String[] args) {
		// criação do documento

		try {
			Document document = new Document();
			File file = new File("src/main/java/objects/basic/tempFiles/PDF_DevMedia.pdf");
			OutputStream fileOut = new FileOutputStream(file);

			Font font20 = new Font();
			font20.setSize(20);
			Font fontbold = new Font();
			fontbold.setStyle(fontbold.BOLD);

			Paragraph paragraph1 = new Paragraph();
			paragraph1.add(new Chunk("Teste1", font20));
			Paragraph paragraph2 = new Paragraph();
			paragraph2.add(new Chunk("Teste2", fontbold));
			Paragraph paragraph3 = new Paragraph();
			paragraph3.add("Teste3\n");
			
			PdfWriter.getInstance(document, fileOut);
			System.out.println("XZS");
			document.open();
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph3);
			document.close();
			
			
			Model model = new Model();
			
			List<String> folder = new LinkedList();
			folder.add("Pasta1");
			folder.add("Pasta2");
			BoxFolderObject currentFolder = null;
			BoxFolderObject rootFolder = null;

			for (String string : folder) {
				currentFolder = model.locateBoxFolderObjectByName(string);
				if (currentFolder == null) {
					currentFolder = new BoxFolderObject(string, model, rootFolder);
				}
				rootFolder = currentFolder;
			}
			
			model.addBoxFileObject(new BoxFileObject("Teste1",model,file,currentFolder));
			

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

	}
}