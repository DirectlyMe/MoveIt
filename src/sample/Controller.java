package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class Controller {


    public Button save_button;
    public TextField activity_name_text_box;
    public ChoiceBox start_time_choice_box;
    public ChoiceBox end_time_choice_box;
    public Button add_button;
    public Button remove_button;
    public TableView<Activity> mActivityTableView;
    public TableColumn name_table_col;
    public TableColumn start_time_table_col;
    public TableColumn end_time_table_col;
    private UserSettings settings;
    private FileParser parser;

    private Activity[] mActivities;
    private ObservableList<Activity> tableData;


    /*sets up GUI elements*/
    @FXML
    public void initialize() {

        start_time_choice_box.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
        17, 18, 19, 20, 21, 22, 23, 24);
        end_time_choice_box.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24);
        name_table_col.setCellValueFactory(new PropertyValueFactory<Activity, String>("activityName"));
        start_time_table_col.setCellValueFactory(new PropertyValueFactory<Activity, Integer>("startTime"));
        end_time_table_col.setCellValueFactory(new PropertyValueFactory<Activity, Integer>("endTime"));

        save_button.setOnAction(this::saveUserSettings);
        add_button.setOnAction(this::addActivity);
        remove_button.setOnAction(this::removeActivity);

        init();
    }

    /* in charge of setting up data objects */
    private void init() {
        tableData = FXCollections.observableArrayList();
        settings = new UserSettings();
        setUpUserSettings(settings);
    }

    /* If a settings file exists this will read it and fill the settings panel table with the current info*/
    public void setUpUserSettings(UserSettings settings) {

        try {
            parser = new FileParser();

            if (parser.isFileExists()) {
                parser.readSettingsFile(parser.isFileExists(), settings);
                mActivities = settings.getActivities();

                tableData.addAll(mActivities);
                mActivityTableView.setItems(tableData);
                mActivityTableView.getColumns().addAll(name_table_col, start_time_table_col, end_time_table_col);
            }
        }
        catch(IOException ex) {
            ex.getStackTrace();
        }

    }

    //handles add_button event
    public void addActivity(ActionEvent actionEvent) {
        tableData.add(new Activity(activity_name_text_box.getText(),
                (Integer) start_time_choice_box.getValue(),
                (Integer) end_time_choice_box.getValue()));
    }

    //handles remove_button event
    public void removeActivity(ActionEvent actionEvent) {
        Activity selectedItem = mActivityTableView.getSelectionModel().getSelectedItem();
        mActivityTableView.getItems().remove(selectedItem);
    }

    /*
        Takes info from the table in the settings panel.
        Saves them to a UserSettings object and then uses FileParser
        to save them to a file.
     */
    public void saveUserSettings(ActionEvent actionEvent) {

        mActivities = new Activity[tableData.size()];

        for(int i = 0; i < mActivities.length; i++) {
            mActivities[i] = new Activity(tableData.get(i).getActivityName(),
                    tableData.get(i).getStartTime(), tableData.get(i).getEndTime());
        }

        for (Activity activity : mActivities) {
            System.out.println(activity.getActivityName() + " " + activity.getStartTime()
            + " " + activity.getEndTime());
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
