package uottawa.engineering.bda.tasks;

import org.json.JSONException;
import org.json.JSONObject;

import uottawa.engineering.bda.api.APIMethods;
import uottawa.engineering.bda.objects.AuthToken;

/**
 * Created by akinyele on 17-04-22.
 */

public class GetTokenTask extends JSONAsyncTask {

    private GetTokenWatcher mWatcher;

    public GetTokenTask(GetTokenWatcher watcher) {
        mWatcher = watcher;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return APIMethods.generateToken();
    }

    @Override
    protected void onPostExecute(JSONObject object) {
        AuthToken.setAuthToken(object);
        mWatcher.setTokenResponse();
    }

    public interface GetTokenWatcher {
        void setTokenResponse();
    }
}
