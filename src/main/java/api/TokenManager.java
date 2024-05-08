package api;

import io.restassured.response.Response;
import utils.ConfigLoader;
import utils.LoggerUtil;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager {

    private static String access_token;
    private static Instant expiry_time;
    public synchronized static String getToken(){
        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                LoggerUtil.getLogger().info("Fetching new token..");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                LoggerUtil.getLogger().info("Token is still valid..");
            }
        }catch (Exception e){
            LoggerUtil.getLogger().error("Abort! Failed to fetch token!! \n" + e.fillInStackTrace());
        }

        return access_token;

    }
    private static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<String,String>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

        Response response = RestResources.postToken(formParams);

        if(response.statusCode() != 200){
            LoggerUtil.getLogger().error("Aborting.. Token refresh failed as no success response is received");
        }

        return response;

    }
}
