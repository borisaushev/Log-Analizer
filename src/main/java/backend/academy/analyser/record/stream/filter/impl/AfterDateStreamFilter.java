package backend.academy.analyser.record.stream.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.filter.StreamFilter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

/**
 * Implementation of {@link StreamFilter} for filtering {@link LogRecord} entries based on a specified date.
 * <p>
 * This filter allows only those log records that occur after the provided date.
 * </p>
 */
public class AfterDateStreamFilter implements StreamFilter<LocalDate> {
    /**
     * Filters a stream of {@link LogRecord} entries, retaining only those records
     * that have a timestamp after the specified date.
     *
     * @param stream the stream of {@link LogRecord} entries to be filtered
     * @param value  the {@link LocalDate} used as the threshold date; records dated after
     *               this value are retained in the result
     * @return a stream of {@link LogRecord} entries occurring after the specified date
     */
    @Override
    public Stream<LogRecord> filterStream(Stream<LogRecord> stream, LocalDate value) {
        return stream.filter(r -> r.timeZoned()
            .isAfter(value.atStartOfDay(ZoneId.systemDefault())));
    }
}