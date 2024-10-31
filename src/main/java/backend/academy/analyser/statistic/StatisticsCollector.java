package backend.academy.analyser.statistic;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;

public interface StatisticsCollector {
    void clear();

    void include(LogRecord logRecord);

    ReportTable getStatistic();
}
