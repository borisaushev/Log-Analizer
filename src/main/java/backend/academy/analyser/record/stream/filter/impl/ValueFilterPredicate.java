package backend.academy.analyser.record.stream.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.LogRecordField;
import backend.academy.analyser.record.stream.filter.FilterFunction;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Class to be used as:
 * {@code
 * * stream.filter(new ValueFilterPredicate(value))
 * * }
 * Value is a {@link Pair}
 * object, where key is a LogRecordField value to filter
 * and key is value of that field
 */
public class ValueFilterPredicate implements FilterFunction {
    private final Pair<LogRecordField, String> value;

    /**
     * If passed object/key/value is null, filtered stream will not change
     * @param value the {@link Pair} object, where the key is a {@link LogRecordField}<p>
     *              representing a column to be filtered,<p>
     *              and an {@link Object} value, that remains only entries, that have<p>
     *              a specified row equal to given value
     */
    public ValueFilterPredicate(Pair<LogRecordField, String> value) {
        this.value = value;
    }

    @Override
    public boolean test(LogRecord logRecord) {
        if (value == null || value.getKey() == null || value.getValue() == null) {
            return true;
        }

        Function<LogRecord, String> strategy = value.getKey().strategy;
        return strategy.apply(logRecord).equalsIgnoreCase(value.getValue());
    }
}
