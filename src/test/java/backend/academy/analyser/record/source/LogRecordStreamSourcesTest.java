package backend.academy.analyser.record.source;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogRecordStreamSourcesTest {

    @DisplayName("Valid file path")
    @ParameterizedTest
    @ValueSource(strings = {"/app/file.txt", "C:/file.txt", "C:/path/path2/file2.txt", "file2.log"})
    public void filePatternValidTest(String path) {
        String filePattern = LogRecordStreamSources.LOCAL_FILE.pattern;
        assertTrue(path.matches("((([A-z]:[\\\\/])|([\\\\/]))?([A-z0-9]+([\\\\/]))*)?([A-z0-9]+((\\.[A-z]+)|\\*))"));
    }

    @DisplayName("Invalid file path")
    @ParameterizedTest
    @ValueSource(strings = {"file.2x", "C://file.txt", "https://google.com/file.txt", "file"})
    public void filePatternInvalidTest(String path) {
        String filePattern = LogRecordStreamSources.LOCAL_FILE.pattern;
        assertFalse(path.matches(filePattern));
    }

    @DisplayName("Valid http path")
    @ParameterizedTest
    @ValueSource(strings = {
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
        "http://google.com/logs.txt",
        "http://google.com/logs"})
    public void httpPatternValidTest(String path) {
        String httpPattern = LogRecordStreamSources.HTTP.pattern;
        assertTrue(path.matches(httpPattern));
    }

    @DisplayName("Invalid http path")
    @ParameterizedTest
    @ValueSource(strings = {"https://raw.githubusercontent.com",
        "C://file.txt",
        "ftp://file.txt",
        "https://file.txt"})
    public void httpPatternInvalidTest(String path) {
        String httpPattern = LogRecordStreamSources.HTTP.pattern;
        assertFalse(path.matches(httpPattern));
    }
}
