package de.wenzlaff.lebenskalender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

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
	public void testGenerateDefault() throws Exception {
		Lebenskalender app = new Lebenskalender();
		CommandLine cmd = new CommandLine(app);

		int exitCode = cmd.execute("-g", "02.05.1960");
		assertEquals(0, exitCode);
	}

	@Test
	public void testGenerateFrau() throws Exception {
		Lebenskalender app = new Lebenskalender();
		CommandLine cmd = new CommandLine(app);

		int exitCode = cmd.execute("-g", "02.05.1960");
		assertEquals(0, exitCode);
	}

	@Test
	public void testGenerateMann() throws Exception {
		Lebenskalender app = new Lebenskalender();
		CommandLine cmd = new CommandLine(app);

		int exitCode = cmd.execute("-g", "02.05.1960", "-m");
		assertEquals(0, exitCode);
	}

	@Test
	public void testHilfe() throws Exception {
		Lebenskalender app = new Lebenskalender();
		CommandLine cmd = new CommandLine(app);

		int exitCode = cmd.execute("--Help");
		assertEquals(2, exitCode);
	}
}