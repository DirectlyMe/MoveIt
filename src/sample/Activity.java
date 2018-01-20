package sample;


/*
Contains information regarding a activity that the user has set up.
The name of the activity.
The start and end time of day that the activity should be called during.
 */
public class Activity {

    String activityName;
    double startTime;
    double endTime;

    public Activity(String activityName, double startTime, double endTime) {
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
