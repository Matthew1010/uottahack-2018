package uottawa.engineering.bda.utils;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by akinyele on 17-04-22.
 */

public final class Utils {

    private Utils() {}

    public static URL join(String currentURL, String... paths) {
        try {
            return join(new URL(currentURL), paths);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL join(URL current, Object... paths) {
        if (current == null) {
            return null;
        }
        String currentURL = current.toString();
        boolean paramsAdded = false;
        for (Object pathObject : paths) {
            System.out.println("Current URL b4: " + currentURL);
            if (pathObject instanceof String) {
                if (!currentURL.endsWith("/")) {
                    currentURL = currentURL.concat("/");
                }
                String path = (String) pathObject;
                if (path.startsWith("/")) {
                    path = path.substring(1, path.length() - 1);
                }
                currentURL = currentURL.concat(path);
            } else if (pathObject instanceof HashMap) {
                HashMap<String, String> params = (HashMap<String, String>) pathObject;
                String paramsString = Utils.formatParams(params, !paramsAdded);
                currentURL = currentURL.concat(paramsString);
                paramsAdded = true;
            }
            System.out.println("Current URL after: " + currentURL);
        }

        try {
            return new URL(currentURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatParams(HashMap<String, String> params) {
        return formatParams(params, true);
    }

    public static String formatParams(HashMap<String, String> params, boolean first) {
        String urlParams = "";
        if (params != null && !params.isEmpty()) {
            urlParams += first ? "?" : "&";
            for (String key : params.keySet()) {
                String paramSet = "%s=%s";
                String value = params.get(key);
                paramSet = String.format(paramSet, key, value);
                if (urlParams.length() > 1) {
                    urlParams += "&";
                }
                urlParams += paramSet;
            }
        }
        return urlParams;
    }

    public static String readFileSafe(Context context, String filename) {
        try {
            return readFile(context, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readFile(Context context, String filename) throws IOException {
        AssetManager am = context.getAssets();
        InputStream is = am.open(filename);

        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static boolean isNull(Object object) {
        return String.valueOf(object).trim().toLowerCase().equals("null");
    }

    public static String read(InputStream stream) {
        if (stream == null) {
            return null;
        }
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(isr);
        try {
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDate(String d) {
        return getDate(d, "MM/dd/yyyy");
    }

    public static Date getDate(String d, String format) {
        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return formatter.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

}
