package backend.academy.analyser.record.stream.filter;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.LogRecordField;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <p>
 * This filter allows only those log records that have a specified field equal to expected value.
 * </p>
 */
public class ValueFilterPredicate {
    private ValueFilterPredicate() {
    }

    /**
     * Filters a stream of {@link LogRecord} entries, retaining only those records
     * that have a specified field equal to expected value.
     *
     * @param values the {@link Pair} object, where the key is a {@link LogRecordField}<p>
     *               representing a column to be filtered,<p>
     *               and an {@link Object} value, that remains only entries, that have<p>
     *               a specified row equal to given value
     * @return a Predicate function to be used as:
     *     *     {@code
     *     *     * stream.filter(BeforeDateStreamFilterPredicate.getPredicate(your values))
     *     *     * }
     */
    public static Predicate<LogRecord> getPredicate(Pair<LogRecordField, String> values) {
        if (values.getKey() == null || values.getValue() == null) {
            return logRecord -> true;
        }

        Function<LogRecord, String> strategy = values.getKey().strategy;
        return ((LogRecord r) -> strategy.apply(r).equalsIgnoreCase(values.getValue()));
    }
}
