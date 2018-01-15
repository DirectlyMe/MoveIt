package sample;

import java.io.*;
import java.util.Scanner;

public class FileParser {

    private String[] mActivityRecommendations;
    private String[] mActivityTimes;
    private String userSettingsFilePath;
    private PrintWriter mPrintWriter;
    private Scanner mInputFile;
    private File mFile;
    private UserSettings settings;


    public FileParser() throws IOException{
        readSettingsFile();
        setUserSettings();
    }

    public FileParser(UserSettings settings) {

        this.settings = settings;

        mActivityRecommendations = settings.getActivityRecommendations();
        mActivityTimes = settings.getActivityTimes();

        userSettingsFilePath = "./userSettings.txt";
        mFile = new File(userSettingsFilePath);

    }

    /* Print the activities to a file for permanence */
    public void writeSettingsFile() throws IOException {

        mPrintWriter = new PrintWriter(userSettingsFilePath);

        for (int i = 0; i < mActivityRecommendations.length; i++) {
            mPrintWriter.println("Activity " + mActivityRecommendations[i]);
            System.out.println("Activity " + mActivityRecommendations[i]);
            mPrintWriter.println("Time " + mActivityTimes[i]);
            System.out.println("Time " + mActivityTimes[i]);
        }

        mPrintWriter.close();
    }

    public void readSettingsFile() throws IOException {

        mInputFile = new Scanner(mFile);
        int i = 0;

        if (!mFile.exists()) {
            return;
        }

        while (mInputFile.hasNextLine()) {

            if (mInputFile.next().equals("Activity")) {
                mActivityRecommendations[i] = mInputFile.next();
                System.out.println(mActivityRecommendations[i]);
            }
            if (mInputFile.next().equals("Time")) {
                mActivityTimes[i] = mInputFile.next();
                System.out.println(mActivityTimes[i]);
            }

            i++;
        }


    }

    //populates userSettings object
    private void setUserSettings() {
        settings.setActivityRecommendations(mActivityRecommendations);
        settings.setActivityTimes(mActivityTimes);
    }
}
