package backend.academy.analyser.format;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

/**
 * Represents a tabular report structure with a description, column headers, and entries.
 * <p>
 * This class allows creating a table by defining column headers and adding rows of entries,
 * while ensuring the number of values in each entry matches the number of columns.
 * </p>
 */
@Getter
public class ReportTable {
    private final String description;
    private final List<String> columns;
    private final List<List<Object>> entries;

    /**
     * Constructs a new {@code ReportTable} with the specified description and columns.
     *
     * @param description the description of the report table
     * @param columns     the list of column headers for the table
     */
    public ReportTable(String description, List<String> columns) {
        this.columns = columns;
        this.description = description;
        this.entries = new LinkedList<>();
    }

    /**
     * Adds a new entry (row) to the table.
     * <p>
     * The number of values provided must match the number of columns. If the count of values
     * does not match the columns, an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param values the values to be added as a new row in the table
     * @throws IllegalArgumentException if the number of values does not match the number of columns
     */
    public void addEntry(Object... values) {
        if (values.length != columns.size()) {
            throw new IllegalArgumentException(
                "Expected " + columns.size() + " arguments, but found " + values.length
            );
        }

        entries.add(List.of(values));
    }
}
