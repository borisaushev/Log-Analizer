package backend.academy.analyser.record.source;

import backend.academy.analyser.record.LogRecord;
import java.io.IOException;
import java.util.stream.Stream;

public interface LogRecordStreamSource {
    Stream<LogRecord> getLogRecordStream(String path);
}
