package de.wenzlaff.lebenskalender;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * PDF Druck.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class Druck {

	public static void printPdfDokument(List<String> kalender) throws DocumentException, FileNotFoundException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream("lebenskalender.pdf"));
		document.open();

		Font f = new Font();
		f.setFamily("Courier");
		f.setSize(10);

		for (String woche : kalender) {
			document.add(new Paragraph(woche, f));
		}
		document.close();
	}

	public static void printSystemOut(List<String> kalender) {
		for (String woche : kalender) {
			System.out.println(woche);
		}
	}
}
