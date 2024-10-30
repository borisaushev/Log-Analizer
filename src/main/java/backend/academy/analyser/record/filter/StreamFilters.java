package backend.academy.analyser.record.filter;

import backend.academy.analyser.record.filter.impl.AfterDateStreamFilter;
import backend.academy.analyser.record.filter.impl.BeforeDateStreamFilter;

public enum StreamFilters {
    AFTER_DATE("from", "\\d{4}-\\d{2}-\\d{2}", new AfterDateStreamFilter()),
    BEFORE_DATE("to", "\\d{4}-\\d{2}-\\d{2}", new BeforeDateStreamFilter());

    public final String parameter;
    public final String pattern;
    public final StreamFilter filter;
    StreamFilters(String parameter, String pattern, StreamFilter filter) {
        this.parameter = parameter;
        this.pattern = pattern;
        this.filter = filter;
    }
}
