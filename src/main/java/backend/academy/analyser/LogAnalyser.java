package backend.academy.analyser;

import backend.academy.analyser.arguments.Arguments;
import backend.academy.analyser.arguments.exception.InvalidArgumentException;
import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatters;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.LogRecordField;
import backend.academy.analyser.record.stream.filter.impl.AfterDateStreamFilterFunction;
import backend.academy.analyser.record.stream.filter.impl.BeforeDateStreamFilterFunction;
import backend.academy.analyser.record.stream.filter.impl.ValueFilterPredicate;
import backend.academy.analyser.record.stream.source.LogRecordStreamSources;
import backend.academy.analyser.statistic.StatisticsCollectors;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The LogAnalyser class is responsible for analyzing log records by processing
 * input arguments, applying filters, collecting statistics, and formatting the output.
 */
@SuppressWarnings("ParameterAssignment")
public class LogAnalyser {

    /**
     * Analyzes log records based on the provided command-line arguments.
     *
     * @param args the command-line arguments
     */
    public String analise(String[] args) throws InvalidArgumentException {
        // Parsing arguments
        Arguments arguments = new Arguments();
        parseArguments(arguments, args);

        // Getting record stream
        String path = arguments.path();
        Stream<LogRecord> logRecordStream = getLogRecordStream(path);

        // Applying filters
        logRecordStream = getFilteredStream(logRecordStream, arguments);

        // Collecting statistics
        List<ReportTable> reportTableList = collectStatistics(logRecordStream);

        // Formatting output
        return formatStatistics(arguments.format(), reportTableList);
    }

    /**
     * Parses the command-line arguments using JCommander.
     *
     * @param arguments the Arguments object to populate with parsed data
     * @param args      the command-line arguments
     */
    public void parseArguments(Arguments arguments, String[] args) throws InvalidArgumentException {
        try {
            JCommander commander = JCommander.newBuilder()
                .addObject(arguments)
                .acceptUnknownOptions(true)
                .build();
            commander.parse(args);
        } catch (ParameterException exc) {
            throw new InvalidArgumentException(exc.getMessage(), exc);
        }
    }

    /**
     * Formats the collected statistics into a string based on the specified format.
     *
     * @param format          the desired output format
     * @param reportTableList the list of report tables to format
     * @return a formatted string representation of the statistics
     * @throws IllegalArgumentException if the specified format is not supported
     */
    private String formatStatistics(String format, List<ReportTable> reportTableList) throws InvalidArgumentException {
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

        String message = "Format is not supported, please use one of: " + Arrays.toString(TableFormatters.values());
        throw new InvalidArgumentException(message);
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
     * Applies date and value filters to the log record stream.
     *
     * @param logRecordStream the original stream of log records
     * @param arguments       parsed arguments
     * @return a filtered stream of log records
     */
    private Stream<LogRecord> getFilteredStream(
        Stream<LogRecord> logRecordStream,
        Arguments arguments
    ) {

        LocalDate dateAfter = arguments.dateAfter();
        LocalDate dateBefore = arguments.dateBefore();
        LogRecordField fieldToFilter = arguments.filterField();
        String valueToFilter = arguments.filterValue();

        AfterDateStreamFilterFunction afterDateFilter = new AfterDateStreamFilterFunction(dateAfter);
        BeforeDateStreamFilterFunction beforeDateFilter = new BeforeDateStreamFilterFunction(dateBefore);
        ValueFilterPredicate valueFilter = new ValueFilterPredicate(Pair.of(fieldToFilter, valueToFilter));
        return logRecordStream
            .filter(afterDateFilter)
            .filter(beforeDateFilter)
            .filter(valueFilter);
    }

    /**
     * Retrieves a stream of log records based on the specified path.
     *
     * @param path the path to the log records
     * @return a stream of log records
     * @throws IllegalArgumentException if the path does not match any existing file sources
     */
    private Stream<LogRecord> getLogRecordStream(String path) throws InvalidArgumentException {
        for (LogRecordStreamSources source : LogRecordStreamSources.values()) {
            if (path.matches(source.pattern)) {
                return source.strategy.getLogRecordStream(path);
            }
        }

        throw new InvalidArgumentException("Path doesn't match with existing file sources");
    }
}
