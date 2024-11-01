package backend.academy.analyser.format.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Formatter for converting a {@link ReportTable} into an AsciiDoc table format.
 * <p>
 * This formatter calculates appropriate column widths based on the content
 * and aligns data in AsciiDoc format with headers and rows.
 * </p>
 */
public class AsciiDocTableFormatter implements TableFormatter {
    /**
     * Calculates the maximum width for each column based on the header and entry content.
     *
     * @param reportTable the {@link ReportTable} containing columns and entries to be formatted
     * @return a list of integers representing the maximum width for each column
     */
    private List<Integer> calculateColumnWidths(ReportTable reportTable) {
        List<Integer> widths = new ArrayList<>();

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
     * Formats a {@link ReportTable} as an AsciiDoc table.
     * <p>
     * Includes an optional description if provided, followed by headers and rows in AsciiDoc format.
     * </p>
     *
     * @param reportTable the {@link ReportTable} to format
     * @return a String representation of the table in AsciiDoc format
     */
    @Override
    public String formatTable(ReportTable reportTable) {
        List<Integer> columnWidths = calculateColumnWidths(reportTable);
        StringBuilder asciidoc = new StringBuilder();

        // Add table description if provided
        if (reportTable.description() != null && !reportTable.description().isEmpty()) {
            asciidoc.append(".").append(reportTable.description()).append("\n");
        }

        // Start the AsciiDoc table syntax
        asciidoc.append("|===\n");

        // Generate and align column headers
        for (int i = 0; i < reportTable.columns().size(); i++) {
            String column = reportTable.columns().get(i);
            asciidoc.append("| ").append(padRight(column, columnWidths.get(i))).append(" ");
        }
        asciidoc.append("|\n");

        // Generate and align each row of table entries
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                String value = entry.get(i) != null ? entry.get(i).toString() : "";
                asciidoc.append("| ").append(padRight(value, columnWidths.get(i))).append(" ");
            }
            asciidoc.append("|\n");
        }

        // End the AsciiDoc table syntax
        asciidoc.append("|===\n");

        return asciidoc.toString();
    }

    // Helper method to align text to the required width
    private String padRight(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
