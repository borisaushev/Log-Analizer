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
 * @param remoteUser    the username of the authenticated user, or {@code null} if not applicable
 * @param timeZoned     the timestamp of the request in a zoned date-time format
 * @param httpMethod    the HTTP method used for the request (e.g., GET, POST)
 * @param uri           the requested resource's URI
 * @param httpVersion   the version of the HTTP protocol used (e.g., HTTP/1.1)
 * @param statusCode    the HTTP status code returned by the server
 * @param bodyBytesSent the number of bytes sent in the response body
 * @param httpReferer   the referrer URL, or {@code null} if not applicable
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

    public static LogRecordBuilder builder() {
        return new LogRecordBuilder();
    }

    public static class LogRecordBuilder {
        private String remoteAddress;
        private String remoteUser;
        private ZonedDateTime timeZoned;
        private String httpMethod;
        private String uri;
        private String httpVersion;
        private int statusCode;
        private int bodyBytesSent;
        private String httpReferer;
        private String httpUserAgent;

        public LogRecordBuilder remoteAddress(String remoteAddress) {
            this.remoteAddress = remoteAddress;
            return this;
        }

        public LogRecordBuilder remoteUser(String remoteUser) {
            this.remoteUser = remoteUser;
            return this;
        }

        public LogRecordBuilder timeZoned(ZonedDateTime timeZoned) {
            this.timeZoned = timeZoned;
            return this;
        }

        public LogRecordBuilder httpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public LogRecordBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public LogRecordBuilder httpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public LogRecordBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public LogRecordBuilder bodyBytesSent(int bodyBytesSent) {
            this.bodyBytesSent = bodyBytesSent;
            return this;
        }

        public LogRecordBuilder httpReferer(String httpReferer) {
            this.httpReferer = httpReferer;
            return this;
        }

        public LogRecordBuilder httpUserAgent(String httpUserAgent) {
            this.httpUserAgent = httpUserAgent;
            return this;
        }

        public LogRecord build() {
            return new LogRecord(
                remoteAddress,
                remoteUser,
                timeZoned,
                httpMethod,
                uri,
                httpVersion,
                statusCode,
                bodyBytesSent,
                httpReferer,
                httpUserAgent
            );
        }
    }
}
