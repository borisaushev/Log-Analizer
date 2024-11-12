package backend.academy.analyser.format;

import backend.academy.analyser.format.impl.AsciiDocTableFormatter;
import backend.academy.analyser.format.impl.MarkdownTableFormatter;

/**
 * Enum representing available table formatters, mapping each format to its strategy.
 * <p>
 * Each enum constant specifies a unique format identifier and a corresponding {@link TableFormatter}
 * implementation for formatting tables in that format.
 * </p>
 */
public enum TableFormatters {
    /** Formatter for AsciiDoc format */
    AsciiDOC("adoc", new AsciiDocTableFormatter()),

    /** Formatter for Markdown format */
    MARKDOWN("markdown", new MarkdownTableFormatter());

    /** The identifier for the table format */
    public final String value;

    /** The formatting strategy associated with the table format */
    public final TableFormatter strategy;

    /**
     * Constructs a new {@code TableFormatters} enum constant with the specified format identifier
     * and formatting strategy.
     *
     * @param value    the identifier for the table format
     * @param strategy the {@link TableFormatter} implementation for the format
     */
    TableFormatters(String value, TableFormatter strategy) {
        this.value = value;
        this.strategy = strategy;
    }
}
