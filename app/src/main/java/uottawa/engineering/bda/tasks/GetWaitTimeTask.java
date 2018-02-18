package uottawa.engineering.bda.tasks;

import org.json.JSONObject;

import uottawa.engineering.bda.objects.WaitTime;
import uottawa.engineering.bda.api.APIMethods;

/**
 * Created by akinyele on 18-02-18.
 */

public class GetWaitTimeTask extends JSONAsyncTask {

    private TaskWatcher watcher;

    public GetWaitTimeTask(TaskWatcher watcher) {
        this.watcher = watcher;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        if (params.length <= 0) {
            return null;
        }
        String token = params[0];
        return APIMethods.getWaitTime(token);
    }

    @Override
    protected void onPostExecute(JSONObject object) {
        System.out.println("Building time with " + object);
        WaitTime waitTime = new WaitTime(object);
        watcher.onWaitTimeReceive(waitTime);
    }

    public interface TaskWatcher {
        void onWaitTimeReceive(WaitTime time);
    }
}
