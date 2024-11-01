package backend.academy.analyser.format.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Formatter for converting a {@link ReportTable} into a Markdown table format.
 * <p>
 * This formatter calculates appropriate column widths based on the content and
 * generates a Markdown table with aligned headers and rows.
 * </p>
 */
public class MarkdownTableFormatter implements TableFormatter {
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
     * Formats a {@link ReportTable} as a Markdown table.
     * <p>
     * Includes an optional description as a header, followed by headers, rows, and separators in Markdown format.
     * </p>
     *
     * @param reportTable the {@link ReportTable} to format
     * @return a String representation of the table in Markdown format
     */
    @Override
    public String formatTable(ReportTable reportTable) {
        List<Integer> columnWidths = calculateColumnWidths(reportTable);
        StringBuilder markdown = new StringBuilder();

        // Add table description if provided
        markdown.append("### ").append(reportTable.description()).append("\n");

        // Generate and align column headers
        markdown.append("| ");
        for (int i = 0; i < reportTable.columns().size(); i++) {
            String column = reportTable.columns().get(i);
            markdown.append(padRight(column, columnWidths.get(i))).append(" | ");
        }
        markdown.append("\n");

        // Header separator with dashes for each column
        markdown.append("|");
        for (int width : columnWidths) {
            markdown.append(" ").append("-".repeat(width)).append(" |");
        }
        markdown.append("\n");

        // Generate and align each row of table entries
        for (List<Object> entry : reportTable.entries()) {
            markdown.append("| ");
            for (int i = 0; i < entry.size(); i++) {
                String value = entry.get(i) != null ? entry.get(i).toString() : "";
                markdown.append(padRight(value, columnWidths.get(i))).append(" | ");
            }
            markdown.append("\n");
        }

        return markdown.toString();
    }

    // Helper method to align text to the required width
    private String padRight(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
