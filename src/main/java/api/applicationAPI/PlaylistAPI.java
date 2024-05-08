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
    //static String access_token = "BQBvKSzT3XivEZx817lCSH_CGxmhEWXKuJc-gLQAXJfcgGZY6_XN1qn4hynxbQrUwp9fis-ajagufTl0kpwjCzrwXz29DmfvDm3mnhhWANYUqTJBfizwBQSBJqIQ24wQ6u0PXCX_LkquEiKZH1scvd9kDUFmm0i6zYiWz8WPxxvcJhGpYxL3fg1Fl7Uk3qdVjZ5Z19qRgdim-8cQxhGcjYBpCzUAkBOESifXmA1KfODtCDN5KFZKna8Ej0XjUc-BDPd3vPXEe2Dja5BP";

    @Step
    public static Response post(Playlist playlistRequest){
        return RestResources.post(playlistRequest, USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken());
    }
    @Step
    public static Response post(Playlist playlistRequest, String token){
        return RestResources.post(playlistRequest,  USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, token);
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
