package backend.academy.analyser.record.stream.parse;

import backend.academy.analyser.record.LogRecord;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A class for parsing Nginx log lines and converting them into {@link LogRecord} objects.
 */
@SuppressWarnings("MultipleStringLiterals")
public class LogParser {
    public static final String REMOTE_ADDRESS_GROUP = "remoteAddress";
    public static final String REMOTE_USER_GROUP = "remoteUser";
    public static final String TIMESTAMP_GROUP = "timestamp";
    public static final String REQUEST_GROUP = "request";
    public static final String STATUS_GROUP = "status";
    public static final String BODY_BYTES_SENT_GROUP = "bodyBytesSent";
    public static final String HTTP_REFERER_GROUP = "httpReferer";
    public static final String HTTP_USER_AGENT_GROUP = "httpUserAgent";
    public static final String NULL_VALUE = "-";
    // Regular expression for parsing log records
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(?<remoteAddress>\\S+)\\s+-\\s+(?<remoteUser>\\S+)\\s+"
            + "\\[(?<timestamp>.+?)]\\s+\"(?<request>.+?)\""
            + "\\s+(?<status>\\d{3})\\s+(?<bodyBytesSent>\\d+)"
            + "\\s+\"(?<httpReferer>.*?)\"\\s+\"(?<httpUserAgent>.*?)\"$"
    );
    // Formatter for parsing timestamps
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.forLanguageTag("RUS"));

    private LogParser() {
    }

    /**
     * Parses a log line and converts it into a {@link LogRecord} object.
     *
     * @param logLine the string representing a Nginx log entry
     * @return a {@link LogRecord} object if the line is successfully matched;
     *     {@code null} if the line does not match the expected format
     */
    public static LogRecord parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.matches()) {
            return null;
        }

        return LogRecord.builder()
            .remoteAddress(matcher.group(REMOTE_ADDRESS_GROUP))
            .remoteUser(
                Objects.equals(matcher.group(REMOTE_USER_GROUP), NULL_VALUE) ? null : matcher.group("remoteUser"))
            .timeZoned(ZonedDateTime.parse(matcher.group(TIMESTAMP_GROUP), DATE_FORMATTER))
            .httpMethod(matcher.group(REQUEST_GROUP).split(" ")[0])
            .uri(matcher.group(REQUEST_GROUP).split(" ").length > 1 ? matcher.group(REQUEST_GROUP).split(" ")[1] : null)
            .httpVersion(
                matcher.group(REQUEST_GROUP).split(" ").length > 2 ? matcher.group(REQUEST_GROUP).split(" ")[2] : null)
            .statusCode(Integer.parseInt(matcher.group(STATUS_GROUP)))
            .bodyBytesSent(Integer.parseInt(matcher.group(BODY_BYTES_SENT_GROUP)))
            .httpReferer(
                Objects.equals(matcher.group(HTTP_REFERER_GROUP), NULL_VALUE) ? null : matcher.group("httpReferer"))
            .httpUserAgent(matcher.group(HTTP_USER_AGENT_GROUP))
            .build();
    }

    /**
     * Parses a stream of log lines and converts them into a stream of {@link LogRecord} objects.
     *
     * @param logLines a stream of strings representing Nginx log entries
     * @return a stream of {@link LogRecord} objects containing only successfully matched records
     */
    public static Stream<LogRecord> parseLogStream(Stream<String> logLines) {
        return logLines.map(LogParser::parseLogLine).filter(Objects::nonNull);
    }
}
