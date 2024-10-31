package backend.academy.analyser.format;

import lombok.Getter;
import java.util.LinkedList;
import java.util.List;

@Getter
public class ReportTable {
    private final String description;
    private final List<String> columns;
    private final List<List<Object>> entries;

    public ReportTable(String description, List<String> columns) {
        this.columns = columns;
        this.description = description;
        this.entries = new LinkedList<>();
    }

    public void addEntry(Object ... values) {
        if(values.length != columns.size()) {
            throw new IllegalArgumentException(
                "Expected " + columns.size() + " arguments, but found " + values.length
            );
        }

        entries.add(List.of(values));
    }
}
