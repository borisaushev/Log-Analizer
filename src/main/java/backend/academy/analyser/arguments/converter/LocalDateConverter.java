package backend.academy.analyser.arguments.converter;

import com.beust.jcommander.IStringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A converter class for parsing a date string into a {@link LocalDate} object.
 * <p>
 * This class implements the {@link IStringConverter} interface to facilitate
 * the automatic conversion of command-line arguments into a LocalDate.
 * Expected date format: {@code yyyy-MM-dd}.
 * </p>
 * <p>
 * Usage example with JCommander:
 * </p>
 * <pre>
 * {@code
 * @Parameter(names = "--date", converter = LocalDateConverter.class)
 * private LocalDate date;
 * }
 * </pre>
 *
 * @see IStringConverter
 */
public class LocalDateConverter implements IStringConverter<LocalDate> {
    /**
     * The date format used for parsing date strings.
     * Expected format: {@code yyyy-MM-dd}.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Converts a given date string to a {@link LocalDate} object.
     *
     * @param value the date string to be converted; expected format is {@code yyyy-MM-dd}
     * @return the parsed {@link LocalDate} object
     * @throws IllegalArgumentException if the date string is invalid or does not match the expected format
     */
    @Override
    public LocalDate convert(String value) {
        try {
            return LocalDate.parse(value, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.", e);
        }
    }
}
