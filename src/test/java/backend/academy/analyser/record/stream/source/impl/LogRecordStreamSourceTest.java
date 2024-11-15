package backend.academy.analyser.record.stream.source.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.source.LogRecordStreamSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogRecordStreamSourceTest {
    DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.of("RUS"));

    LogRecordStreamSource httpStreamSource = new HttpLogRecordStreamSource();
    LogRecordStreamSource localFileStreamSource = new LocalFileLogRecordStreamSource();

    // First log entry:
    // 93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
    LogRecord expectedFirstLogRecord = new LogRecord(
        "93.180.71.3",
        null,
        ZonedDateTime.parse("17/May/2015:08:05:32 +0000", DATE_FORMATTER),
        "GET",
        "/downloads/product_1",
        "HTTP/1.1",
        304,
        0,
        null,
        "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
    );
    int expectedLogSize = 51462;

    @DisplayName("Get LogRecord Stream for a remote file")
    @Test
    void getRemoteLogRecordStreamTest() {
        // Given
        String sourceUrl =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        // When
        Stream<LogRecord> stream = httpStreamSource.getLogRecordStream(sourceUrl);
        List<LogRecord> recordList = stream.toList();
        LogRecord firstRecord = recordList.getFirst();
        int totalCount = recordList.size();

        // Then
        assertEquals(expectedFirstLogRecord, firstRecord);
        assertEquals(expectedLogSize, totalCount);
    }

    @DisplayName("Get LogRecord Stream for non-existing remote file")
    @Test
    void getRemoteEmptyLogRecordStreamTest() {
        // Given
        String sourceUrl = "https://doesntExists/nginx_logs";

        // When
        Executable executable = () -> httpStreamSource.getLogRecordStream(sourceUrl);

        // Then
        assertThrows(RuntimeException.class, executable);
    }

    @DisplayName("Get LogRecord Stream for a local file")
    @Test
    void getLocalLogRecordStreamTest() {
        // Given
        String sourcePath = "src/main/resources/testLogs.txt";

        // When
        assertTrue(Files.exists(Path.of(sourcePath)));

        Stream<LogRecord> stream = localFileStreamSource.getLogRecordStream(sourcePath);
        List<LogRecord> recordList = stream.toList();
        LogRecord firstRecord = recordList.getFirst();
        int totalCount = recordList.size();

        // Then
        assertEquals(expectedFirstLogRecord, firstRecord);
        assertEquals(expectedLogSize, totalCount);
    }

    @DisplayName("Get LogRecord Stream for non-existing local file")
    @Test
    void getLocalEmptyLogRecordStreamTest() {
        // Given
        String sourcePath = "doesn'tExists.txt";

        // When
        Executable executable = () -> httpStreamSource.getLogRecordStream(sourcePath);

        // Then
        assertThrows(RuntimeException.class, executable);
    }
}
