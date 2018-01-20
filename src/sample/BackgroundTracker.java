package sample;

import java.awt.*;
import java.util.Calendar;

public class BackgroundTracker implements Runnable {

    private Activity[] userActivities;
    private Calendar calendar;
    private int hour;
    private int minute;
    private boolean active;
    private TrayIconNotification tin;

    public BackgroundTracker(Activity[] activities) {

        try {
            tin = TrayIconNotification.get();
        }
        catch (AWTException ex) {
            ex.printStackTrace();
        }

        userActivities = activities;
        active = false;
        System.out.println("background thread initialized.");

    }

    @Override
    public void run() {

        System.out.println("background thread running");

        //while (active) {

            try {

                getTime();

                tin.displayTray(userActivities[0]);

                /*for (Activity activity : userActivities) {
                    if (hour > activity.startTime && hour < activity.endTime) {
                        tin.displayTray(activity);
                    }
                }
                */

                Thread.sleep(5000);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        //}

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
