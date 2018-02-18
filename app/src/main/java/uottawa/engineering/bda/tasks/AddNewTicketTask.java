package uottawa.engineering.bda.tasks;

import org.json.JSONObject;

import uottawa.engineering.bda.api.APIMethods;
import uottawa.engineering.bda.objects.AuthToken;
import uottawa.engineering.bda.objects.Ticket;

/**
 * Created by akinyele on 18-02-18.
 */

public class AddNewTicketTask extends JSONAsyncTask {

    @Override
    protected JSONObject doInBackground(String... params) {
        if (params.length <= 0) {
            return null;
        }
        String code = params[0];
        return APIMethods.addNewTicket(AuthToken.current(), code);
    }

    @Override
    protected void onPostExecute(JSONObject object) {

    }

    public interface TaskWatcher {
        void onTicketSaved(Ticket ticket);
    }
}
