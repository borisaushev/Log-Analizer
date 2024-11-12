package backend.academy.analyser.arguments;

import backend.academy.analyser.arguments.converter.LocalDateConverter;
import backend.academy.analyser.arguments.converter.RecordFieldConverter;
import backend.academy.analyser.record.LogRecordField;
import com.beust.jcommander.Parameter;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Class representing command-line arguments for the backend academy analyzer application.
 * <p>
 * This class defines the possible arguments, including date range, file path, and output format,
 * and supports automatic conversion of date arguments into {@link LocalDate} using the {@link LocalDateConverter}.
 * </p>
 *
 * <p> Usage example: </p>
 * <pre>
 * {@code
 * java -jar analyser.jar --from 2024-01-01 --to 2024-12-31 --path /data/logs --format markdown
 * }
 * </pre>
 */
@Getter
public class Arguments {
    /**
     * Start date for the data range filter.
     * <p>
     * Expected format: {@code yyyy-MM-dd}.
     * </p>
     */
    @Parameter(
        names = {"--from"},
        description = "Date in format yyyy-MM-dd",
        converter = LocalDateConverter.class
    )
    private LocalDate dateAfter;

    /**
     * End date for the data range filter.
     * <p>
     * Expected format: {@code yyyy-MM-dd}.
     * </p>
     */
    @Parameter(
        names = {"--to"},
        description = "Date in format yyyy-MM-dd",
        converter = LocalDateConverter.class
    )
    private LocalDate dateBefore;

    /**
     * Path to the input data, which can be a URL or a local file path.
     * <p>
     * This parameter is required and represents the location(URL or local file path)
     * of the log file to analyze.
     * </p>
     */
    @Parameter(
        names = {"--path"},
        required = true,
        description = "Path, either URL or path to local file"
    )
    private String path;

    /**
     * Output format for the analysis results.
     * <p>
     * This parameter is required and determines
     * the format (AsciiDOC or markdown) of the output.
     * </p>
     */
    @Parameter(
        names = {"--format"},
        required = true,
        description = "Output format"
    )
    private String format;

    /**
     * Field to filter, must be one of
     * LogRecordField enum specified values
     */
    @Parameter(
        names = {"--filter-field"},
        description = "field to filter",
        converter = RecordFieldConverter.class
    )
    private LogRecordField filterField;

    /**
     * Value to filter
     */
    @Parameter(
        names = {"--filter-value"},
        description = "value to filter"
    )
    private String filterValue;
}
