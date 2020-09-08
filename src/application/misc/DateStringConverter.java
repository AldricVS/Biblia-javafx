package application.misc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

/**
 * Class used by DatePicker in order to get date writed accordingly.<p>
 * A date will be writted like this : 
 * <pre>Day/Month/Year</pre>
 * @author Aldric Vitali Silvestre
 *
 */
public class DateStringConverter extends StringConverter<LocalDate> {

	private final String pattern = "dd/MM/yyyy";
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

	@Override
	public LocalDate fromString(String string) {
		if (string != null && !string.isEmpty()) {
			return LocalDate.parse(string, dateTimeFormatter);
		}
		return null;
	}

	@Override
	public String toString(LocalDate localDate) {
		if (localDate != null) {
			return dateTimeFormatter.format(localDate);
		}
		return null;
	}

}
