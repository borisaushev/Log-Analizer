package backend.academy.analyser.record.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.filter.StreamFilter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

public class BeforeDateStreamFilter implements StreamFilter {
    @Override
    public Stream<LogRecord> applyFilter(Stream<LogRecord> stream, String value) {
        return stream.filter(r -> r.timeZoned()
            .isBefore(LocalDate.parse(value).atStartOfDay(ZoneId.systemDefault())));
    }
}
