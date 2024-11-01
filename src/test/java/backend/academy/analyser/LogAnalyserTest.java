package backend.academy.analyser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LogAnalyserTest {
    LogAnalyser logAnalyser = new LogAnalyser();

    @Test
    void analise() {
        String[] args = new String[] {"--path", "src/main/resources/testLogs.txt",
            "--to", "2024-08-31", "--format", "markdown",
            "--filter-field", "httpMethod",
            "--filter-value", "GET"};

        assertDoesNotThrow(() -> logAnalyser.analise(args));
    }
}
