package backend.academy.analyser;

import backend.academy.analyser.record.LogRecord;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogParser {
    // Регулярное выражение для разбора строки лога
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(\\S+)\\s+-\\s+(\\S+)\\s+\\[(.+?)]\\s+\"(.+?)\"\\s+(\\d{3})\\s+(\\d+)\\s+\"(.*?)\"\\s+\"(.*?)\"$"
    );

    // Формат даты
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.of("RUS"));

    // Метод, который принимает строку лога и возвращает LogRecord
    public static LogRecord parseLogLine(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Неверный формат строки лога: " + logLine);
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

    // Метод, который принимает Stream<String> и возвращает Stream<LogRecord>
    public static Stream<LogRecord> parseLogStream(Stream<String> logLines) {
        return logLines.map(line -> {
            try {
                return parseLogLine(line);
            } catch (IllegalArgumentException e) {
                // Логирование или обработка ошибки
                System.err.println("Ошибка при разборе строки: " + e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull); // Фильтруем нераспознанные строки
    }
}

