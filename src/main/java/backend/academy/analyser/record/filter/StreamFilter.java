package backend.academy.analyser.record.filter;

import backend.academy.analyser.record.LogRecord;
import java.util.stream.Stream;

public interface StreamFilter {
    Stream<LogRecord> applyFilter(Stream<LogRecord> stream, String value);
}
