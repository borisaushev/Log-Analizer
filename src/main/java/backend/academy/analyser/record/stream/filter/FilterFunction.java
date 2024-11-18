package backend.academy.analyser.record.stream.filter;

import backend.academy.analyser.record.LogRecord;
import java.util.function.Predicate;

public interface FilterFunction extends Predicate<LogRecord> {

}
