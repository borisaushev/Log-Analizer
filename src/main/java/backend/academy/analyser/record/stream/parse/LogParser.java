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
public class LogParser {
    // Regular expression for parsing log records
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(?<remoteAddress>\\S+)\\s+-\\s+(?<remoteUser>\\S+)\\s+\\[(?<timestamp>.+?)]\\s+\"(?<request>.+?)\"\\s+(?<status>\\d{3})\\s+(?<bodyBytesSent>\\d+)\\s+\"(?<httpReferer>.*?)\"\\s+\"(?<httpUserAgent>.*?)\"$"
    );

    // Formatter for parsing timestamps
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.forLanguageTag("RUS"));

    /**
     * Parses a log line and converts it into a {@link LogRecord} object.
     *
     * @param logLine the string representing a Nginx log entry
     * @return a {@link LogRecord} object if the line is successfully matched;
     *         {@code null} if the line does not match the expected format
     */
    public static LogRecord parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.matches()) {
            return null;
        }

        String remoteAddress = matcher.group("remoteAddress");
        String remoteUser = matcher.group("remoteUser").equals("-") ? null : matcher.group("remoteUser");
        ZonedDateTime timeZoned = ZonedDateTime.parse(matcher.group("timestamp"), DATE_FORMATTER);
        String[] request = matcher.group("request").split(" ");
        String httpMethod = request.length > 0 ? request[0] : null;
        String uri = request.length > 1 ? request[1] : null;
        String httpVersion = request.length > 2 ? request[2] : null;

        int status = Integer.parseInt(matcher.group("status"));
        int bodyBytesSent = Integer.parseInt(matcher.group("bodyBytesSent"));
        String httpReferer = matcher.group("httpReferer").equals("-") ? null : matcher.group("httpReferer");
        String httpUserAgent = matcher.group("httpUserAgent");

        return new LogRecord(remoteAddress, remoteUser, timeZoned, httpMethod, uri, httpVersion, status, bodyBytesSent,
            httpReferer, httpUserAgent);
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
