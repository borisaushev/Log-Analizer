package backend.academy;

import backend.academy.analyser.LogParser;
import backend.academy.analyser.record.LogRecord;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream<String> logLines = Stream.of(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\""
        );

        Stream<LogRecord> logRecords = LogParser.parseLogStream(logLines);
        logRecords.forEach(System.out::println);
    }
}
