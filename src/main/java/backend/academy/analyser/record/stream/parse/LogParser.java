package backend.academy.analyser.record.stream.parse;

import backend.academy.analyser.record.LogRecord;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogParser {
    // Regex to parse log records
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(\\S+)\\s+-\\s+(\\S+)\\s+\\[(.+?)]\\s+\"(.+?)\"\\s+(\\d{3})\\s+(\\d+)\\s+\"(.*?)\"\\s+\"(.*?)\"$"
    );

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.of("RUS"));

    //TODO: document
    public static LogRecord parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.matches()) {
            return null;
        }

        String remoteAddress = matcher.group(1);
        String remoteUser = matcher.group(2).equals("-") ? null : matcher.group(2);
        ZonedDateTime timeZoned = ZonedDateTime.parse(matcher.group(3), DATE_FORMATTER);
        String[] request = matcher.group(4).split(" ");
        String httpMethod = request[0];
        String uri = request[1];
        String httpVersion = request[2];
        int status = Integer.parseInt(matcher.group(5));
        int bodyBytesSent = Integer.parseInt(matcher.group(6));
        String httpReferer = matcher.group(7).equals("-") ? null : matcher.group(7);
        String httpUserAgent = matcher.group(8);

        return new LogRecord(remoteAddress, remoteUser, timeZoned, httpMethod, uri, httpVersion, status, bodyBytesSent, httpReferer, httpUserAgent);
    }

    //TODO: document
    public static Stream<LogRecord> parseLogStream(Stream<String> logLines) {
        return logLines.map(LogParser::parseLogLine).filter(Objects::nonNull);
    }
}

