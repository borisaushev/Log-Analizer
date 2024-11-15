package backend.academy.analyser.format;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for formatting a {@link ReportTable} into a specific string representation.
 * <p>
 * Implementations of this interface define how to convert
 * a table into different formats
 * </p>
 */
public abstract class TableFormatter {
    /**
     * Calculates the maximum width for each column based on the header and entry content.
     *
     * @param reportTable the {@link ReportTable} containing columns and entries to be formatted
     * @return a list of integers representing the maximum width for each column
     */
    protected List<Integer> calculateColumnWidths(ReportTable reportTable) {
        List<Integer> widths = new ArrayList<>(reportTable.columns().size());

        // Initial widths set to column header lengths
        for (String column : reportTable.columns()) {
            widths.add(column.length());
        }

        // Update each column's width to accommodate the longest entry in each column
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                int valueLength = entry.get(i) != null ? entry.get(i).toString().length() : 0;
                widths.set(i, Math.max(widths.get(i), valueLength));
            }
        }

        return widths;
    }

    /**
     * Formats the specified {@code ReportTable} into a string representation.
     *
     * @param reportTable the {@link ReportTable} to be formatted
     * @return the formatted string representation of the table
     */
    abstract public String formatTable(ReportTable reportTable);
}
