package backend.academy.analyser;

import backend.academy.analyser.arguments.Arguments;
import backend.academy.analyser.format.TableFormatters;
import backend.academy.analyser.record.filter.impl.AfterDateStreamFilter;
import backend.academy.analyser.record.filter.impl.BeforeDateStreamFilter;
import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.source.LogRecordStreamSources;
import backend.academy.analyser.statistic.StatisticsCollectors;
import com.beust.jcommander.JCommander;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogAnalyser {

    public void analise(String[] args) {
        //Parsing arguments
        Arguments arguments = new Arguments();
        parseArguments(arguments, args);

        //Getting record stream
        String path = arguments.path();
        Stream<LogRecord> logRecordStream = getLogRecordStream(path);

        //applying filters
        logRecordStream = applyFilters(logRecordStream, arguments.dateAfter(), arguments.dateBefore());

        //collecting statistics
        List<ReportTable> reportTableList = collectStatistics(logRecordStream);

        //formatting
        String output = formatStatistics(arguments.format(), reportTableList);

        System.out.println(output);
    }

    private void parseArguments(Arguments arguments, String[] args) {
        JCommander commander = JCommander.newBuilder()
            .addObject(arguments)
            .build();
        commander.parse(args);
    }

    public String formatStatistics(String format, List<ReportTable> reportTableList) {
        StringBuilder output = new StringBuilder();
        for(TableFormatters tableFormatter : TableFormatters.values()) {
            //If we matched the format, return formatted output
            if(tableFormatter.value.equalsIgnoreCase(format)) {
                for(ReportTable reportTable : reportTableList) {
                    output.append(tableFormatter.strategy.formatTable(reportTable));
                    output.append('\n');
                }
                return output.toString();
            }
        }

        throw new IllegalArgumentException("Format is not supported");
    }

    public List<ReportTable> collectStatistics(Stream<LogRecord> logRecordStream) {
        logRecordStream.forEach(r -> {
            for(StatisticsCollectors statisticsCollector : StatisticsCollectors.values()) {
                statisticsCollector.strategy.include(r);
            }
        });

        List<ReportTable> reportTableList = new LinkedList<>();
        for(StatisticsCollectors statisticsCollector : StatisticsCollectors.values()) {
            reportTableList.add(statisticsCollector.strategy.getStatistic());
        }

        return reportTableList;
    }

    public Stream<LogRecord> applyFilters(
        Stream<LogRecord> logRecordStream,
        LocalDate dateAfter,
        LocalDate dateBefore
    ) {
        AfterDateStreamFilter afterDateStreamFilter = new AfterDateStreamFilter();
        BeforeDateStreamFilter beforeDateStreamFilter = new BeforeDateStreamFilter();

        if(dateAfter != null) {
            logRecordStream = afterDateStreamFilter.filterStream(logRecordStream, dateAfter);
        }
        if(dateBefore != null) {
            logRecordStream = beforeDateStreamFilter.filterStream(logRecordStream, dateBefore);
        }
        return logRecordStream;
    }

    public Stream<LogRecord> getLogRecordStream(String path) {
        LogRecordStreamSources logRecordStreamSource;
        for (LogRecordStreamSources source : LogRecordStreamSources.values()) {
            if (path.matches(source.pattern)) {
                return source.strategy.getLogRecordStream(path);
            }
        }

        throw new IllegalArgumentException("Path doesn't match with existing file sources");
    }
}
