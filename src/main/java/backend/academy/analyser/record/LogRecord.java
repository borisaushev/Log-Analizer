package backend.academy.analyser.record;

import java.time.ZonedDateTime;

public record LogRecord(
    String remoteAddress,
    String remoteUser,
    ZonedDateTime timeZoned,
    String httpMethod,
    String uri,
    String httpVersion,
    int statusCode,
    int bodyBytesSent,
    String httpReferer,
    String httpUserAgent) {
}