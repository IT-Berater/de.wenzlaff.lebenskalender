package de.wenzlaff.lebenskalender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import picocli.CommandLine.ITypeConverter;

/**
 * Das deutsche Datumsformat. dd.MM.yyyy
 * 
 * @author Thomas Wenzlaff
 */
public class GermanLocalDateConverter implements ITypeConverter<LocalDate> {

	@Override
	public LocalDate convert(String datumDeFormat) throws Exception {

		return LocalDate.from(DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(datumDeFormat));
	}
}