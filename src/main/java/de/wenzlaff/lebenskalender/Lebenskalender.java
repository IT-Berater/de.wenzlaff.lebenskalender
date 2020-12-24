package de.wenzlaff.lebenskalender;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

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

	/** Wochen pro Monat, abgerundet. */
	private static final int WOCHEN_PRO_MONAT = 4;

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

		DateTimeFormatter deFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDate geburtsDatum = LocalDate.parse(gebDatum, deFormatter);

		LocalDate heute = LocalDate.now();
		if (geburtsDatum.isAfter(heute)) {
			throw new IllegalArgumentException("Das eingegebene Geburtsdatum " + gebDatum + " liegt in der Zukunft. Es muss in der Vergangenheit liegen");
		}
		if (geburtsDatum.isEqual(heute)) {
			throw new IllegalArgumentException("Das eingegebene Geburtsdatum ist der gleiche jetzt. Es muss in der Vergangenheit liegen.");
		}

		int aktuellesAlterJahre = heute.getYear() - geburtsDatum.getYear();
		int aktuellesAlterWochen = (heute.getMonthValue() - geburtsDatum.getMonthValue()) * WOCHEN_PRO_MONAT;
		int maxLebensalter = getMaxLebensalter(isMann);

		List<String> kalender = new ArrayList<>();
		kalender.add(getTitel(gebDatum, heute.format(deFormatter).toString(), aktuellesAlterJahre));
		kalender.addAll(getLebenskalender(aktuellesAlterJahre, aktuellesAlterWochen, maxLebensalter));
		kalender.add(getFuss(aktuellesAlterJahre, isMann, maxLebensalter));

		Druck.printSystemOut(kalender);
		Druck.printPdfDokument(kalender);
	}

	private static List<String> getLebenskalender(int aktuellesAlterJahre, int aktuellesAlterWochen, int maxLebensalter) {

		List<String> kalender = new ArrayList<>();

		int jahr = 0;
		// 1. volle Zeile für jedes volle Jahr
		for (; jahr < aktuellesAlterJahre; jahr++) {
			kalender.add(Zeile.getVolleZeile(jahr));
		}

		// 2. rest Monate
		kalender.add(new Zeile(jahr++, ++aktuellesAlterWochen).toString());

		// 3. auffüllen mit leer Zeilen
		for (; jahr < maxLebensalter + 1; jahr++) {
			kalender.add(Zeile.getLeerZeile(jahr));
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

	private static String getGeschlecht(boolean isMann) {
		if (isMann) {
			return "Mann";
		} else {
			return "Frau";
		}
	}

	private static String getTitel(String gebDatum, String heute, int aktuellesAlterJahre) {
		StringBuffer b = new StringBuffer();
		b.append(TABULATOR);
		b.append("                   Lebenskalender");
		b.append(NEUE_ZEILE);
		b.append(TABULATOR);
		b.append("Geburtsdatum " + gebDatum + " Alter " + aktuellesAlterJahre + " Jahre am " + heute);
		b.append(NEUE_ZEILE);
		return b.toString();
	}

	private static String getFuss(int alterJahre, boolean isMann, int maxLebensalter) {
		StringBuffer b = new StringBuffer();
		b.append(NEUE_ZEILE);
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
}