package com.augmentedcooking.Enums.Http;

public enum ErrorMessages {
    /**
     * Informational responses
     */
    CONTINUE("Continue"),
    SWITCHING_PROTOCOLS("Switching Protocols"),
    PROCESSING_DEPRECATED("Processing Deprecated"),
    EARLY_HINTS("Early Hints"),

    /**
     * Successful responses
     */
    OK("OK"),
    CREATED("Created"),
    ACCEPTED("Accepted"),
    NONAUTHORITATIVE_INFORMATION("NonAuthoritative Information"),
    NO_CONTENT("No Content"),
    RESET_CONTENT("Reset Content"),
    PARTIAL_CONTENT("Partial Content"),
    MULTISTATUS("MultiStatus"),
    ALREADY_REPORTED("Already Reported"),
    IM_USED("IM Used"),

    /**
     * Redirection messages
     */
    MULTIPLE_CHOICES("Multiple Choices"),
    MOVED_PERMANENTLY("Moved Permanently"),
    FOUND("Found"),
    SEE_OTHER("See Other"),
    NOT_MODIFIED("Not Modified"),
    USE_PROXY_DEPRECATED("Use Proxy Deprecated"),
    UNUSED("Unused"),
    TEMPORARY_REDIRECT("Temporary Redirect"),
    PERMANENT_REDIRECT("Permanent Redirect"),

    /**
     * Client error responses
     */
    BAD_REQUEST("Bad Request"),
    UNAUTHORIZED("Unauthorized"),
    PAYMENT_REQUIRED("Payment Required"),
    FORBIDDEN("Forbidden"),
    NOT_FOUND("Not Found"),
    METHOD_NOT_ALLOWED("Method Not Allowed"),
    NOT_ACCEPTABLE("Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED("Proxy Authentication Required"),
    REQUEST_TIMEOUT("Request Timeout"),
    CONFLICT("Conflict"),
    GONE("Gone"),
    LENGTH_REQUIRED("Length Required"),
    PRECONDITION_FAILED("Precondition Failed"),
    CONTENT_TOO_LARGE("Content Too Large"),
    URI_TOO_LONG("URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE("Unsupported Media Type"),
    RANGE_NOT_SATISFIABLE("Range Not Satisfiable"),
    EXPECTATION_FAILED("Expectation Failed"),
    IM_A_TEAPOT("I'm a teapot"),
    MISDIRECTED_REQUEST("Misdirected Request"),
    UNPROCESSABLE_CONTENT("Unprocessable Content"),
    LOCKED("Locked"),
    FAILED_DEPENDENCY("Failed Dependency"),
    TOO_EARLY_EXPERIMENTAL("Too Early Experimental"),
    UPGRADE_REQUIRED("Upgrade Required"),
    PRECONDITION_REQUIRED("Precondition Required"),
    TOO_MANY_REQUESTS("Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE("Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS("Unavailable For Legal Reasons"),

    /**
     * Server error responses
     */
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    NOT_IMPLEMENTED("Not Implemented"),
    BAD_GATEWAY("Bad Gateway"),
    SERVICE_UNAVAILABLE("Service Unavailable"),
    GATEWAY_TIMEOUT("Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED("HTTP Version Not Supported"),
    VARIANT_ALSO_NEGOTIATES("Variant Also Negotiates"),
    INSUFFICIENT_STORAGE("Insufficient Storage"),
    LOOP_DETECTED("Loop Detected"),
    NOT_EXTENDED("Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED("Network Authentication Required");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String value() {
        return message;
    }
}
