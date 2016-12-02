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

}
