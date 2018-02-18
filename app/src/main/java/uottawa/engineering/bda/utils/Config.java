package uottawa.engineering.bda.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by akinyele on 17-04-22.
 */

public final class Config {

    private HashMap<String, Object> hosts;

    private Config(JSONObject object) throws JSONException {
        this.hosts = new HashMap<>();
        this.fillUp(object);
    }

    private void fillUp(JSONObject object) throws JSONException {
        if (object != null) {
            this.hosts = (HashMap<String, Object>) Utils.jsonToMap(object);
        }
    }

    public URL getMainHost() {
        return this.getHost("main");
    }

    public URL getMainAPIHost() {
        return this.getHost("api-main");
    }

    private URL getHost(String key) {
        Object object = this.hosts.get("hosts");
        System.out.println(object);
        if (object instanceof HashMap) {
            HashMap<String, Object> hostsInfo = (HashMap<String, Object>) object;
            HashMap<String, Object> specInfo = ((HashMap<String, Object>) hostsInfo.get(key));

            Object protocol = specInfo.get("protocol");
            Object domain = specInfo.get("domain");
            Object subDomain = specInfo.get("sub_domain");
            if (Utils.isNull(domain)) {
                System.err.println("Domain not found.");
                return null;
            }
            if (Utils.isNull(protocol)) {
                protocol = "http";
            }
            if (!Utils.isNull(subDomain)) {
                domain = String.format("%s.%s", subDomain, domain);
            }
            String urlString = String.format("%s://%s", protocol, domain);
            try {
                return new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
        System.out.println("Invalid Config JSON.");
        return null;
    }

    private static final String FILENAME = "config.json";
    private static Config CONFIG;
    private static JSONObject jsonObject;

    public static final Object sync = new Object();

    private static void initialize() throws JSONException {
        if (CONFIG == null) {
            synchronized (sync) {
                if (CONFIG == null) {
                    CONFIG = new Config(jsonObject);
                }
            }
        }
    }

    public static Config getInstance() {
        try {
            initialize();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return CONFIG;
    }

    public static JSONObject getConfig(Context context) {
        if (context == null) {
            return null;
        }
        String contents = Utils.readFileSafe(context, FILENAME);
        if (contents == null) {
            return null;
        }
        try {
            JSONObject object = new JSONObject(contents);
            jsonObject = object;
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
