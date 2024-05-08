package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static api.ROUTE_CONSTANTS.*;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(SPOTIFY_URL)
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification getTokenRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(SPOTIFY_TOKEN_URL)
                .setContentType(ContentType.URLENC)
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .build();
    }
    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
