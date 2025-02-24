package com.augmentedcooking.Enums.Http;

import lombok.Getter;

@Getter
public enum HttpStatus {
    /**
     * Informational responses
     */
    CONTINUE(100, ErrorCodes.CONTINUE, ErrorMessages.CONTINUE),
    SWITCHING_PROTOCOLS(101, ErrorCodes.SWITCHING_PROTOCOLS, ErrorMessages.SWITCHING_PROTOCOLS),
    PROCESSING_DEPRECATED(102, ErrorCodes.PROCESSING_DEPRECATED, ErrorMessages.PROCESSING_DEPRECATED),
    EARLY_HINTS(103, ErrorCodes.EARLY_HINTS, ErrorMessages.EARLY_HINTS),

    /**
     * Successful responses
     */
    OK(200, ErrorCodes.OK, ErrorMessages.OK),
    CREATED(201, ErrorCodes.CREATED, ErrorMessages.CREATED),
    ACCEPTED(202, ErrorCodes.ACCEPTED, ErrorMessages.ACCEPTED),
    NONAUTHORITATIVE_INFORMATION(203, ErrorCodes.NONAUTHORITATIVE_INFORMATION,
            ErrorMessages.NONAUTHORITATIVE_INFORMATION),
    NO_CONTENT(204, ErrorCodes.NO_CONTENT, ErrorMessages.NO_CONTENT),
    RESET_CONTENT(205, ErrorCodes.RESET_CONTENT, ErrorMessages.RESET_CONTENT),
    PARTIAL_CONTENT(206, ErrorCodes.PARTIAL_CONTENT, ErrorMessages.PARTIAL_CONTENT),
    MULTISTATUS(207, ErrorCodes.MULTISTATUS, ErrorMessages.MULTISTATUS),
    ALREADY_REPORTED(208, ErrorCodes.ALREADY_REPORTED, ErrorMessages.ALREADY_REPORTED),
    IM_USED(226, ErrorCodes.IM_USED, ErrorMessages.IM_USED),

    /**
     * Redirection messages
     */
    MULTIPLE_CHOICES(300, ErrorCodes.MULTIPLE_CHOICES, ErrorMessages.MULTIPLE_CHOICES),
    MOVED_PERMANENTLY(301, ErrorCodes.MOVED_PERMANENTLY, ErrorMessages.MOVED_PERMANENTLY),
    FOUND(302, ErrorCodes.FOUND, ErrorMessages.FOUND),
    SEE_OTHER(303, ErrorCodes.SEE_OTHER, ErrorMessages.SEE_OTHER),
    NOT_MODIFIED(304, ErrorCodes.NOT_MODIFIED, ErrorMessages.NOT_MODIFIED),
    USE_PROXY_DEPRECATED(305, ErrorCodes.USE_PROXY_DEPRECATED, ErrorMessages.USE_PROXY_DEPRECATED),
    UNUSED(306, ErrorCodes.UNUSED, ErrorMessages.UNUSED),
    TEMPORARY_REDIRECT(307, ErrorCodes.TEMPORARY_REDIRECT, ErrorMessages.TEMPORARY_REDIRECT),
    PERMANENT_REDIRECT(308, ErrorCodes.PERMANENT_REDIRECT, ErrorMessages.PERMANENT_REDIRECT),

    /**
     * Client error responses
     */
    BAD_REQUEST(400, ErrorCodes.BAD_REQUEST, ErrorMessages.BAD_REQUEST),
    UNAUTHORIZED(401, ErrorCodes.UNAUTHORIZED, ErrorMessages.UNAUTHORIZED),
    PAYMENT_REQUIRED(402, ErrorCodes.PAYMENT_REQUIRED, ErrorMessages.PAYMENT_REQUIRED),
    FORBIDDEN(403, ErrorCodes.FORBIDDEN, ErrorMessages.FORBIDDEN),
    NOT_FOUND(404, ErrorCodes.NOT_FOUND, ErrorMessages.NOT_FOUND),
    METHOD_NOT_ALLOWED(405, ErrorCodes.METHOD_NOT_ALLOWED, ErrorMessages.METHOD_NOT_ALLOWED),
    NOT_ACCEPTABLE(406, ErrorCodes.NOT_ACCEPTABLE, ErrorMessages.NOT_ACCEPTABLE),
    PROXY_AUTHENTICATION_REQUIRED(407, ErrorCodes.PROXY_AUTHENTICATION_REQUIRED,
            ErrorMessages.PROXY_AUTHENTICATION_REQUIRED),
    REQUEST_TIMEOUT(408, ErrorCodes.REQUEST_TIMEOUT, ErrorMessages.REQUEST_TIMEOUT),
    CONFLICT(409, ErrorCodes.CONFLICT, ErrorMessages.CONFLICT),
    GONE(410, ErrorCodes.GONE, ErrorMessages.GONE),
    LENGTH_REQUIRED(411, ErrorCodes.LENGTH_REQUIRED, ErrorMessages.LENGTH_REQUIRED),
    PRECONDITION_FAILED(412, ErrorCodes.PRECONDITION_FAILED, ErrorMessages.PRECONDITION_FAILED),
    CONTENT_TOO_LARGE(413, ErrorCodes.CONTENT_TOO_LARGE, ErrorMessages.CONTENT_TOO_LARGE),
    URI_TOO_LONG(414, ErrorCodes.URI_TOO_LONG, ErrorMessages.URI_TOO_LONG),
    UNSUPPORTED_MEDIA_TYPE(415, ErrorCodes.UNSUPPORTED_MEDIA_TYPE, ErrorMessages.UNSUPPORTED_MEDIA_TYPE),
    RANGE_NOT_SATISFIABLE(416, ErrorCodes.RANGE_NOT_SATISFIABLE, ErrorMessages.RANGE_NOT_SATISFIABLE),
    EXPECTATION_FAILED(417, ErrorCodes.EXPECTATION_FAILED, ErrorMessages.EXPECTATION_FAILED),
    IM_A_TEAPOT(418, ErrorCodes.IM_A_TEAPOT, ErrorMessages.IM_A_TEAPOT),
    MISDIRECTED_REQUEST(421, ErrorCodes.MISDIRECTED_REQUEST, ErrorMessages.MISDIRECTED_REQUEST),
    UNPROCESSABLE_CONTENT(422, ErrorCodes.UNPROCESSABLE_CONTENT, ErrorMessages.UNPROCESSABLE_CONTENT),
    LOCKED(423, ErrorCodes.LOCKED, ErrorMessages.LOCKED),
    FAILED_DEPENDENCY(424, ErrorCodes.FAILED_DEPENDENCY, ErrorMessages.FAILED_DEPENDENCY),
    TOO_EARLY_EXPERIMENTAL(425, ErrorCodes.TOO_EARLY_EXPERIMENTAL, ErrorMessages.TOO_EARLY_EXPERIMENTAL),
    UPGRADE_REQUIRED(426, ErrorCodes.UPGRADE_REQUIRED, ErrorMessages.UPGRADE_REQUIRED),
    PRECONDITION_REQUIRED(428, ErrorCodes.PRECONDITION_REQUIRED, ErrorMessages.PRECONDITION_REQUIRED),
    TOO_MANY_REQUESTS(429, ErrorCodes.TOO_MANY_REQUESTS, ErrorMessages.TOO_MANY_REQUESTS),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, ErrorCodes.REQUEST_HEADER_FIELDS_TOO_LARGE,
            ErrorMessages.REQUEST_HEADER_FIELDS_TOO_LARGE),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, ErrorCodes.UNAVAILABLE_FOR_LEGAL_REASONS,
            ErrorMessages.UNAVAILABLE_FOR_LEGAL_REASONS),

    /**
     * Server error responses
     */
    INTERNAL_SERVER_ERROR(500, ErrorCodes.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR),
    NOT_IMPLEMENTED(501, ErrorCodes.NOT_IMPLEMENTED, ErrorMessages.NOT_IMPLEMENTED),
    BAD_GATEWAY(502, ErrorCodes.BAD_GATEWAY, ErrorMessages.BAD_GATEWAY),
    SERVICE_UNAVAILABLE(503, ErrorCodes.SERVICE_UNAVAILABLE, ErrorMessages.SERVICE_UNAVAILABLE),
    GATEWAY_TIMEOUT(504, ErrorCodes.GATEWAY_TIMEOUT, ErrorMessages.GATEWAY_TIMEOUT),
    HTTP_VERSION_NOT_SUPPORTED(505, ErrorCodes.HTTP_VERSION_NOT_SUPPORTED, ErrorMessages.HTTP_VERSION_NOT_SUPPORTED),
    VARIANT_ALSO_NEGOTIATES(506, ErrorCodes.VARIANT_ALSO_NEGOTIATES, ErrorMessages.VARIANT_ALSO_NEGOTIATES),
    INSUFFICIENT_STORAGE(507, ErrorCodes.INSUFFICIENT_STORAGE, ErrorMessages.INSUFFICIENT_STORAGE),
    LOOP_DETECTED(508, ErrorCodes.LOOP_DETECTED, ErrorMessages.LOOP_DETECTED),
    NOT_EXTENDED(510, ErrorCodes.NOT_EXTENDED, ErrorMessages.NOT_EXTENDED),
    NETWORK_AUTHENTICATION_REQUIRED(511, ErrorCodes.NETWORK_AUTHENTICATION_REQUIRED,
            ErrorMessages.NETWORK_AUTHENTICATION_REQUIRED);

    private final int responseCode;
    private final ErrorCodes errorCode;
    private final ErrorMessages message;

    HttpStatus(int responseCode, ErrorCodes errorCode, ErrorMessages message) {
        this.responseCode = responseCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int responseCode() {
        return responseCode;
    }

    public ErrorCodes errorCode() {
        return errorCode;
    }

    public ErrorMessages message() {
        return message;
    }

    /**
     * Returns the HttpStatus corresponding to the given response code.
     *
     * @param code the HTTP response code
     * @return the HttpStatus corresponding to the given response code
     * @throws IllegalArgumentException if no matching HttpStatus is found for the
     *                                  given response code
     */
    public static HttpStatus fromCode(int code) {
        for (HttpStatus status : values()) {
            if (status.responseCode == code)
                return status;
        }
        throw new IllegalArgumentException("No matching HttpStatus for response code: " + code);
    }

    public boolean isServerError() {
        return this.responseCode >= 500 && this.responseCode < 600;
    }
}
