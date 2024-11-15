package backend.academy.analyser.record.stream.source;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogRecordStreamSourcesTest {

    @DisplayName("Valid file path")
    @ParameterizedTest
    @ValueSource(strings = {"/app/file.txt", "C:/file.txt", "C:/path/path2/file2.txt", "file2.log"})
    public void filePatternValidTest(String path) {
        //Given
        String filePattern = LogRecordStreamSources.LOCAL_FILE.pattern;

        //Then
        assertThat(path).matches(filePattern);
    }

    @DisplayName("Invalid file path")
    @ParameterizedTest
    @ValueSource(strings = {"file.2x", "C://file.txt", "https://google.com/file.txt", "file"})
    public void filePatternInvalidTest(String path) {
        //Given
        String filePattern = LogRecordStreamSources.LOCAL_FILE.pattern;

        //Then
        assertThat(path).doesNotMatch(filePattern);
    }

    @DisplayName("Valid http path")
    @ParameterizedTest
    @ValueSource(strings = {
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
        "http://google.com/logs.txt",
        "http://google.com/logs"})
    public void httpPatternValidTest(String path) {
        //Given
        String httpPattern = LogRecordStreamSources.HTTP.pattern;

        //Then
        assertThat(path).matches(httpPattern);
    }

    @DisplayName("Invalid http path")
    @ParameterizedTest
    @ValueSource(strings = {"https://raw.githubusercontent.com",
        "C://file.txt",
        "ftp://file.txt",
        "https://file.txt"})
    public void httpPatternInvalidTest(String path) {
        //Given
        String httpPattern = LogRecordStreamSources.HTTP.pattern;

        //Then
        assertThat(path).doesNotMatch(httpPattern);
    }
}
