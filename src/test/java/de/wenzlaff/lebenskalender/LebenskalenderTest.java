package de.wenzlaff.lebenskalender;

import org.junit.jupiter.api.Test;

/**
 * Testklasse für den Lebenskalender.
 * 
 * @author Thomas Wenzlaff
 */
public class LebenskalenderTest {

	private static final String TEST_GEBURTS_DATUM = "01.01.1990";
	private static final Boolean MANN = true;
	private static final Boolean FRAU = false;

	@Test
	public void testGenerateMann() throws Exception {
		Lebenskalender.generate(TEST_GEBURTS_DATUM, MANN);
	}

	@Test
	public void testGenerateFrau() throws Exception {
		Lebenskalender.generate(TEST_GEBURTS_DATUM, FRAU);
	}

	@Test
	public void testGenerateMain() throws Exception {
		String[] param = { TEST_GEBURTS_DATUM, "false" };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerZuwenig() throws Exception {
		String[] param = { TEST_GEBURTS_DATUM };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerZuviel() throws Exception {
		String[] param = { TEST_GEBURTS_DATUM, "zuviel", "Parameter" };
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