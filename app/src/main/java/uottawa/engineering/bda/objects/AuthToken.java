package uottawa.engineering.bda.objects;

import org.json.JSONObject;

/**
 * Created by akinyele on 18-02-18.
 */

public class AuthToken {

    private String token;

    private AuthToken() {}

    public String getToken() {
        return token;
    }

    private void setToken(JSONObject object) {
        this.token = object.optString("auth_token");
    }

    private static AuthToken instance;
    private static final Object lock = new Object();

    public static AuthToken getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AuthToken();
                }
            }
        }
        return instance;
    }

    public static void setAuthToken(JSONObject object) {
        getInstance().setToken(object);
    }

    public static String current() {
        return getInstance() == null ? null : getInstance().getToken();
    }
}
