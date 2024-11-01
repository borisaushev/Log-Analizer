package backend.academy.analyser.format;

/**
 * Interface for formatting a {@link ReportTable} into a specific string representation.
 * <p>
 * Implementations of this interface define how to convert
 * a table into different formats
 * </p>
 */
public interface TableFormatter {
    /**
     * Formats the specified {@code ReportTable} into a string representation.
     *
     * @param reportTable the {@link ReportTable} to be formatted
     * @return the formatted string representation of the table
     */
    String formatTable(ReportTable reportTable);
}
