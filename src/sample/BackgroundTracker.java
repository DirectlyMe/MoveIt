package sample;



import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.*;


/*
Once the program is closed this thread continues to run and check
for a relevant notification every hour.
 */
public class BackgroundTracker implements Runnable, GlobalMouseListener.CallBack {

    private Activity[] userActivities;
    private GlobalMouseListener mouseListener;
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

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                if (active) {
                    try {

                        getTime();

                        for (Activity activity : userActivities) {
                            if (hour >= activity.startTime && hour <= activity.endTime) {
                                tin.displayTray(activity);
                                break;
                            }
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        },
        360000);

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException intEx) {
            intEx.printStackTrace();
        }
    }

    //gets the time of day
    private void getTime() {
        calendar = Calendar.getInstance();

        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void setActive(boolean activity) {
        active = activity;
    }

    public void setActiveUser(boolean active) {
        active = true;
    }

}
