package backend.academy.analyser.record.stream.filter;

import backend.academy.analyser.record.LogRecord;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Predicate;

/**
 * <p>
 * This filter allows only those log records that occur before the provided date.
 * </p>
 */
public class BeforeDateStreamFilterPredicate {
    private BeforeDateStreamFilterPredicate() {
    }

    /**
     * Filters a stream of {@link LogRecord} entries, retaining only those records
     * that have a timestamp before the specified date.
     *
     * @param value the {@link LocalDate} used as the threshold date; records dated before
     *              this value are retained in the result
     * @return a Predicate function to be used as:
     *     {@code
     *     * stream.filter(BeforeDateStreamFilterPredicate.getPredicate(dateBefore))
     *     * }
     */
    public static Predicate<LogRecord> getPredicate(LocalDate value) {
        return value == null ? logRecord -> true
            : logRecord -> logRecord.timeZoned().isBefore(value.atStartOfDay(ZoneId.systemDefault()));
    }
}
