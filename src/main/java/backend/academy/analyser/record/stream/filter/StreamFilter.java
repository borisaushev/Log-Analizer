package backend.academy.analyser.record.stream.filter;

import backend.academy.analyser.record.LogRecord;
import java.util.stream.Stream;

/**
 * Interface defining a filter for processing streams of {@link LogRecord}
 * elements based on a specified criterion.
 *
 * @param <T> the type of the value used as a filter criterion
 */
public interface StreamFilter<T> {
    /**
     * Filters a stream of {@link LogRecord} elements based on the provided criterion.
     *
     * @param stream the stream of {@link LogRecord} objects to filter
     * @param value  the criterion used to filter the stream
     * @return a new stream containing only {@link LogRecord} elements that match the filter criterion
     */
    Stream<LogRecord> filterStream(Stream<LogRecord> stream, T value);
}
