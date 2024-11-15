package backend.academy.analyser.record.stream.source;

import backend.academy.analyser.record.stream.source.impl.HttpLogRecordStreamSource;
import backend.academy.analyser.record.stream.source.impl.LocalFileLogRecordStreamSource;

/**
 * An enumeration representing different sources of log records.
 * Each source is associated with a regex pattern for validation
 * and an implementation of the {@link LogRecordStreamSource} interface
 * for retrieving log records from the source.
 */
public enum LogRecordStreamSources {

    /**
     * Represents a local file source for log records.
     * Validates file paths using a specific regex pattern.
     */
    LOCAL_FILE(
        "((([A-z]:[\\\\/])|([\\\\/]))?([A-z0-9]+([\\\\/]))*)?([A-z0-9]+((\\.[A-z]+)|\\*))",
        new LocalFileLogRecordStreamSource()
    ),

    /**
     * Represents an HTTP source for log records.
     * Validates URLs using a specific regex pattern.
     */
    HTTP(
        "https?://[A-z0-9%.]+(/[%A-z0-9]+)+([%A-z0-9.]+)",
        new HttpLogRecordStreamSource()
    );

    /**
     * The regex pattern used for validating the source path.
     */
    public final String pattern;

    /**
     * The strategy for retrieving log records from the source.
     */
    public final LogRecordStreamSource strategy;

    /**
     * Constructor to create an instance of the enum with a specific pattern and strategy.
     *
     * @param pattern  the regex pattern used for validating the source path
     * @param strategy the strategy for retrieving log records from the source
     */
    LogRecordStreamSources(String pattern, LogRecordStreamSource strategy) {
        this.pattern = pattern;
        this.strategy = strategy;
    }
}
