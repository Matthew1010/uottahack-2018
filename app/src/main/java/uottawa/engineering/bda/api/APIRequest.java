package uottawa.engineering.bda.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import uottawa.engineering.bda.utils.Utils;

/**
 * Created by akinyele on 17-04-22.
 */

public final class APIRequest {

    private APIRequest() {}

    static JSONObject get(URL url) {
        return get(url, null);
    }

    static JSONObject get(URL url, String path) {
        return get(url, path, null);
    }

    static JSONObject get(URL url, String path, HashMap<String, String> params) {
        return request("GET", url, path, params);
    }

    static JSONObject post(URL url) {
        return post(url, null);
    }

    static JSONObject post(URL url, String path) {
        return post(url, path, null);
    }

    static JSONObject post(URL url, String path, HashMap<String, String> params) {
        return request("POST", url, path, params);
    }

    private static JSONObject request(String method, URL myURL, String path, HashMap<String, String> params) {
        if (myURL == null) {
            System.err.println("URL cannot be null");
            return null;
        }

        String urlParams = Utils.formatParams(params);
        URL url;
        try {
            if (path != null && !path.trim().isEmpty()) {
                if (myURL.toString().endsWith("/") && path.startsWith("/")) {
                    path = path.substring(1, path.length()-1);
                } else if (!myURL.toString().endsWith("/") && !path.startsWith("/")) {
                    path = String.format("/%s", path);
                }
                path = myURL.toString() + path;
            } else {
                path = myURL.toString();
            }
            url = new URL(path + urlParams);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        JSONObject result = new JSONObject();
        try {
            URLConnection connection = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) connection;
            http.setRequestMethod(method);
            if (method.equals("POST")) {
                http.setDoOutput(true);
            }
            http.connect();

            try {
                InputStream res = http.getInputStream();
                String response = Utils.read(res);
                result = new JSONObject(response);
            } catch (JSONException e) {
                System.err.println("Invalid JSON response: " + e.getLocalizedMessage());
                result = null;
            }
        } catch (IOException e) {
            try {
                result.put("exception", e.getLocalizedMessage());
                result.put("message", "Could not establish a connection with our servers. Please try again later.");
                result.put("type", e.getClass().getSimpleName());
            } catch (JSONException e1) {
                e1.printStackTrace();
                result = null;
            }
        }

        return result;
    }

}
