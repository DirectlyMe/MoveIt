package sample;



import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;


/*
Once the program is closed this thread continues to run and check
for a relevant notification every hour.
 */
public class BackgroundTracker implements Runnable, PropertyChangeListener {

    private Activity[] userActivities;
    private GlobalMouseListener mouseListener;
    private Calendar calendar;
    private int hour;
    private int minute;
    private boolean active;
    private TrayIconNotification tin;

    /*
    Constructor provides a single instance of TrayIconNotification, an instance of
    globalmouselistener, sets activities array
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

        mouseListener = new GlobalMouseListener();
        mouseListener.addChangeListener(this);



    }

    /*
    uses a while loop to check the time of day and find a relevant
    activity recommendation.
     */
    public void run() {

        System.out.println("background thread running");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("Activity Timer ran");
                if (active) {
                    try {

                        getTime();

                        for (Activity activity : userActivities) {
                            if (hour >= activity.getStartTime() && hour <= activity.getEndTime()) {
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
        3000, 360000);

        mouseListener.run();

    }

    //gets the time of day
    private void getTime() {
        calendar = Calendar.getInstance();

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void propertyChange(PropertyChangeEvent event) {
        active = (boolean) event.getNewValue();
    }


}
