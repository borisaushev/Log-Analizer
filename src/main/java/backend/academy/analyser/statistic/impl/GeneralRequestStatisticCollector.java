package backend.academy.analyser.statistic.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.statistic.StatisticsCollector;
import com.datadoghq.sketch.ddsketch.DDSketch;
import com.datadoghq.sketch.ddsketch.DDSketches;
import java.util.List;

/**
 * A class for collecting general statistics from HTTP log records.
 * This collector uses a {@link DDSketch} to efficiently summarize
 * the sizes of response bodies in the logs.
 *
 * @see <a href="https://github.com/DataDog/sketches-java/tree/master">git repo</a>
 */
public class GeneralRequestStatisticCollector implements StatisticsCollector {

    /**
     * The relative accuracy of the sketch used for statistics collection.
     */
    public static final double RELATIVE_ACCURACY = 0.01;

    /**
     * The sketch used to accumulate statistics on response body sizes.
     */
    private final DDSketch sketch = DDSketches.unboundedDense(RELATIVE_ACCURACY);

    /**
     * Includes a log record in the statistics collection.
     *
     * @param logRecord the log record to include; the response body size will be extracted and recorded
     */
    @Override
    public void include(LogRecord logRecord) {
        sketch.accept(logRecord.bodyBytesSent());
    }

    /**
     * Generates a report table summarizing the collected statistics.
     *
     * @return a {@link ReportTable} containing metrics such as:<p>
     *     request count,<p>
     *     average response size,<p>
     *     95th percentile response size,<p>
     *     maximum response size,<p>
     *     and minimum response size
     */
    @Override
    public ReportTable getStatistic() {
        long count = sketch.isEmpty() ? 0 : (long) sketch.getCount();
        if(sketch.isEmpty()) {
            sketch.accept(0); // Accepting a zero value to ensure non-empty statistics
        }
        long average = (long) sketch.getAverage();
        long percentile = (long) sketch.getValueAtQuantile(0.95);
        long max = (long) sketch.getMaxValue();
        long min = (long) sketch.getMinValue();

        ReportTable reportTable = new ReportTable("General info", List.of("Metric", "Value"));
        reportTable.addEntry("request count", count);
        reportTable.addEntry("avg response size", average);
        reportTable.addEntry("95% percentile", percentile);
        reportTable.addEntry("max response size", max);
        reportTable.addEntry("min response size", min);

        return reportTable;
    }
}
