package backend.academy.analyser.record.stream.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.LogRecordField;
import backend.academy.analyser.record.stream.filter.StreamFilter;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Implementation of {@link StreamFilter} for filtering {@link LogRecord} entries based on given row and value.
 * <p>
 * This filter allows only those log records that have a specified field equal to expected value.
 * </p>
 */
public class ValueFilter implements StreamFilter<Pair<LogRecordField, String>> {
    /**
     * Filters a stream of {@link LogRecord} entries, retaining only those records
     * that have a specified field equal to expected value.
     *
     * @param stream the stream of {@link LogRecord} entries to be filtered
     * @param values the {@link Pair} object, where the key is a {@link LogRecordField}<p>
     *               representing a column to be filtered,<p>
     *               and an {@link Object} value, that remains only entries, that have<p>
     *               a specified row equal to given value
     * @return a stream of {@link LogRecord} filtered stream
     */
    @Override
    public Stream<LogRecord> filterStream(
        Stream<LogRecord> stream,
        Pair<LogRecordField, String> values
    ) {
        Function<LogRecord, String> strategy = values.getKey().strategy;
        return stream.filter(r -> strategy.apply(r).equalsIgnoreCase(values.getValue()));
    }
}
