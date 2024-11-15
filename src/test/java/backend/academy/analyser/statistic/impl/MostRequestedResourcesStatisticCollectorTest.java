package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.source.impl.LocalFileLogRecordStreamSource;
import backend.academy.analyser.statistic.StatisticsCollector;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MostRequestedResourcesStatisticCollectorTest {
    @DisplayName("Top requested resources")
    @Test
    public void topRequestedTest() {
        //Given
        String path = "src/main/resources/testLogs.txt";
        Stream<LogRecord> stream = new LocalFileLogRecordStreamSource().getLogRecordStream(path);
        StatisticsCollector statisticCollector = new MostRequestedResourcesStatisticCollector();
        String expectedTop1 = "/downloads/product_1";
        String expectedTop2 = "/downloads/product_2";
        String expectedTop3 = "/downloads/product_3";

        //When
        stream.forEach(statisticCollector::include);
        List<List<Object>> entries = statisticCollector.getStatistic().entries();

        //Then
        assertEquals(expectedTop1, entries.get(0).get(0));
        assertEquals(expectedTop2, entries.get(1).get(0));
        assertEquals(expectedTop3, entries.get(2).get(0));
    }
}
