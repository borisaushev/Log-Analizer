package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.statistic.StatisticsCollector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

/**
 * A class for collecting statistics on the least requested resources from HTTP log records.
 * This collector tracks the frequency of requests for each resource URI and provides a
 * report of the three least requested resources.
 */
public class LeastRequestedResourcesStatisticCollector implements StatisticsCollector {

    /**
     * A map that holds the frequency of requests for each resource URI.
     */
    private final Map<String, Integer> frequencyMap = new HashMap<>();

    /**
     * Includes a log record in the statistics collection by updating the frequency count
     * for the resource URI associated with the log record.
     *
     * @param logRecord the log record to include; the URI will be extracted and its frequency updated
     */
    @Override
    public void include(LogRecord logRecord) {
        String uri = logRecord.uri();
        int frequency = frequencyMap.getOrDefault(uri, 0) + 1;
        frequencyMap.put(uri, frequency);
    }

    /**
     * Generates a report table summarizing the least requested resources.
     *
     * @return a {@link ReportTable} containing the three least requested resources and their request counts
     */
    @Override
    public ReportTable getStatistic() {
        List<Pair<String, Integer>> bottomList = getBottom3StringAndCount();

        ReportTable table = new ReportTable(
            "Least requested resources",
            List.of("Resource", "Requests")
        );
        for (Pair<String, Integer> entry : bottomList) {
            table.addEntry(entry.getKey(), entry.getValue());
        }

        return table;
    }

    /**
     * Retrieves the three least requested resources and their request counts.
     *
     * @return a list of 3 pairs containing the resource URIs and their respective request counts
     */
    private List<Pair<String, Integer>> getBottom3StringAndCount() {
        String bottom1 = "";
        String bottom2 = "";
        String bottom3 = "";
        int bottom1Count = Integer.MAX_VALUE;
        int bottom2Count = Integer.MAX_VALUE;
        int bottom3Count = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            String newString = entry.getKey();
            int newCount = entry.getValue();

            if (newCount < bottom1Count) {
                String bottom1Temp = bottom1;
                int bottom1CountTemp = bottom1Count;
                String bottom2Temp = bottom2;
                int bottom2CountTemp = bottom2Count;

                bottom1 = newString;
                bottom1Count = newCount;
                bottom2 = bottom1Temp;
                bottom2Count = bottom1CountTemp;
                bottom3 = bottom2Temp;
                bottom3Count = bottom2CountTemp;
            } else if (newCount < bottom2Count) {
                String bottom2Temp = bottom2;
                int bottom2CountTemp = bottom2Count;

                bottom2 = newString;
                bottom2Count = newCount;
                bottom3 = bottom2Temp;
                bottom3Count = bottom2CountTemp;
            } else if (newCount < bottom3Count) {
                bottom3 = newString;
                bottom3Count = newCount;
            }
        }

        return List.of(
            Pair.of(bottom1, bottom1Count == Integer.MAX_VALUE ? 0 : bottom1Count),
            Pair.of(bottom2, bottom2Count == Integer.MAX_VALUE ? 0 : bottom2Count),
            Pair.of(bottom3, bottom3Count == Integer.MAX_VALUE ? 0 : bottom3Count)
        );
    }
}
