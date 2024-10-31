package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.source.impl.LocalFileLogRecordStreamSource;
import backend.academy.analyser.statistic.StatisticsCollector;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LeastRequestedResourcesStatisticCollectorTest {
    @DisplayName("Least requested resources")
    @Test
    public void leastRequestedTest() throws InterruptedException {
        String path = "src/main/resources/testLogs.txt";
        Stream<LogRecord> stream = new LocalFileLogRecordStreamSource().getLogRecordStream(path);
        StatisticsCollector statisticCollector = new LeastRequestedResourcesStatisticCollector();
        String expectedBottom1 = "/downloads/product_3";
        String expectedBottom2 = "/downloads/product_2";
        String expectedBottom3 = "/downloads/product_1";

        stream.forEach(statisticCollector::include);
        List<List<Object>> entries = statisticCollector.getStatistic().entries();

        assertEquals(expectedBottom1, entries.get(0).get(0));
        assertEquals(expectedBottom2, entries.get(1).get(0));
        assertEquals(expectedBottom3, entries.get(2).get(0));
    }
}
