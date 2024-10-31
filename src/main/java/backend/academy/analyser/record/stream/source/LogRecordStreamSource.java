package backend.academy.analyser.record.stream.source;

import backend.academy.analyser.record.LogRecord;
import java.util.stream.Stream;

public interface LogRecordStreamSource {
    Stream<LogRecord> getLogRecordStream(String path);
}
