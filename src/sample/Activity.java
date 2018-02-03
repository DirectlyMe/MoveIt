package sample;


/*
Contains information regarding a activity that the user has set up.
The name of the activity.
The start and end time of day that the activity should be called during.
 */
public class Activity {


    private String activityName;
    private Integer startTime;
    private Integer endTime;

    public Activity(String activityName, int startTime, int endTime) {
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

}
