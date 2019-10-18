package de.wenzlaff.lebenskalender;

import org.junit.jupiter.api.Test;

/**
 * Testklasse für den Lebenskalender.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class LebenskalenderTest {

	private static final String TEST_ALTER_MONAT = "8";
	private static final String TEST_ALTER_JAHRE = "55";

	@Test
	public void testGenerateMann() throws Exception {
		Lebenskalender.generate(55, 5, true);
	}

	@Test
	public void testGenerateFrau() throws Exception {
		Lebenskalender.generate(57, 8, false);
	}

	@Test
	public void testGenerateMain() throws Exception {
		String[] param = { TEST_ALTER_JAHRE, TEST_ALTER_MONAT, "false" };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerZuwenig() throws Exception {
		String[] param = { TEST_ALTER_JAHRE, TEST_ALTER_MONAT };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerZuviel() throws Exception {
		String[] param = { TEST_ALTER_JAHRE, TEST_ALTER_MONAT, "zuviel", "Parameter" };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerNull() throws Exception {
		String[] param = null;
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainKonst() throws Exception {
		new Lebenskalender();
	}

}
