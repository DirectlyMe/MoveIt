package moveIt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private Activity[] mUserActivities;
    private String userSettingsFilePath;
    private PrintWriter mPrintWriter;
    private BufferedReader mBufferReader;
    private File mFile;

    private boolean fileExists;



    /*
    Sets up user settings file.
     */
    public FileParser() {
        userSettingsFilePath = "./userSettings.txt";
        mFile = new File(userSettingsFilePath);
        fileExists = false;
    }

    /* Print the activities to a file for permanence */
    public void writeSettingsFile(UserSettings userSettings) throws IOException {

        mUserActivities = userSettings.getActivities();

        mPrintWriter = new PrintWriter(userSettingsFilePath);

        for (Activity activity : mUserActivities) {
            mPrintWriter.println("Activity " + activity.getActivityName());
            mPrintWriter.println("StartTime " + activity.getStartTime());
            mPrintWriter.println("EndTime " + activity.getEndTime());
        }

        mPrintWriter.close();

    }

    /*
    Reads the settings file and populates the UserSettings object with
    the activities names and time ranges contained in the file.
     */
    public void readSettingsFile(boolean fileExistence, UserSettings userSettings) throws IOException {

        if (fileExistence) {

            List<Activity> readActivities = new ArrayList();
            String fileRead;
            String[] tokenize;

            try {
                mBufferReader = new BufferedReader(new FileReader(userSettingsFilePath));

                fileRead = mBufferReader.readLine();

                while (fileRead != null) {

                    StringBuilder name;
                    String nameStr = "";
                    int start = 0;
                    int end = 0;
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
                            start = Integer.parseInt(tokenize[1]);
                            System.out.println(start);
                            fileRead = mBufferReader.readLine();
                            tokenize = fileRead.split(" ");
                        }
                        if (tokenize[0].equals("EndTime")) {
                            end = Integer.parseInt(tokenize[1]);
                        }
                    }

                    activity = new Activity(nameStr, start, end);

                    readActivities.add(activity);

                    fileRead = mBufferReader.readLine();
                }

                Activity[] activities = readActivities.toArray(new Activity[readActivities.size()]);

                userSettings.setActivities(activities);

                mBufferReader.close();

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
