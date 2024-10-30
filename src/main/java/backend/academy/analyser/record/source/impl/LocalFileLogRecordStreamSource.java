package backend.academy.analyser.record.source.impl;

import backend.academy.analyser.LogParser;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.source.LogRecordStreamSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class LocalFileLogRecordStreamSource implements LogRecordStreamSource {
    @Override
    public Stream<LogRecord> getLogRecordStream(String path) {
        try {
            Path filePath = Path.of(path);
            return LogParser.parseLogStream(Files.lines(filePath));
        } catch (InvalidPathException | IOException exc) {
            return Stream.of();
        }
    }
}
