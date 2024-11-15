package backend.academy.analyser.format.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatter;
import java.util.List;

/**
 * Formatter for converting a {@link ReportTable} into an AsciiDoc table format.
 * <p>
 * This formatter calculates appropriate column widths based on the content
 * and aligns data in AsciiDoc format with headers and rows.
 * </p>
 */
public class AsciiDocTableFormatter extends TableFormatter {

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
            asciidoc.append('.').append(reportTable.description()).append('\n');
        }
        String asciiBorder = "|===\n";
        String asciiLineEnd = "|\n";

        // Start the AsciiDoc table syntax
        asciidoc.append(asciiBorder);

        // Generate and align column headers
        for (int i = 0; i < reportTable.columns().size(); i++) {
            String column = reportTable.columns().get(i);
            asciidoc.append("| ").append(padRight(column, columnWidths.get(i))).append(' ');
        }
        asciidoc.append(asciiLineEnd);

        // Generate and align each row of table entries
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                String value = entry.get(i) != null ? entry.get(i).toString() : "";
                asciidoc.append("| ").append(padRight(value, columnWidths.get(i))).append(' ');
            }
            asciidoc.append(asciiLineEnd);
        }

        // End the AsciiDoc table syntax
        asciidoc.append(asciiBorder);

        return asciidoc.toString();
    }

    // Helper method to align text to the required width
    private String padRight(String text, int width) {
        return String.format("%-" + width + 's', text);
    }
}
