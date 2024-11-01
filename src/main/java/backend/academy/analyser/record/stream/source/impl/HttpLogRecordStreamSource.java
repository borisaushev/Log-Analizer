package backend.academy.analyser.record.stream.source.impl;

import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.parse.LogParser;
import backend.academy.analyser.record.stream.source.LogRecordStreamSource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * A class that retrieves log records from an HTTP source and provides them as a stream of {@link LogRecord} objects.
 * Implements the {@link LogRecordStreamSource} interface.
 */
@SuppressFBWarnings("OS_OPEN_STREAM")
public class HttpLogRecordStreamSource implements LogRecordStreamSource {

    /**
     * Retrieves a stream of {@link LogRecord} objects from the specified HTTP source.
     *
     * @param path the URI of the HTTP resource containing log records
     * @return a stream of {@link LogRecord} objects; returns an empty stream if an error occurs
     */
    @Override
    public Stream<LogRecord> getLogRecordStream(String path) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(path))
            .GET()
            .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8));
            // Wrapping Stream<LogRecord> so that BufferedReader is closed when the Stream is closed
            return LogParser.parseLogStream(reader.lines())
                .onClose(() -> {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException("Unable to close InputStream", e);
                    }
                });
        } catch (IOException | InterruptedException exc) {
            throw new RuntimeException("Error occurred reading remote file", exc);
        }
    }
}
