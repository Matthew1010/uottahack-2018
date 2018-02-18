package uottawa.engineering.bda.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akinyele on 17-04-23.
 */

public abstract class JSONAsyncTask extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected abstract void onPostExecute(JSONObject object);

    protected JSONObject getError(String message) {
        return this.getError(null, message);
    }

    protected JSONObject getError(Throwable t, String message) {
        JSONObject object = new JSONObject();
        try {
            if (t != null) {
                object.put("error", t);
                object.put("message", t.getLocalizedMessage());
                object.put("cause", t.getClass().getSimpleName());
            }
            if (message != null) {
                if (object.has("message")) {
                    object.put("error-message", object.optString("message"));
                }
                object.put("message", message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
}
