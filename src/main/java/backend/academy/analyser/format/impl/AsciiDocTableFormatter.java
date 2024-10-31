package backend.academy.analyser.format.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatter;
import java.util.ArrayList;
import java.util.List;

public class AsciiDocTableFormatter implements TableFormatter {
    // Method to calculate the maximum width for each column
    private List<Integer> calculateColumnWidths(ReportTable reportTable) {
        List<Integer> widths = new ArrayList<>();

        // Initial widths are set to the lengths of column headers
        for (String column : reportTable.columns()) {
            widths.add(column.length());
        }

        // Iterate through each entry and update the width for each column
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                int valueLength = entry.get(i) != null ? entry.get(i).toString().length() : 0;
                widths.set(i, Math.max(widths.get(i), valueLength));
            }
        }

        return widths;
    }

    @Override
    public String formatTable(ReportTable reportTable) {
        List<Integer> columnWidths = calculateColumnWidths(reportTable);
        StringBuilder asciidoc = new StringBuilder();

        // Add table description if it is specified
        if (reportTable.description() != null && !reportTable.description().isEmpty()) {
            asciidoc.append(".").append(reportTable.description()).append("\n");
        }

        // Start the AsciiDoc table
        asciidoc.append("|===\n");

        // Generate column headers
        for (int i = 0; i < reportTable.columns().size(); i++) {
            String column = reportTable.columns().get(i);
            asciidoc.append("| ").append(padRight(column, columnWidths.get(i))).append(" ");
        }
        asciidoc.append("|\n");

        // Generate table rows
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                String value = entry.get(i) != null ? entry.get(i).toString() : "";
                asciidoc.append("| ").append(padRight(value, columnWidths.get(i))).append(" ");
            }
            asciidoc.append("|\n");
        }

        // End the AsciiDoc table
        asciidoc.append("|===\n");

        return asciidoc.toString();
    }

    // Helper method to align text to the required width
    private String padRight(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
