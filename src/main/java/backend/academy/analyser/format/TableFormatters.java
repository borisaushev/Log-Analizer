package backend.academy.analyser.format;

import backend.academy.analyser.format.impl.AsciiDocTableFormatter;
import backend.academy.analyser.format.impl.MarkdownTableFormatter;

public enum TableFormatters {
    AsciiDOC("adoc", new AsciiDocTableFormatter()),
    MARKDOWN("markdown", new MarkdownTableFormatter());

    public final String value;
    public final TableFormatter strategy;
    TableFormatters(String value, TableFormatter strategy) {
        this.value = value;
        this.strategy = strategy;
    }
}
