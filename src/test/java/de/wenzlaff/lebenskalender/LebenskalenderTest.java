package de.wenzlaff.lebenskalender;

import org.junit.Test;

/**
 * 
 * @author Thomas Wenzlaff
 *
 */
public class LebenskalenderTest {

	@Test
	public void testGenerateMann() throws Exception {
		Lebenskalender.generate(52, 7, true);
	}

	@Test
	public void testGenerateFrau() throws Exception {
		Lebenskalender.generate(54, 10, false);
	}

	@Test
	public void testGenerateMain() throws Exception {
		String[] param = { "54", "10", "false" };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerZuwenig() throws Exception {
		String[] param = { "54", "10" };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerZuviel() throws Exception {
		String[] param = { "54", "10", "zuviel", "Parameter" };
		Lebenskalender.main(param);
	}

	@Test
	public void testGenerateMainFehlerNull() throws Exception {
		String[] param = null;
		Lebenskalender.main(param);
	}

}
