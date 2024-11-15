package backend.academy.analyser.arguments;

import backend.academy.analyser.LogAnalyser;
import com.beust.jcommander.JCommander;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void notAllRequiredArgumentsTest() {
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

    @Test
    @DisplayName("with unknown arguments")
    void unknownArgumentsTest() {
        String[] args = {
            "--path", "logs/logs.txt",
            "--from", "2024-08-31",
            "--format", "markdown",
            "--notReal", "RyanGosling"
        };
        Arguments jArgs = new Arguments();

        assertDoesNotThrow(() -> new LogAnalyser().parseArguments(jArgs, args));
    }
}
