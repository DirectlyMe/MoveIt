package sample;

import java.util.Calendar;

public class BackgroundTracker implements Runnable {

    private Activity[] userActivities;
    private Calendar calendar;
    private int hour;
    private int minute;
    public boolean active;

    public BackgroundTracker(Activity[] activities) {

        userActivities = activities;
        active = false;
        System.out.println("background thread initialized.");

    }

    @Override
    public void run() {

        System.out.println("background thread running");

        while (active) {

            getTime();

            for (Activity activity : userActivities) {
                if (hour > activity.startTime && hour < activity.endTime) {

                }
            }
        }

    }

    private void getTime() {
        calendar = Calendar.getInstance();

        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
