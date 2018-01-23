package sample;

import jdk.nashorn.internal.objects.Global;

import java.awt.*;
import java.util.Calendar;

/*
Once the program is closed this thread continues to run and check
for a relevant notification every hour.
 */
public class BackgroundTracker implements Runnable {

    private Activity[] userActivities;
    private GlobalKeyboardListener keyboardListener;
    private Calendar calendar;
    private int hour;
    private int minute;
    private boolean active;
    private TrayIconNotification tin;

    /*
    Constructor provides a single instance of TrayIconNotification, sets activities array
    and initializes active
    */
    public BackgroundTracker() {

        try {
            tin = TrayIconNotification.get();
        }
        catch (AWTException ex) {
            ex.printStackTrace();
        }

        userActivities = UserSettings.getActivities();
        active = false;
        System.out.println("background thread initialized.");

    }

    /*
    uses a while loop to check the time of day and find a relevant
    activity recommendation.
     */
    public void run() {

        System.out.println("background thread running");

        keyboardListener.run();

        while (active) {

            try {

                getTime();

                for (Activity activity : userActivities) {
                    if (hour >= activity.startTime && hour <= activity.endTime) {
                        tin.displayTray(activity);
                        break;
                    }
                }


                Thread.sleep(3600000);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    //gets the time of day
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
