package backend.academy.analyser.record.stream.parse;

import backend.academy.analyser.record.LogRecord;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogParserTest {
    @Test
    void parseLogLine() {
        String logLine =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
        LogRecord expectedLogRecord = new LogRecord(
            "93.180.71.3", // remoteAddress
            null,          // remoteUser (отсутствует в строке, соответствует "-")
            ZonedDateTime.parse("2015-05-17T08:05:32Z"), // timeZoned
            "GET",        // httpMethod
            "/downloads/product_1", // uri
            "HTTP/1.1",  // httpVersion
            304,         // statusCode
            0,           // bodyBytesSent
            null,        // httpReferer (отсутствует в строке, соответствует "-")
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)" // httpUserAgent
        );

        LogRecord logRecord = LogParser.parseLogLine(logLine);

        assertEquals(expectedLogRecord, logRecord);
    }
}
