package spotify.oauth2.tests;

import api.applicationAPI.PlaylistAPI;
import com.spotify.oauth2.pojo.ErrorOuter;
import com.spotify.oauth2.pojo.Playlist;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static api.Reusable_Methods.*;
import static api.StatusCodes.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.FakerUtils.*;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")

public class PlaylistTests extends BaseTest {
    String playlistId;

    @Story("Create a Playlist POST API")
    @Severity(CRITICAL)
    @Owner("Abdullah Mohamed")
    @Link(name = "Website", url = "https://dev.example.com/")
    @Issue("AUTH-123")
    @TmsLink("TMS-456")
    @Description("This Testcase is to Validate POST API Functionality and a new Playlist is created")
    @Test(description = "Create Playlist using POST API")
    public void CreateAPlaylist_Test(){
        Playlist playlistRequest = playlistBuilder("New Playlist", "New playlist description", false);

        Response response = PlaylistAPI.post(playlistRequest);

        Playlist playlistResponse = response.as(Playlist.class);

        assertStatusCode(response.statusCode(), STATUS_201);
        assertThat(playlistResponse.getName(), equalTo(playlistRequest.getName()));
        assertThat(playlistResponse.getDescription(), equalTo(playlistRequest.getDescription()));
        assertThat(playlistResponse.get_public(), equalTo(playlistRequest.get_public()));

        boolean isValid = validateSchema(response, "D:\\Users\\amohamed37\\Desktop\\selenium\\RestAssured_Framework_Allure_Lombok\\src\\main\\java\\api\\schema\\postPlaylist.json");
        assertThat(isValid, equalTo(true));

        playlistId = playlistResponse.getId();
    }

    @Story("GET a Playlist using GET API")
    @Description("This Testcase is to Validate GGET API Functionality and created Playlist is retrieved")
    @Test(description = "Able to retrieve created Playlist using GET API", dependsOnMethods = "CreateAPlaylist_Test")
    public void GetPlaylist_Test(){
        Playlist playlistRequest = playlistBuilder("New Playlist", "New playlist description", false);
        Response response = PlaylistAPI.get(playlistId);

        Playlist playlistResponse = response.as(Playlist.class);

        assertStatusCode(response.statusCode(), STATUS_200);
        assertThat(playlistResponse.getName(), equalTo(playlistRequest.getName()));
        assertThat(playlistResponse.getDescription(), equalTo(playlistRequest.getDescription()));
        assertThat(playlistResponse.get_public(), equalTo(!playlistRequest.get_public()));

    }

    @Story("Update a Playlist using PUT API")
    @Description("This Testcase is to Validate PUT API Functionality and a Playlist details are updated")
    @Test(description = "Validate able to Update playlist using PUT API", dependsOnMethods = "GetPlaylist_Test")
    public void UpdateAPlaylist_Test(){
        Playlist playlistRequest = playlistBuilder("Updated Playlist", "Updated playlist description", true);
        Response response = PlaylistAPI.put(playlistRequest, playlistId);

        assertStatusCode(response.statusCode(), STATUS_200);
    }

    @Story("Create a Playlist POST API")
    @Description("This Testcase is to Validate POST API Validation when not supplying a mandatory field {name} and error is returned")
    @Test(description = "Validate that POSTING without mandatory field is not possible")
    public void CreateAPlaylist_Missing_Name_Mandatory_Field_Test(){
        Playlist playlistRequest = playlistBuilder(emptyString(), generateDescription(), generatePublic());

        Response response = PlaylistAPI.post(playlistRequest);

        ErrorOuter errorOuter = response.as(ErrorOuter.class);

        assertStatusCode(response.statusCode(), STATUS_400);
        assertError(errorOuter, STATUS_400);
    }

    @Story("Create a Playlist POST API")
    @Description("This Testcase is to Validate that POSTING a new Playlist is not possible with an Expired Token")
    @Test(description = "Validate not able to POST with Expired Token")
    public void CannotCreateAPlaylist_Expired_Token_Test(){
        Playlist playlistRequest = playlistBuilder(generateName(), generateDescription(), generatePublic());

        Response response = PlaylistAPI.post(playlistRequest, "12345");

        ErrorOuter errorOuter = response.as(ErrorOuter.class);

        assertStatusCode(response.statusCode(), STATUS_401);
        assertError(errorOuter, STATUS_401);
    }
}
