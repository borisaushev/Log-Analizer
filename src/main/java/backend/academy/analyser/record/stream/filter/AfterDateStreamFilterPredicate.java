package backend.academy.analyser.record.stream.filter;

import backend.academy.analyser.record.LogRecord;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Predicate;

/**
 * <p>
 * This filter allows only those log records that occur after the provided date.
 * </p>
 */
public class AfterDateStreamFilterPredicate {
    private AfterDateStreamFilterPredicate() {
    }

    /**
     * Filters a stream of {@link LogRecord} entries, retaining only those records
     * that have a timestamp after the specified date.
     *
     * @param value the {@link LocalDate} used as the threshold date; records dated after
     *              this value are retained in the result
     * @return a Predicate function to be used as:
     *     {@code
     *     * stream.filter(AfterDateStreamFilterPredicate.getPredicate(dateAfter))
     *     * }
     */
    public static Predicate<LogRecord> getPredicate(LocalDate value) {
        return value == null ? logRecord -> true
            : logRecord -> logRecord.timeZoned().isAfter(value.atStartOfDay(ZoneId.systemDefault()));
    }
}
