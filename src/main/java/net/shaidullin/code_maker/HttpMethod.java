package net.shaidullin.code_maker;

/**
 * Http methods
 */
public enum HttpMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    private final String code;

    HttpMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static HttpMethod createByCode(String code) {
        for (HttpMethod value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return code;
    }
}
