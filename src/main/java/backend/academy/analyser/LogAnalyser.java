package backend.academy.analyser;

import backend.academy.analyser.arguments.Arguments;
import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatters;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.filter.impl.AfterDateStreamFilter;
import backend.academy.analyser.record.filter.impl.BeforeDateStreamFilter;
import backend.academy.analyser.record.stream.source.LogRecordStreamSources;
import backend.academy.analyser.statistic.StatisticsCollectors;
import com.beust.jcommander.JCommander;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The LogAnalyser class is responsible for analyzing log records by processing
 * input arguments, applying filters, collecting statistics, and formatting the output.
 */
public class LogAnalyser {

    /**
     * Analyzes log records based on the provided command-line arguments.
     *
     * @param args the command-line arguments
     */
    public void analise(String[] args) {
        // Parsing arguments
        Arguments arguments = new Arguments();
        parseArguments(arguments, args);

        // Getting record stream
        String path = arguments.path();
        Stream<LogRecord> logRecordStream = getLogRecordStream(path);

        // Applying filters
        logRecordStream = applyFilters(logRecordStream, arguments.dateAfter(), arguments.dateBefore());

        // Collecting statistics
        List<ReportTable> reportTableList = collectStatistics(logRecordStream);

        // Formatting output
        String output = formatStatistics(arguments.format(), reportTableList);

        System.out.println(output);
    }

    /**
     * Parses the command-line arguments using JCommander.
     *
     * @param arguments the Arguments object to populate with parsed data
     * @param args      the command-line arguments
     */
    public void parseArguments(Arguments arguments, String[] args) {
        JCommander commander = JCommander.newBuilder()
            .addObject(arguments)
            .acceptUnknownOptions(true)
            .build();
        commander.parse(args);
    }

    /**
     * Formats the collected statistics into a string based on the specified format.
     *
     * @param format           the desired output format
     * @param reportTableList  the list of report tables to format
     * @return a formatted string representation of the statistics
     * @throws IllegalArgumentException if the specified format is not supported
     */
    private String formatStatistics(String format, List<ReportTable> reportTableList) {
        StringBuilder output = new StringBuilder();
        for (TableFormatters tableFormatter : TableFormatters.values()) {
            // If we matched the format, return formatted output
            if (tableFormatter.value.equalsIgnoreCase(format)) {
                for (ReportTable reportTable : reportTableList) {
                    output.append(tableFormatter.strategy.formatTable(reportTable));
                    output.append('\n');
                }
                return output.toString();
            }
        }

        throw new IllegalArgumentException("Format is not supported");
    }

    /**
     * Collects statistics from the provided log record stream.
     *
     * @param logRecordStream the stream of log records to analyze
     * @return a list of report tables containing the collected statistics
     */
    private List<ReportTable> collectStatistics(Stream<LogRecord> logRecordStream) {
        logRecordStream.forEach(r -> {
            for (StatisticsCollectors statisticsCollector : StatisticsCollectors.values()) {
                statisticsCollector.strategy.include(r);
            }
        });

        List<ReportTable> reportTableList = new LinkedList<>();
        for (StatisticsCollectors statisticsCollector : StatisticsCollectors.values()) {
            reportTableList.add(statisticsCollector.strategy.getStatistic());
        }

        return reportTableList;
    }

    /**
     * Applies date filters to the log record stream.
     *
     * @param logRecordStream the original stream of log records
     * @param dateAfter      the date after which records should be included (can be null)
     * @param dateBefore     the date before which records should be included (can be null)
     * @return a filtered stream of log records
     */
    private Stream<LogRecord> applyFilters(
        Stream<LogRecord> logRecordStream,
        LocalDate dateAfter,
        LocalDate dateBefore
    ) {
        AfterDateStreamFilter afterDateStreamFilter = new AfterDateStreamFilter();
        BeforeDateStreamFilter beforeDateStreamFilter = new BeforeDateStreamFilter();

        if (dateAfter != null) {
            logRecordStream = afterDateStreamFilter.filterStream(logRecordStream, dateAfter);
        }
        if (dateBefore != null) {
            logRecordStream = beforeDateStreamFilter.filterStream(logRecordStream, dateBefore);
        }
        return logRecordStream;
    }

    /**
     * Retrieves a stream of log records based on the specified path.
     *
     * @param path the path to the log records
     * @return a stream of log records
     * @throws IllegalArgumentException if the path does not match any existing file sources
     */
    private Stream<LogRecord> getLogRecordStream(String path) {
        LogRecordStreamSources logRecordStreamSource;
        for (LogRecordStreamSources source : LogRecordStreamSources.values()) {
            if (path.matches(source.pattern)) {
                return source.strategy.getLogRecordStream(path);
            }
        }

        throw new IllegalArgumentException("Path doesn't match with existing file sources");
    }
}
