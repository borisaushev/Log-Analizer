package backend.academy.analyser.record.stream.source;

import backend.academy.analyser.record.LogRecord;
import java.util.stream.Stream;

/**
 * An interface for sources that provide a stream of {@link LogRecord} objects.
 * Implementing classes should define how to retrieve log records from various sources
 */
public interface LogRecordStreamSource {

    /**
     * Retrieves a stream of {@link LogRecord} objects from the specified source.
     *
     * @param path the path to the source from which to retrieve log records;
     *             the interpretation of this path depends on the implementing class
     * @return a stream of {@link LogRecord} objects;
     *         may return an empty stream if no records are available or an error occurs
     */
    Stream<LogRecord> getLogRecordStream(String path);
}
