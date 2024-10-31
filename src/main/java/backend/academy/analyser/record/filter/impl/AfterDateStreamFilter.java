package backend.academy.analyser.record.filter.impl;

import backend.academy.analyser.record.filter.StreamFilter;
import backend.academy.analyser.record.LogRecord;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

public class AfterDateStreamFilter implements StreamFilter<LocalDate> {
    @Override
    public Stream<LogRecord> applyFilter(Stream<LogRecord> stream, LocalDate value) {
        return stream.filter(r -> r.timeZoned()
            .isAfter(value.atStartOfDay(ZoneId.systemDefault())));
    }
}
