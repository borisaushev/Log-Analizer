package backend.academy.analyser.record.arguments;

import backend.academy.analyser.arguments.Arguments;
import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {
    @Test
    @DisplayName("all parameters")
    void allArgumentsTest() {
        String[] args = {
            "--path", "logs/logs.txt",
            "--from", "2024-08-31", "--to", "2024-09-30",
            "--format", "markdown"
        };
        Arguments jArgs = new Arguments();
        JCommander commander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();

        commander.parse(args);

        assertEquals(jArgs.path(), "logs/logs.txt");
        assertEquals(jArgs.dateAfter(), LocalDate.parse("2024-08-31"));
        assertEquals(jArgs.dateBefore(), LocalDate.parse("2024-09-30"));
        assertEquals(jArgs.format(), "markdown");
    }

    @Test
    @DisplayName("not all not-required parameters")
    void notAllArgumentsTest() {
        String[] args = {
            "--path", "logs/logs.txt",
            "--from", "2024-08-31",
            "--format", "markdown"
        };
        Arguments jArgs = new Arguments();
        JCommander commander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();

        commander.parse(args);

        assertEquals(jArgs.path(), "logs/logs.txt");
        assertEquals(jArgs.dateAfter(), LocalDate.parse("2024-08-31"));
        assertNull(jArgs.dateBefore());
        assertEquals(jArgs.format(), "markdown");
    }

    @Test
    @DisplayName("not all required parameters")
    void notAllReArgumentsTest() {
        String[] args = {
            "--from", "2024-08-31",
            "--format", "markdown"
        };
        Arguments jArgs = new Arguments();
        JCommander commander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();

        assertThrows(Exception.class, () -> commander.parse(args));
    }
}
