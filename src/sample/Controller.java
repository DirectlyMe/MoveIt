package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public class Controller {

    private UserSettings settings;

    public TextField mTextFieldUserActivity1;
    public TextField mTextFieldUserActivity2;
    public Button mButtonSave;
    public TextField mTextFieldUserActivity3;
    public TextField mTextFieldActivityTime1;
    public TextField mTextFieldActivityTime2;
    public TextField mTextFieldActivityTime3;

    private String[] mActivities;
    private String[] mActivityTimes;
    private TextField[] mActivityTextFields;
    private TextField[] mTimeTextFields;


    public Controller() {

        init();

    }

    public void init() {
        mActivityTextFields = new TextField[3];
        mTimeTextFields = new TextField[3];
        mActivities = new String[3];
        mActivityTimes = new String[3];

        mActivityTextFields[0] = mTextFieldUserActivity1;
        mActivityTextFields[1] = mTextFieldUserActivity2;
        mActivityTextFields[2] = mTextFieldUserActivity3;

        mTimeTextFields[0] = mTextFieldActivityTime1;
        mTimeTextFields[1] = mTextFieldActivityTime2;
        mTimeTextFields[2] = mTextFieldActivityTime3;

        settings = new UserSettings();
    }

    /* If a settings file exists this will read it and fill the settings panel with the current info*/
    public void setUpUserObj(UserSettings settings) {

        int i = 0;

        try {
            FileParser parser = new FileParser();
        }
        catch(IOException ex) {
            ex.getStackTrace();
        }

        for (String activity : settings.getActivityRecommendations()) {

        }

    }

    /*
        Takes info from the textfields in the settings panel.
        Saves them to a UserSettings object and then uses FileParser
        to save them to a file.
     */
    public void saveUserSettings(ActionEvent actionEvent) {



        for (int i = 0; i < 3; i++) {
            mActivities[i] = mActivityTextFields[i].getText();
            mActivityTimes[i] = mTimeTextFields[i].getText();
        }

        settings.setActivityRecommendations(mActivities);
        settings.setActivityTimes(mActivityTimes);

        try {
            FileParser parser = new FileParser(settings);
            parser.writeSettingsFile();
            parser.readSettingsFile();
        }
        catch(IOException x) {
            System.out.println("File creation failed.");
        }
    }
}
