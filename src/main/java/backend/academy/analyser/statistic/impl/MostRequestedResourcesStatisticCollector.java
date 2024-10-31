package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.statistic.StatisticsCollector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class MostRequestedResourcesStatisticCollector implements StatisticsCollector {
    private final Map<String, Integer> frequencyMap = new HashMap<>();

    @Override
    public void clear() {
        frequencyMap.clear();
    }

    @Override
    public void include(LogRecord logRecord) {
        String uri = logRecord.uri();
        int frequency = frequencyMap.getOrDefault(uri, 0) + 1;
        frequencyMap.put(uri, frequency);
    }

    @Override
    public ReportTable getStatistic() {
        List<Pair<String, Integer>> topList = getTop3StringAndCount();

        ReportTable table = new ReportTable(
            "Most requested resources",
            List.of("Resource", "Requests")
        );
        for (Pair<String, Integer> entry : topList) {
            table.addEntry(entry.getKey(), entry.getValue());
        }

        return table;
    }

    public List<Pair<String, Integer>> getTop3StringAndCount() {
        String top1 = "";
        String top2 = "";
        String top3 = "";
        int top1Count = 0;
        int top2Count = 0;
        int top3Count = 0;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            String newString = entry.getKey();
            int newCount = entry.getValue();

            if (newCount > top1Count) {
                String top1Temp = top1;
                int top1CountTemp = top1Count;
                String top2Temp = top2;
                int top2CountTemp = top2Count;

                top1 = newString;
                top1Count = newCount;
                top2 = top1Temp;
                top2Count = top1CountTemp;
                top3 = top2Temp;
                top3Count = top2CountTemp;
            } else if (newCount > top2Count) {
                String top2Temp = top2;
                int top2CountTemp = top2Count;

                top2 = newString;
                top2Count = newCount;
                top3 = top2Temp;
                top3Count = top2CountTemp;
            } else if (newCount > top3Count) {
                top3 = newString;
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
