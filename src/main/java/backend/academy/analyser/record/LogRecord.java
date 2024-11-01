package backend.academy.analyser.record;

import java.time.ZonedDateTime;

/**
 * A record representing a single log entry from an HTTP server.
 * This record encapsulates details such as the remote address,
 * user information, timestamp, HTTP method, requested URI,
 * HTTP version, status code, number of bytes sent, referrer,
 * and user agent.
 *
 * @param remoteAddress the IP address of the client making the request
 * @param remoteUser the username of the authenticated user, or {@code null} if not applicable
 * @param timeZoned the timestamp of the request in a zoned date-time format
 * @param httpMethod the HTTP method used for the request (e.g., GET, POST)
 * @param uri the requested resource's URI
 * @param httpVersion the version of the HTTP protocol used (e.g., HTTP/1.1)
 * @param statusCode the HTTP status code returned by the server
 * @param bodyBytesSent the number of bytes sent in the response body
 * @param httpReferer the referrer URL, or {@code null} if not applicable
 * @param httpUserAgent the user agent string of the client making the request
 */
@SuppressWarnings("RecordComponentNumber")
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
