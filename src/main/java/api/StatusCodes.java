package api;

public enum StatusCodes {

    STATUS_200(200, ""),
    STATUS_201(201, ""),
    STATUS_400(400, "Missing required field:" + ".*"),
    STATUS_401(401, "Invalid access token");

    public final int code;
    public final String message;

    StatusCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
