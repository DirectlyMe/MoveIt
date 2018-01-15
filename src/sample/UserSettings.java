package sample;

public class UserSettings {

    private String[] mActivityRecommendations;
    private String[] mActivityTimes;
    private String userSettingsFilePath;

    public String[] getActivityRecommendations() {
        return mActivityRecommendations;
    }

    public void setActivityRecommendations(String[] mActivityRecommendations) {
        this.mActivityRecommendations = mActivityRecommendations;
    }

    public String[] getActivityTimes() {
        return mActivityTimes;
    }

    public void setActivityTimes(String[] mActivityTimes) {
        this.mActivityTimes = mActivityTimes;
    }

}