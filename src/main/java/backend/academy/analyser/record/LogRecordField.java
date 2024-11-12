package backend.academy.analyser.record;

import java.util.function.Function;

/**
 * Enumeration representing the fields of a {@link LogRecord}.
 *
 * <p>Each enum constant corresponds to a specific field in the {@link LogRecord}
 * class and provides a method to extract the value of that field from a
 * {@link LogRecord} instance.</p>
 */
public enum LogRecordField {
    bodyBytesSent(r -> String.valueOf(r.bodyBytesSent())),
    httpMethod(LogRecord::httpMethod),
    httpReferer(LogRecord::httpReferer),
    httpUserAgent(LogRecord::httpUserAgent),
    httpVersion(LogRecord::httpVersion),
    remoteAddress(LogRecord::remoteAddress),
    remoteUser(LogRecord::remoteUser),
    statusCode(r -> String.valueOf(r.statusCode())),
    timeZoned(r -> String.valueOf(r.timeZoned())),
    uri(LogRecord::uri);

    /**
     * A function that defines how to extract the value of the log record field
     * from a {@link LogRecord} instance, and converts in to String.
     */
    public final Function<LogRecord, String> strategy;

    /**
     * Constructs a {@link LogRecordField} with the specified field getter function.
     *
     * @param fieldGetter a function that retrieves the field value from a
     *                    {@link LogRecord} instance, and converts in to String.
     */
    LogRecordField(Function<LogRecord, String> fieldGetter) {
        strategy = fieldGetter;
    }
}
