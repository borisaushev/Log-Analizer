package backend.academy.analyser.record.stream.source;

import backend.academy.analyser.record.stream.source.impl.HttpLogRecordStreamSource;
import backend.academy.analyser.record.stream.source.impl.LocalFileLogRecordStreamSource;

public enum LogRecordStreamSources {
    LOCAL_FILE(
        "((([A-z]:[\\\\/])|([\\\\/]))?([A-z0-9]+([\\\\/]))+)?([A-z0-9]+((\\.[A-z]+)|\\*))",
        new LocalFileLogRecordStreamSource()
    ),
    HTTP(
        "https?://[A-z0-9%.]+(/[%A-z0-9]+)+([%A-z0-9.]+)",
        new HttpLogRecordStreamSource()
    );

    public final String pattern;
    public final LogRecordStreamSource strategy;

    LogRecordStreamSources(String pattern, LogRecordStreamSource strategy) {
        this.pattern = pattern;
        this.strategy = strategy;
    }
}
