package backend.academy.analyser.arguments;

import backend.academy.analyser.arguments.converter.LocalDateConverter;
import com.beust.jcommander.Parameter;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Arguments {
    @Parameter(
        names = {"--from"},
        description = "Date in format yyyy-MM-dd",
        converter = LocalDateConverter.class
    )
    private LocalDate dateAfter;

    @Parameter(
        names = {"--to"},
        description = "Date in format yyyy-MM-dd",
        converter = LocalDateConverter.class
    )
    private LocalDate dateBefore;

    @Parameter(
        names = {"--path"},
        required = true,
        description = "path, either URL or path to local file"
    )
    private String path;

    @Parameter(
        names = {"--format"},
        required = true,
        description = "Output format"
    )
    private String format;
}
