package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;


public class Controller {



    private UserSettings settings;
    private FileParser parser;

    public Button mButtonSave;
    public TextField mTextFieldUserActivity1;
    public TextField mTextFieldUserActivity2;
    public TextField mTextFieldUserActivity3;
    public TextField mTextFieldActivityStartTime1;
    public TextField mTextFieldActivityStartTime2;
    public TextField mTextFieldActivityStartTime3;
    public TextField mTextFieldActivityEndTime1;
    public TextField mTextFieldActivityEndTime2;
    public TextField mTextFieldActivityEndTime3;

    private Activity[] mActivities;
    private TextField[] mActivityTextFields;
    private TextField[] mTimeStartTextFields;
    private TextField[] mTimeEndTextFields;


    @FXML
    public void initialize() {
        init();
    }


    private void init() {
        mActivityTextFields = new TextField[3];
        mTimeStartTextFields = new TextField[3];
        mTimeEndTextFields = new TextField[3];

        mActivityTextFields[0] = mTextFieldUserActivity1;
        mActivityTextFields[1] = mTextFieldUserActivity2;
        mActivityTextFields[2] = mTextFieldUserActivity3;

        mTimeStartTextFields[0] = mTextFieldActivityStartTime1;
        mTimeStartTextFields[1] = mTextFieldActivityStartTime2;
        mTimeStartTextFields[2] = mTextFieldActivityStartTime3;

        mTimeEndTextFields[0] = mTextFieldActivityEndTime1;
        mTimeEndTextFields[1] = mTextFieldActivityEndTime2;
        mTimeEndTextFields[2] = mTextFieldActivityEndTime3;

        settings = new UserSettings();
        setUpUserSettings(settings);
    }

    /* If a settings file exists this will read it and fill the settings panel with the current info*/
    public void setUpUserSettings(UserSettings settings) {

        try {
            parser = new FileParser();

            if (parser.isFileExists()) {
                parser.readSettingsFile(parser.isFileExists(), settings);
                mActivities = settings.getActivities();

                for (int i = 0; i < mActivities.length; i++) {
                    mActivityTextFields[i].setText(mActivities[i].activityName);
                    mTimeStartTextFields[i].setText(Double.toString(mActivities[i].startTime));
                    mTimeEndTextFields[i].setText(Double.toString(mActivities[i].endTime));
                }
            }
        }
        catch(IOException ex) {
            ex.getStackTrace();
        }

    }

    /*
        Takes info from the textfields in the settings panel.
        Saves them to a UserSettings object and then uses FileParser
        to save them to a file.
     */
    public void saveUserSettings(ActionEvent actionEvent) {

        mActivities = new Activity[3];

        for (int i = 0; i < 3; i++) {
            Activity activity = new Activity(mActivityTextFields[i].getText(),
                    Double.parseDouble(mTimeStartTextFields[i].getText()),
                    Double.parseDouble(mTimeEndTextFields[i].getText()));
            System.out.println(activity.activityName);
            mActivities[i] = activity;
        }

        settings.setActivities(mActivities);

        try {
            parser.writeSettingsFile(settings);
        }
        catch(IOException x) {
            System.out.println("File creation failed.");
        }
    }
}
