package de.wenzlaff.lebenskalender;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Erzeugt einen Lebenskalender auf System.out und erzeugt eine PDF mit dem
 * Lebenskalender.
 *
 * @author Thomas Wenzlaff
 */
public final class Lebenskalender {

	// Stand 2018
	// https://de.wikipedia.org/wiki/Lebenserwartung#Beispiel_Deutschland

	// Lebenserwartung Mann in Deutschland 78 Jahre 5 Monate - aufgerundet
	private static final int MAX_LEBENSERWARTUNG_JAHRE_MANN = 78;
	private static final int MAX_LEBENSERWARTUNG_MONATE_MANN = 5;

	// Lebenserwartung Frau in Deutschland 83 Jahre 3 Monate - aufgerundet
	private static final int MAX_LEBENSERWARTUNG_JAHRE_FRAU = 83;
	private static final int MAX_LEBENSERWARTUNG_MONATE_FRAU = 3;

	private static final String TABULATOR = "        ";
	private static final String NEUE_ZEILE = "\n\r";
	private static final String WOCHEN_ZEICHEN_VERGANGEN = "X";
	private static final String WOCHEN_ZEICHEN_ZUKUNFT = ".";

	/** Die Anzahl der Wochen im Jahr. */
	private static final int MAX_WOCHEN = 52;
	/** Wochen pro Monat, abgerundet. */
	private static final int WOCHEN_PRO_MONAT = 4;

	/**
	 * Eine Zeile die das Darstellt: Jahr Wochen
	 * 
	 * <pre>
	0        XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	1        XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	2        ....................................................
	3        ....................................................
	4        ....................................................
	5        ....................................................
	6        ....................................................
	7        ....................................................
	8        ....................................................
	9        ....................................................
	10        ....................................................
	 * </pre>
	 * 
	 * @author Thomas Wenzlaff
	 *
	 */
	private class Zeile {

		private String inhalt;
	}

	/**
	 * Aufruf der Klasse: [mit Geburtsdatum in der Form dd.mm.yyyy] [ob Mann ist
	 * dann true]
	 * 
	 * Z.b für 01.11.1970 true
	 * 
	 * für einen Mann der am 1.11.1970 geboren ist
	 * 
	 * @param args drei Argumente, [mit Geburtsdatum in der Form dd.mm.yyyy] [Mann
	 *             dann true, Frau dann false]
	 * @throws Exception bei Fehler
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("Programm Aufruf [mit Geburtsdatum in der Form dd.mm.yyyy] [ob Mann ist dann true]  "
					+ "Z.b für einen Mann der am 1.11.1970 geboren ist 01.11.1970 true oder für eine Frau die am 7.2.2020 geboren ist 07.02.2020 false");
			return;
		}
		String gebDatum = args[0];
		boolean isMann = Boolean.valueOf(args[1]);

		generate(gebDatum, isMann);
	}

	public static void generate(String gebDatum, boolean isMann) throws DocumentException, FileNotFoundException {

		int maxLebensalter = getMaxLebensalter(isMann);

		DateTimeFormatter deFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDate heute = LocalDate.now();

		List<String> kalender = new ArrayList<>();
		kalender.add(getTitel(gebDatum, heute.format(deFormatter).toString()));

		LocalDate geburtsDatum = LocalDate.parse(gebDatum, deFormatter);

		if (geburtsDatum.isAfter(heute)) {
			throw new IllegalArgumentException("Das eingegebene Geburtsdatum " + gebDatum + " liegt in der Zukunft. Es muss in der Vergangenheit liegen");
		}
		if (geburtsDatum.isEqual(heute)) {
			throw new IllegalArgumentException("Das eingegebene Geburtsdatum ist der gleiche jetzt. Es muss in der Vergangenheit liegen.");
		}

		int aktuellesAlterJahre = heute.getYear() - geburtsDatum.getYear();
		int aktuellesAlterMonate = heute.getMonthValue() - geburtsDatum.getMonthValue();

		kalender.addAll(getLebenskalender(aktuellesAlterJahre, aktuellesAlterMonate, maxLebensalter));
		kalender.add(getFuss(aktuellesAlterJahre, isMann, maxLebensalter));

		printSystemOut(kalender);
		printPdfDokument(kalender);
	}

	private static List<String> getLebenskalender(int aktuellesAlterJahre, int aktuellesAlterMonate, int maxLebensalter) {

		System.out.println("Aktuelles Alter in Jahren: " + aktuellesAlterJahre + " und Monate: " + aktuellesAlterJahre);

		List<String> kalender = new ArrayList<>();

		for (int i = 0; i < maxLebensalter; i++) {
			String eineZeile = "";
			if (i <= 9) {
				eineZeile = " " + i + TABULATOR;
			} else {
				eineZeile = i + TABULATOR;
			}

			for (int j = 0; j < MAX_WOCHEN; j++) {

				if (i <= aktuellesAlterJahre) {
					eineZeile += WOCHEN_ZEICHEN_VERGANGEN;
				} else {
					eineZeile += WOCHEN_ZEICHEN_ZUKUNFT;
				}

				if (i == aktuellesAlterJahre + 1) {
					String jahrWochen = i + TABULATOR;
					for (int k = 0; k < aktuellesAlterMonate * WOCHEN_PRO_MONAT; k++) {
						jahrWochen += WOCHEN_ZEICHEN_VERGANGEN;
					}
					for (int k = 0; k < MAX_WOCHEN - aktuellesAlterMonate * WOCHEN_PRO_MONAT; k++) {
						jahrWochen += WOCHEN_ZEICHEN_ZUKUNFT;
					}
					eineZeile = jahrWochen;
				}
			}
			kalender.add(eineZeile);
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

	private static String getTitel(String gebDatum, String heute) {
		StringBuffer b = new StringBuffer();
		b.append(TABULATOR);
		b.append("                   Lebenskalender");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Verwende Geburtsdatum " + gebDatum + " am " + heute);
		b.append(NEUE_ZEILE);
		return b.toString();
	}

	private static String getFuss(int alterJahre, boolean isMann, int maxLebensalter) {
		StringBuffer b = new StringBuffer();
		b.append(TABULATOR);
		b.append("  Lebenserwartung in Deutschland:");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Männer " + MAX_LEBENSERWARTUNG_JAHRE_MANN + " Jahre " + MAX_LEBENSERWARTUNG_MONATE_MANN + " Monate");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("  Frauen " + MAX_LEBENSERWARTUNG_JAHRE_FRAU + " Jahre " + MAX_LEBENSERWARTUNG_MONATE_FRAU + " Monate");

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