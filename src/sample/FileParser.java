package sample;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileParser {

    private Activity[] mUserActivities;
    private String userSettingsFilePath;
    private PrintWriter mPrintWriter;
    private BufferedReader mBufferReader;
    private File mFile;
    private UserSettings settings;

    private boolean fileExists;


    private void init() {
        userSettingsFilePath = "./userSettings.txt";
        mFile = new File(userSettingsFilePath);
        fileExists = false;
    }

    /*Checks if a settings file exists, if it does the file is
    read and the textfields are populated.
     */
    public FileParser() throws IOException{
        init();
    }

    /* Print the activities to a file for permanence */
    public void writeSettingsFile(UserSettings userSettings) throws IOException {

        mUserActivities = userSettings.getActivities();

        mPrintWriter = new PrintWriter(userSettingsFilePath);

        for (int i = 0; i < mUserActivities.length; i++) {
            mPrintWriter.println("Activity " + mUserActivities[i].activityName);
            System.out.println("Activity " + mUserActivities[i].activityName);
            mPrintWriter.println("StartTime " + mUserActivities[i].startTime);
            System.out.println("StartTime " + mUserActivities[i].startTime);
            mPrintWriter.println("EndTime " + mUserActivities[i].endTime);
        }

        mPrintWriter.close();
    }

    /*Reads the settings file and populates the UserSettings object with
    the activities and time ranges contained in the file.
     */
    public void readSettingsFile(boolean fileExistence, UserSettings userSettings) throws IOException {

        if (fileExistence) {

            List<Activity> readActivities = new ArrayList<>();
            String fileRead;
            String[] tokenize;

            try {
                mBufferReader = new BufferedReader(new FileReader(userSettingsFilePath));

                fileRead = mBufferReader.readLine();

                while (fileRead != null) {

                    StringBuilder name;
                    String nameStr = "";
                    double start = 0;
                    double end = 0;
                    Activity activity;

                    tokenize = fileRead.split(" ");

                    if (tokenize[0].equals("Activity")) {

                        name = new StringBuilder();
                        for (int i = 1; i < tokenize.length; i++) {
                            name.append(tokenize[i] + " ");
                        }
                        nameStr = name.toString().trim();

                        fileRead = mBufferReader.readLine();
                        tokenize = fileRead.split(" ");

                        if (tokenize[0].equals("StartTime")) {
                            start = Double.parseDouble(tokenize[1]);
                            System.out.println(start);
                            fileRead = mBufferReader.readLine();
                            tokenize = fileRead.split(" ");
                        }
                        if (tokenize[0].equals("EndTime")) {
                            end = Double.parseDouble(tokenize[1]);
                        }
                    }

                    activity = new Activity(nameStr, start, end);
                    System.out.println(activity.activityName);
                    System.out.println(activity.startTime);
                    System.out.println(activity.endTime);

                    readActivities.add(activity);

                    fileRead = mBufferReader.readLine();
                }

                Activity[] activities = readActivities.toArray(new Activity[readActivities.size()]);

                userSettings.setActivities(activities);

                mBufferReader.close();

                //setUserSettings(activityList.toArray(), timeList.toArray());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    /*
    Checks for the existence of a file. The return value is a
    prerequisite for the readSettingsFile method.
     */
    public boolean isFileExists() {
        if (mFile.exists() && !mFile.isDirectory()) {
            fileExists = true;
        }
        else {
            fileExists = false;
        }
        return  fileExists;
    }

}
