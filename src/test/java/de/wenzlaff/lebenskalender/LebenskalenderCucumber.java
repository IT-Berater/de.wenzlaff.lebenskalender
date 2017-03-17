package de.wenzlaff.lebenskalender;

import cucumber.api.java.en.Given;

/**
 * Testklasse für den Lebenskalender.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class LebenskalenderCucumber {

	@Given("^eine Mann im Alter von (\\d+) Jahren und (\\d+) Monaten$")
	public void generateMann(int jahre, int monate) throws Throwable {
		Lebenskalender.generate(jahre, monate, false);
	}

	@Given("^eine Frau im Alter von (\\d+) Jahren und (\\d+) Monaten$")
	public void generateFrau(int jahre, int monate) throws Throwable {
		Lebenskalender.generate(jahre, monate, true);
	}

}
