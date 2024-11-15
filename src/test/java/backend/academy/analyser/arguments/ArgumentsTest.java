package backend.academy.analyser.arguments;

import backend.academy.analyser.LogAnalyser;
import com.beust.jcommander.JCommander;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgumentsTest {

    @Test
    @DisplayName("all possible parameters")
    void allArgumentsTest() {
        // Given
        String[] args = {
            "--path", "logs/logs.txt",
            "--from", "2024-08-31", "--to", "2024-09-30",
            "--format", "markdown"
        };
        Arguments jArgs = new Arguments();
        JCommander commander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();

        // When
        commander.parse(args);

        // Then
        assertEquals(jArgs.path(), "logs/logs.txt");
        assertEquals(jArgs.dateAfter(), LocalDate.parse("2024-08-31"));
        assertEquals(jArgs.dateBefore(), LocalDate.parse("2024-09-30"));
        assertEquals(jArgs.format(), "markdown");
    }

    @Test
    @DisplayName("not all not-required parameters")
    void notAllArgumentsTest() {
        // Given
        String[] args = {
            "--path", "logs/logs.txt",
            "--from", "2024-08-31",
            "--format", "markdown"
        };
        Arguments jArgs = new Arguments();
        JCommander commander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();

        // When
        commander.parse(args);

        // Then
        assertEquals(jArgs.path(), "logs/logs.txt");
        assertEquals(jArgs.dateAfter(), LocalDate.parse("2024-08-31"));
        assertNull(jArgs.dateBefore());
        assertEquals(jArgs.format(), "markdown");
    }

    @Test
    @DisplayName("not all required parameters")
    void notAllRequiredArgumentsTest() {
        // Given
        String[] args = {
            "--from", "2024-08-31",
            "--format", "markdown"
        };
        Arguments jArgs = new Arguments();
        JCommander commander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();

        // When
        Executable executable = () -> commander.parse(args);

        // Then
        assertThrows(Exception.class, executable);
    }

    @Test
    @DisplayName("with unknown arguments")
    void unknownArgumentsTest() {
        // Given
        String[] args = {
            "--path", "logs/logs.txt",
            "--from", "2024-08-31",
            "--format", "markdown",
            "--notReal", "RyanGosling"
        };
        Arguments jArgs = new Arguments();
        LogAnalyser logAnalyser = new LogAnalyser();

        // When
        Executable executable = () -> logAnalyser.parseArguments(jArgs, args);

        // Then
        assertDoesNotThrow(executable);
    }
}
