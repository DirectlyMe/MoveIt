package moveIt;

/*
Holds users activities, and the frequency of the notifications displaying.
 */
public class UserSettings {

    private static Activity[] activities;

    private static int notification_frequency = 3600000;


    public static Activity[] getActivities() {
        return activities;
    }

    public void setActivities(Activity[] activities) {
        UserSettings.activities = activities;
    }

    public static int getNotification_frequency() {
        return notification_frequency;
    }

    public static void setNotification_frequency(int notification_frequency) {
        UserSettings.notification_frequency = notification_frequency;
    }
}