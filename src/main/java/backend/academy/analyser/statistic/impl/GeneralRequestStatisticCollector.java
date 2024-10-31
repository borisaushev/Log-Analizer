package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.statistic.StatisticsCollector;
import backend.academy.analyser.format.ReportTable;
import com.datadoghq.sketch.ddsketch.DDSketch;
import com.datadoghq.sketch.ddsketch.DDSketches;
import java.util.List;

public class GeneralRequestStatisticCollector implements StatisticsCollector {
    public static final double RELATIVE_ACCURACY = 0.01;
    DDSketch sketch = DDSketches.unboundedDense(RELATIVE_ACCURACY);

    @Override
    public void clear() {
        sketch.clear();
    }

    @Override
    public void include(LogRecord logRecord) {
        sketch.accept(logRecord.bodyBytesSent());
    }

    @Override
    public ReportTable getStatistic() {
        long count = (long) sketch.getCount();
        long average = (long) sketch.getAverage();
        long percentile = (long) sketch.getValueAtQuantile(0.95);
        long max = (long) sketch.getMaxValue();
        long min = (long) sketch.getMinValue();

        ReportTable reportTable = new ReportTable("General info",
            List.of("Metric", "Value"));
        reportTable.addEntry("request count", count);
        reportTable.addEntry("avg response size", average);
        reportTable.addEntry("95% percentile", percentile);
        reportTable.addEntry("max response size", max);
        reportTable.addEntry("min response size", min);

        return reportTable;
    }
}
