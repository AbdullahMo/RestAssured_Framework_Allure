package api;
import io.restassured.response.Response;
import java.util.HashMap;

import static api.ROUTE_CONSTANTS.API;
import static api.ROUTE_CONSTANTS.TOKEN;
import static api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResources {

    public static Response post(Object playlistRequest, String path, String access_token){
        return given(getRequestSpec())
                .auth().oauth2(access_token)
                .body(playlistRequest)
        .when().post(path)
        .then().spec(getResponseSpec())
                .extract().response();
    }

    public static Response postToken(HashMap<String, String> formParams){
        return given(getTokenRequestSpec())
                .formParams(formParams)
        .when().post(API+ TOKEN)
        .then().spec(getResponseSpec())
                .extract().response();
    }
    public static Response get(String path, String access_token){
        return given(getRequestSpec())
                .auth().oauth2(access_token)
        .when().get(path)
        .then().spec(getResponseSpec())
                .extract().response();
    }

    public static Response put(Object playlistRequest, String path, String access_token){
        return given(getRequestSpec())
                .auth().oauth2(access_token)
                .body(playlistRequest)
        .when().put(path)
        .then()
                .extract().response();
    }
}
