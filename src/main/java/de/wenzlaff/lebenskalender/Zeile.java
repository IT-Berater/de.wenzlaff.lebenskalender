package de.wenzlaff.lebenskalender;

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
 */
public class Zeile {

	private static final String WOCHEN_ZEICHEN_VERGANGEN = "X";
	private static final String WOCHEN_ZEICHEN_ZUKUNFT = ".";

	/** Die Anzahl der Wochen im Jahr. */
	private static final int MAX_WOCHEN = 52;

	private int wochen;
	private int jahr;

	/**
	 * Zeile.
	 * 
	 * @param jahr   das Jahr
	 * @param wochen die Wochen
	 */
	public Zeile(int jahr, int wochen) {
		this.wochen = wochen;
		this.jahr = jahr;
	}

	/**
	 * Gibt eine Leere Zeile.
	 * 
	 * @param jahr das Jahr
	 * @return Jahr
	 */
	public static String getLeerZeile(int jahr) {
		return new Zeile(jahr, 0).toString();
	}

	/**
	 * Lieftert eine volle Zeile.
	 * 
	 * @param jahr das Jahr
	 * @return volle Zeile
	 */
	public static String getVolleZeile(int jahr) {
		return new Zeile(jahr, MAX_WOCHEN).toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (jahr < 10) {
			builder.append(" ");
		}
		builder.append(jahr);

		builder.append("      ");

		int i = 0;
		for (; i < wochen; i++) {
			builder.append(WOCHEN_ZEICHEN_VERGANGEN);
		}
		for (; i < MAX_WOCHEN; i++) {
			builder.append(WOCHEN_ZEICHEN_ZUKUNFT);
		}
		return builder.toString();
	}
}