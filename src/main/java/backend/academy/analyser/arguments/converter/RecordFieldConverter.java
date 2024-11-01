package backend.academy.analyser.arguments.converter;

import backend.academy.analyser.record.LogRecordField;
import com.beust.jcommander.IStringConverter;
import java.util.Arrays;

/**
 * Converts a string representation of a log record field to its corresponding
 * {@link LogRecordField} enumeration value.
 *
 * <p>If the provided string does not match any of the defined log record fields,
 * a {@link RuntimeException} is thrown with a message indicating the available
 * options.</p>
 */
public class RecordFieldConverter implements IStringConverter<LogRecordField> {

    /**
     * Converts the given string to a corresponding {@link LogRecordField} enum value.
     *
     * @param s the string representation of the log record field
     * @return the corresponding {@link LogRecordField} enum value
     * @throws RuntimeException if the given string does not match any log record field
     */
    @Override
    public LogRecordField convert(String s) {
        for (LogRecordField field : LogRecordField.values()) {
            if (s.equalsIgnoreCase(field.name())) {
                return field;
            }
        }
        String message = "Unable to match given field to filter with available one's\n"
            + "please use one of:\n"
            + Arrays.toString(LogRecordField.values());
        throw new RuntimeException(message);
    }
}
