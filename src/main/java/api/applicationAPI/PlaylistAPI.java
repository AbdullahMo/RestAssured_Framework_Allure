package api.applicationAPI;

import api.RestResources;
import com.spotify.oauth2.pojo.Playlist;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.ConfigLoader;

import static api.ROUTE_CONSTANTS.PLAYLISTS;
import static api.ROUTE_CONSTANTS.USERS;
import static api.TokenManager.getToken;

public class PlaylistAPI {
    @Step
    public static Response post(Playlist playlistRequest){
        return RestResources.post(playlistRequest, USERS + "/" + ConfigLoader.getInstance().getPropertyValue("user_id") + PLAYLISTS, getToken());
    }
    @Step
    public static Response post(Playlist playlistRequest, String token){
        return RestResources.post(playlistRequest,  USERS + "/" + ConfigLoader.getInstance().getPropertyValue("user_id") + PLAYLISTS, token);
    }
    @Step
    public static Response get(String playlistId){
        return RestResources.get(PLAYLISTS + "/" + playlistId, getToken());
    }
    @Step
    public static Response put(Playlist playlistRequest, String playlistId){
        return RestResources.put(playlistRequest, PLAYLISTS + "/" + playlistId, getToken());
    }


}
