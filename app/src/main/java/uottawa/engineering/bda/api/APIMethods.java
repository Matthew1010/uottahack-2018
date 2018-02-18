package uottawa.engineering.bda.api;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import uottawa.engineering.bda.utils.Base64;
import uottawa.engineering.bda.utils.Config;

/**
 * Created by akinyele on 17-04-22.
 */

public final class APIMethods {

    private static final URL URL = Config.getInstance().getMainAPIHost();
    private static final URL SERVER_URL = Config.getInstance().getMainHost();

    private APIMethods() {}

    public static URL getAPIURL() {
        return URL;
    }

    public static URL getServerURL() {
        return SERVER_URL;
    }

    public static JSONObject generateToken() {
        return APIRequest.post(URL, "/api/new/token");
    }

    public static JSONObject getWaitTime(String token) {
        HashMap<String, String> params = createTokenParam(token);
        return APIRequest.get(URL, "/api/tickets/wait-time", params);
    }

    public static JSONObject getByID(int id, String what, String token) {
        HashMap<String, String> params = createTokenParam(token);
        params.put("id", String.valueOf(id));
        return APIRequest.post(URL, String.format("/api/get/%s", what), params);
    }

    private static HashMap<String, String> createTokenParam(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("auth_token", token);
        return params;
    }

}
