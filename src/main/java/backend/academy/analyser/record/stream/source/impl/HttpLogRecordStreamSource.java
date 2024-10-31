package backend.academy.analyser.record.stream.source.impl;

import backend.academy.analyser.record.stream.parse.LogParser;
import backend.academy.analyser.record.LogRecord;
import backend.academy.analyser.record.stream.source.LogRecordStreamSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

public class HttpLogRecordStreamSource implements LogRecordStreamSource {
    @Override
    public Stream<LogRecord> getLogRecordStream(String path) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(path))
            .GET()
            .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
                return LogParser.parseLogStream(reader.lines()).toList().stream();
            }
        } catch (IOException | InterruptedException exc) {
            return Stream.of();
        }
    }
}
