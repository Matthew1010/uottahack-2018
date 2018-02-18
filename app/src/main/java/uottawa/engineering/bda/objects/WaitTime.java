package uottawa.engineering.bda.objects;

import org.json.JSONObject;

/**
 * Created by akinyele on 18-02-18.
 */

public class WaitTime {

    private int hour;
    private int min;
    private boolean approximation;

    public WaitTime(JSONObject object) {
        if (object.has("wait_time")) {
            object = object.optJSONObject("wait_time");
        }
        this.hour = object.optInt("hours");
        this.min = object.optInt("mins");
        this.approximation = object.optBoolean("approximation");
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return min;
    }

    public boolean isApproximation() {
        return approximation;
    }

    @Override
    public String toString() {
        return this.getHour() + ":" + this.getMinute();
    }
}
