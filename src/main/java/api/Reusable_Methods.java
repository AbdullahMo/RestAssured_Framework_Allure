package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.spotify.oauth2.pojo.ErrorOuter;
import com.spotify.oauth2.pojo.Playlist;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.LoggerUtil;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class Reusable_Methods {
    @Step("Building Playlist Payload")
    public static Playlist playlistBuilder(String playlistName, String playlistDescription, boolean _public){
//        return new Playlist().
//                .setName(playlistName)
//                .setDescription(playlistDescription)
//                .setPublic(_public);
        // Using Lombok Builder
        return Playlist.builder().name(playlistName)
                .description(playlistDescription)
                ._public(_public)
                .build();
    }

    @Step
    public static void assertStatusCode(int actualStatusCode, StatusCodes expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode.code));
    }

    @Step
    public static void assertError(ErrorOuter errResponse, StatusCodes statusCode){
        assertThat(errResponse.getError().getStatus(), equalTo(statusCode.code));
        assertThat(errResponse.getError().getMessage(), matchesPattern(statusCode.message));
    }

    @Step
    public static boolean validateSchema(Response response, String schemaPath){
        try{
            // Read schema file using Jackson JsonLoader
            JsonNode schemaReader = JsonLoader.fromFile(new File(schemaPath));

            // Parse JSON schema
            JsonSchema jsonSchema = JsonSchemaFactory.byDefault().getJsonSchema(schemaReader);

            // Parse Response body to JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJsonNode = objectMapper.readTree(response.getBody().asString());

            ProcessingReport report = jsonSchema.validate(responseJsonNode);

            // Log validation errors, if any
            if (!report.isSuccess()) {
                for (ProcessingMessage message : report) {
                    LoggerUtil.getLogger().error("Schema validation error: {}", message.getMessage());
                }
            }
            return report.isSuccess();
        }catch(Exception e){
            LoggerUtil.getLogger().error("Failed to parse json schema file or File was not found..", e.fillInStackTrace());
            return false;
        }
    }
}
