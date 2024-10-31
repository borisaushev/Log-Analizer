package backend.academy.analyser.record.filter.impl;

import backend.academy.analyser.record.stream.parse.LogParser;
import backend.academy.analyser.record.LogRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateStreamFilterTest {
    private final String LOGS = """
            93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            93.180.71.3 - - [17/May/2015:08:05:23 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            80.91.33.133 - - [18/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
            217.168.17.5 - - [18/May/2015:08:05:34 +0000] "GET /downloads/product_1 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
            217.168.17.5 - - [18/May/2015:08:05:09 +0000] "GET /downloads/product_2 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
            93.180.71.3 - - [19/May/2015:08:05:57 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            217.168.17.5 - - [19/May/2015:08:05:02 +0000] "GET /downloads/product_2 HTTP/1.1" 404 337 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
            217.168.17.5 - - [20/May/2015:08:05:42 +0000] "GET /downloads/product_1 HTTP/1.1" 404 332 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
            80.91.33.133 - - [20/May/2015:08:05:01 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
            93.180.71.3 - - [21/May/2015:08:05:27 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            217.168.17.5 - - [21/May/2015:08:05:12 +0000] "GET /downloads/product_2 HTTP/1.1" 200 3316 "-" "-"
            """;

    @DisplayName("Date after filter")
    @ParameterizedTest
    @CsvSource({"2015-05-21,2", "2015-05-25,0", "2015-05-01,11"})
    public void applyAfterDateFilterTest(String afterDate, int expectedCount) {
        AfterDateStreamFilter afterDateFilter = new AfterDateStreamFilter();
        Stream<LogRecord> stream = LogParser.parseLogStream(Arrays.stream(LOGS.split("\n")));

        Stream<LogRecord> afterDateStream = afterDateFilter.applyFilter(stream, LocalDate.parse(afterDate));

        assertEquals(expectedCount, afterDateStream.count());
    }

    @DisplayName("Date before filter")
    @ParameterizedTest
    @CsvSource({"2015-05-25,11", "2015-05-21,9", "2015-05-01,0"})
    public void applyBeforeDateFilterTest(String beforeDate, int expectedCount) {
        BeforeDateStreamFilter beforeDateFilter = new BeforeDateStreamFilter();
        Stream<LogRecord> stream = LogParser.parseLogStream(Arrays.stream(LOGS.split("\n")));

        Stream<LogRecord> beforeDateStream = beforeDateFilter.applyFilter(stream, LocalDate.parse(beforeDate));

        assertEquals(expectedCount, beforeDateStream.count());
    }
}
