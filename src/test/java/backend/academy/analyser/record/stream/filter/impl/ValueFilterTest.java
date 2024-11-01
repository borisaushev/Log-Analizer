package backend.academy.analyser.record.stream.filter.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.LogRecordField;
import backend.academy.analyser.record.stream.parse.LogParser;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class ValueFilterTest {
    private final String LOGS = """
        93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        93.180.71.3 - - [17/May/2015:08:05:23 +0000] "POST /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        80.91.33.133 - - [18/May/2015:08:05:24 +0000] "POST /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        217.168.17.5 - - [18/May/2015:08:05:34 +0000] "POST /downloads/product_1 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [18/May/2015:08:05:09 +0000] "POST /downloads/product_2 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        93.180.71.3 - - [19/May/2015:08:05:57 +0000] "POST /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        217.168.17.5 - - [19/May/2015:08:05:02 +0000] "GET /downloads/product_2 HTTP/1.1" 404 337 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [20/May/2015:08:05:42 +0000] "GET /downloads/product_1 HTTP/1.1" 404 332 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        80.91.33.133 - - [20/May/2015:08:05:01 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        93.180.71.3 - - [21/May/2015:08:05:27 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        217.168.17.5 - - [21/May/2015:08:05:12 +0000] "GET /downloads/product_2 HTTP/1.1" 200 3316 "-" "-"
        """;

    ValueFilter valueFilter = new ValueFilter();

    @DisplayName("response code value filter")
    @ParameterizedTest
    @CsvSource({"500, 0", "304,6"})
    public void applyCodeFilterTest(String response, int expectedCount) {
        Stream<LogRecord> stream = LogParser.parseLogStream(Arrays.stream(LOGS.split("\n")));

        Stream<LogRecord> filteredStream =
            valueFilter.filterStream(stream, Pair.of(LogRecordField.statusCode, response));

        assertEquals(expectedCount, filteredStream.count());
    }

    @DisplayName("Http method value filter")
    @ParameterizedTest
    @CsvSource({"GET,6", "POST,5"})
    public void applyMethodFilterTest(String method, int expectedCount) {
        Stream<LogRecord> stream = LogParser.parseLogStream(Arrays.stream(LOGS.split("\n")));

        Stream<LogRecord> filteredStream =
            valueFilter.filterStream(stream, Pair.of(LogRecordField.httpMethod, method));

        assertEquals(expectedCount, filteredStream.count());
    }
}
