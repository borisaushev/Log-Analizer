package backend.academy.analyser.statistic;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.format.ReportTable;

public interface StatisticsCollector {
    void clear();
    void include(LogRecord logRecord);
    ReportTable getStatistic();
}
