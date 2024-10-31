package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.statistic.StatisticsCollector;
import backend.academy.analyser.format.ReportTable;
import org.apache.commons.lang3.tuple.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeastRequestedResourcesStatisticCollector implements StatisticsCollector {
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
        List<Pair<String, Integer>> bottomList =getBottom3StringAndCount();

        ReportTable table = new ReportTable(
            "Least requested resources",
            List.of("Resource", "Requests")
        );
        for(Pair<String, Integer> entry : bottomList) {
            table.addEntry(entry.getKey(), entry.getValue());
        }

        return table;
    }

    public List<Pair<String, Integer>> getBottom3StringAndCount() {
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
            Pair.of(bottom1, bottom1Count),
            Pair.of(bottom2, bottom2Count),
            Pair.of(bottom3, bottom3Count)
        );
    }
}
