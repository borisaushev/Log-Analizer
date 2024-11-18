package backend.academy.analyser.record.stream.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.filter.FilterFunction;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Class to be used as:
 * {@code
 * * stream.filter(new AfterDateStreamFilterFunction(dateAfter))
 * * }
 */
public class AfterDateStreamFilterFunction implements FilterFunction {
    private final LocalDate dateAfter;

    /**
     * If passed value is null, filtered stream will not change
     * @param dateAfter the {@link LocalDate} used as the threshold date; records dated after
     *                  this value are retained in the result, can be null
     */
    public AfterDateStreamFilterFunction(LocalDate dateAfter) {
        this.dateAfter = dateAfter;
    }

    @Override
    public boolean test(LogRecord logRecord) {
        return dateAfter == null || logRecord.timeZoned().isAfter(dateAfter.atStartOfDay(ZoneId.systemDefault()));
    }
}
