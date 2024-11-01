package backend.academy.analyser.statistic;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;

/**
 * An interface for collecting statistics from log records.
 * Implementing classes should provide methods to include log records
 * and generate a report table summarizing the collected statistics.
 */
public interface StatisticsCollector {

    /**
     * Includes a log record for statistical analysis.
     *
     * @param logRecord the log record to be included in the statistics
     */
    void include(LogRecord logRecord);

    /**
     * Generates a report table containing the collected statistics.
     *
     * @return a {@link ReportTable} summarizing the statistics collected
     */
    ReportTable getStatistic();
}
