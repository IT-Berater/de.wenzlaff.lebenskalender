package de.wenzlaff.lebenskalender;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Erzeugt einen Lebenskalender auf System.out und erzeugt eine PDF mit dem Lebenskalender.
 * 
 * @author Thomas Wenzlaff
 */
public final class Lebenskalender {

	// Stand 2015
	// https://de.wikipedia.org/wiki/Lebenserwartung#Beispiel_Deutschland
	// Lebenserwartung Mann in Deutschland 77 Jahre 9 Monate - aufgerundet
	private static final int MAX_LEBENSERWARTUNG_JAHRE_MANN = 78;
	// Lebenserwartung Frau in Deutschland 82 Jahre 10 Monate - aufgerundet
	private static final int MAX_LEBENSERWARTUNG_JAHRE_FRAU = 83;

	private static final String TABULATOR = "        ";
	private static final String NEUE_ZEILE = "\n\r";
	private static final String WOCHEN_ZEICHEN_VERGANGEN = "X";
	private static final String WOCHEN_ZEICHEN_ZUKUNFT = ".";
	/** Die Anzahl der Wochen im Jahr. */
	private static final int MAX_WOCHEN = 52;
	/** Wochen pro Monat, abgerundet. */
	private static final int WOCHEN_PRO_MONAT = 4;

	/**
	 * Aufruf der Klasse: [mit aktuellem Jahr] [Monat] [ob Mann ist dann true] 
	 * 
	 * Z.b für 50 Jahre und 5 Monate als Mann. 50 5 true
	 * 
	 * @param args
	 *            drei Argumente, [mit aktuellem Jahr] [Monat] [Mann dann true, Frau dann false]
	 * @throws Exception
	 *             bei Fehler
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 3) {
			System.out.println("Programm Aufruf [mit aktuellem Jahr] [Monat] [ob Mann ist dann true]  Z.b für 50 Jahre und 5 Monate als Mann. 50 5 true");
			return;
		}
		int aktuelles_alter_in_jahre = Integer.valueOf(args[0]);
		int aktuelles_alter_in_monate = Integer.valueOf(args[1]);
		boolean isMann = Boolean.valueOf(args[2]);

		generate(aktuelles_alter_in_jahre, aktuelles_alter_in_monate, isMann);
	}

	public static void generate(int aktuellesAlterJahre, int aktuellesAlterMonate, boolean isMann) throws DocumentException, FileNotFoundException {

		int maxLebensalter = getMaxLebensalter(isMann);

		List<String> kalender = new ArrayList<String>();

		kalender.add(getTitel(aktuellesAlterJahre, aktuellesAlterMonate));
		kalender.addAll(getLebenskalender(aktuellesAlterJahre, aktuellesAlterMonate, maxLebensalter));
		kalender.add(getFuss(aktuellesAlterJahre, isMann, maxLebensalter));

		printSystemOut(kalender);
		printPdfDokument(kalender);
	}

	private static List<String> getLebenskalender(int aktuellesAlterJahre, int aktuellesAlterMonate, int maxLebensalter) {

		List<String> kalender = new ArrayList<String>();

		for (int i = 0; i < maxLebensalter; i++) {
			String jahr = "";
			if (i <= 9) {
				jahr = " " + i + TABULATOR;
			} else {
				jahr = i + TABULATOR;
			}

			for (int j = 0; j < MAX_WOCHEN; j++) {

				if (i <= aktuellesAlterJahre) {
					jahr += WOCHEN_ZEICHEN_VERGANGEN;
				} else {
					jahr += WOCHEN_ZEICHEN_ZUKUNFT;
				}

				if (i == aktuellesAlterJahre + 1) {
					String jahrWochen = i + TABULATOR;
					for (int k = 0; k < aktuellesAlterMonate * WOCHEN_PRO_MONAT; k++) {
						jahrWochen += WOCHEN_ZEICHEN_VERGANGEN;
					}
					for (int k = 0; k < MAX_WOCHEN - aktuellesAlterMonate * WOCHEN_PRO_MONAT; k++) {
						jahrWochen += WOCHEN_ZEICHEN_ZUKUNFT;
					}
					jahr = jahrWochen;
				}
			}
			kalender.add(jahr);
		}
		return kalender;
	}

	private static int getMaxLebensalter(boolean isMann) {
		int maxLebensalter;

		if (isMann) {
			maxLebensalter = MAX_LEBENSERWARTUNG_JAHRE_MANN;
		} else {
			maxLebensalter = MAX_LEBENSERWARTUNG_JAHRE_FRAU;
		}
		return maxLebensalter;
	}

	private static String getTitel(int alterJahre, int alterMonate) {
		StringBuffer b = new StringBuffer();
		b.append(TABULATOR);
		b.append("                   Lebenskalender");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Berechne von aktuellem Alter " + alterJahre + " Jahren und " + alterMonate + " Monate");
		b.append(NEUE_ZEILE);
		b.append("Jahre");
		b.append(TABULATOR);
		b.append("                   Wochen");
		return b.toString();
	}

	private static String getFuss(int alterJahre, boolean isMann, int maxLebensalter) {
		StringBuffer b = new StringBuffer();
		b.append(TABULATOR);
		b.append("  Lebenserwartung in Deutschland:");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Männer 77 Jahre 9 Monate");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Frauen 82 Jahre 10 Monate");

		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Statistisch also noch: " + (maxLebensalter - alterJahre + 1) + " Jahre als " + getGeschlecht(isMann) + " zu leben!");
		return b.toString();
	}

	private static String getGeschlecht(boolean isMann) {
		if (isMann) {
			return "Mann";
		} else {
			return "Frau";
		}
	}

	private static void printSystemOut(List<String> kalender) {
		for (String woche : kalender) {
			System.out.println(woche);
		}
	}

	private static void printPdfDokument(List<String> kalender) throws DocumentException, FileNotFoundException {
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

}
