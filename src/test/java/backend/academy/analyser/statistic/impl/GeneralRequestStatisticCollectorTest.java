package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.source.impl.LocalFileLogRecordStreamSource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneralRequestStatisticCollectorTest {
    public static final double RELATIVE_ACCURACY = 0.01;

    @DisplayName("General statistics")
    @Test
    public void generalTest() {
        String path = "src/main/resources/testLogs.txt";
        Stream<LogRecord> stream = new LocalFileLogRecordStreamSource().getLogRecordStream(path);
        LinkedList<Integer> list = new LinkedList<>();
        GeneralRequestStatisticCollector statisticCollector = new GeneralRequestStatisticCollector();
        stream.forEach(r -> {
            list.addLast(r.bodyBytesSent());
            statisticCollector.include(r);
        });
        Collections.sort(list);
        long expectedCount = list.size();
        long sum = 0;
        for (int val : list) {
            sum += val;
        }
        long expectedAverage = (long) ((double) (sum) / list.size());
        long expectedPercentile = list.get((int) (0.95 * list.size()));
        long expectedMax = list.stream().max(Integer::compareTo).get();
        long expectedMin = list.stream().min(Integer::compareTo).get();

        List<List<Object>> entries = statisticCollector.getStatistic().entries();

        assertTrue(equalsWithAccuracy(expectedCount, (Long) entries.get(0).get(1)));
        assertTrue(equalsWithAccuracy(expectedAverage, (Long) entries.get(1).get(1)));
        assertTrue(equalsWithAccuracy(expectedPercentile, (Long) entries.get(2).get(1)));
        assertTrue(equalsWithAccuracy(expectedMax, (Long) entries.get(3).get(1)));
        assertTrue(equalsWithAccuracy(expectedMin, (Long) entries.get(4).get(1)));
    }

    private boolean equalsWithAccuracy(long expected, long value) {
        return Math.abs(expected - value) <= RELATIVE_ACCURACY * expected;
    }
}
