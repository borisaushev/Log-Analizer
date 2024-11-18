package backend.academy.analyser.record.stream.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.filter.FilterFunction;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Class to be used as:
 * {@code
 * * stream.filter(new BeforeDateStreamFilterFunction(dateBefore))
 * * }
 */
public class BeforeDateStreamFilterFunction implements FilterFunction {
    private final LocalDate dateBefore;

    /**
     * If passed value is null, filtered stream will not change
     * @param dateBefore the {@link LocalDate} used as the threshold date; records dated before
     *                   this value are retained in the result, can be null
     */
    public BeforeDateStreamFilterFunction(LocalDate dateBefore) {
        this.dateBefore = dateBefore;
    }

    @Override
    public boolean test(LogRecord logRecord) {
        return dateBefore == null || logRecord.timeZoned().isBefore(dateBefore.atStartOfDay(ZoneId.systemDefault()));
    }
}
