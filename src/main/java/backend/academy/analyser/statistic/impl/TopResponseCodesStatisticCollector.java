package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.statistic.StatisticsCollector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

/**
 * A class for collecting statistics on the top response codes from HTTP log records.
 * This collector tracks the frequency of HTTP status codes and provides a report
 * of the three most common response codes.
 */
public class TopResponseCodesStatisticCollector implements StatisticsCollector {

    /**
     * A map that holds the frequency of each HTTP status code.
     */
    private final Map<Integer, Integer> frequencyMap = new HashMap<>();

    /**
     * Includes a log record in the statistics collection by updating the frequency count
     * for the HTTP status code associated with the log record.
     *
     * @param logRecord the log record to include; the status code will be extracted and its frequency updated
     */
    @Override
    public void include(LogRecord logRecord) {
        int statusCode = logRecord.statusCode();
        int frequency = frequencyMap.getOrDefault(statusCode, 0) + 1;
        frequencyMap.put(statusCode, frequency);
    }

    /**
     * Generates a report table summarizing the top response codes.
     *
     * @return a {@link ReportTable} containing the three most common response codes and their counts
     */
    @Override
    public ReportTable getStatistic() {
        List<Pair<Integer, Integer>> topList = getTop3CodesAndCount();

        ReportTable table = new ReportTable(
            "Top response codes",
            List.of("Code", "Count")
        );
        for (Pair<Integer, Integer> entry : topList) {
            table.addEntry(entry.getKey(), entry.getValue());
        }

        return table;
    }

    /**
     * Retrieves the three most common response codes and their frequencies.
     *
     * @return a list of 3 pairs containing the response codes and their respective counts
     */
    public List<Pair<Integer, Integer>> getTop3CodesAndCount() {
        int top1 = -1;
        int top2 = -1;
        int top3 = -1;
        int top1Count = 0;
        int top2Count = 0;
        int top3Count = 0;

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            Integer newCode = entry.getKey();
            int newCount = entry.getValue();

            if (newCount > top1Count) {
                int top1Temp = top1;
                int top1CountTemp = top1Count;
                int top2Temp = top2;
                int top2CountTemp = top2Count;

                top1 = newCode;
                top1Count = newCount;
                top2 = top1Temp;
                top2Count = top1CountTemp;
                top3 = top2Temp;
                top3Count = top2CountTemp;
            } else if (newCount > top2Count) {
                int top2Temp = top2;
                int top2CountTemp = top2Count;

                top2 = newCode;
                top2Count = newCount;
                top3 = top2Temp;
                top3Count = top2CountTemp;
            } else if (newCount > top3Count) {
                top3 = newCode;
                top3Count = newCount;
            }
        }

        return List.of(
            Pair.of(top1, top1Count),
            Pair.of(top2, top2Count),
            Pair.of(top3, top3Count)
        );
    }
}
