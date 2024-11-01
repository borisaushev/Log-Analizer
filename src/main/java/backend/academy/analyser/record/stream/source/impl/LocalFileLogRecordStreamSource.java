package backend.academy.analyser.record.stream.source.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.parse.LogParser;
import backend.academy.analyser.record.stream.source.LogRecordStreamSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * A class that retrieves log records from a local file and provides them as a stream of {@link LogRecord} objects.
 * Implements the {@link LogRecordStreamSource} interface.
 */
public class LocalFileLogRecordStreamSource implements LogRecordStreamSource {

    /**
     * Retrieves a stream of {@link LogRecord} objects from the specified local file.
     *
     * @param path the path to the local file containing log records
     * @return a stream of {@link LogRecord} objects; returns an empty stream if an error occurs
     */
    @Override
    public Stream<LogRecord> getLogRecordStream(String path) {
        Path filePath = Path.of(path);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found");
        }

        try {
            return LogParser.parseLogStream(Files.lines(filePath));
        } catch (IOException exc) {
            throw new RuntimeException("Error occurred reading file", exc);
        }
    }
}
