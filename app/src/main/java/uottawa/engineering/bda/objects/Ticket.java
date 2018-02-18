package uottawa.engineering.bda.objects;

import org.json.JSONObject;

/**
 * Created by akinyele on 18-02-18.
 */

public class Ticket {

    private int id;
    private String ticketCode;

    public Ticket(JSONObject object) {
        this.id = object.optInt("id");
        this.ticketCode = object.optString("ticket_code");
    }

    public int getId() {
        return id;
    }

    public String getTicketCode() {
        return ticketCode;
    }
}
