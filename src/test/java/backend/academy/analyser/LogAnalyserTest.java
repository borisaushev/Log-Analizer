package backend.academy.analyser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LogAnalyserTest {
    LogAnalyser logAnalyser = new LogAnalyser();

    @Test
    @DisplayName("Whole process test")
    void analise() {
        String[] args = new String[] {"--path", "src/main/resources/testLogs.txt",
            "--to", "2024-08-31", "--format", "markdown",
            "--filter-field", "httpMethod",
            "--filter-value", "GET"};

        Executable executable = () -> logAnalyser.analise(args);

        assertDoesNotThrow(executable);
    }
}
