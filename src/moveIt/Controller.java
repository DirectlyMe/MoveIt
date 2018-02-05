package moveIt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public TableView<Activity> table_view_activities;
    public TableColumn name_table_col;
    public TableColumn start_time_table_col;
    public TableColumn end_time_table_col;
    public Button start_button;
    public MenuItem close_menu_item;
    private ToggleGroup toggleGroup;
    public RadioMenuItem frequency_30min;
    public RadioMenuItem frequency_1hour;
    public RadioMenuItem frequency_2hours;

    private UserSettings settings;
    private FileParser parser;
    private Thread backThread;

    private Activity[] mActivities;
    private ObservableList<Activity> tableData;


    /*sets up GUI elements*/
    @FXML
    public void initialize() {

        activity_name_text_box.setPromptText("Activity Name");
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
        start_button.setOnAction(this::startBackgroundProcess);

        close_menu_item.setOnAction((event -> {
            System.exit(0);
        }));

        toggleGroup = new ToggleGroup();
        frequency_30min.setToggleGroup(toggleGroup);
        frequency_1hour.setToggleGroup(toggleGroup);
        frequency_1hour.setSelected(true);
        frequency_2hours.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(toggleGroup.getSelectedToggle() == frequency_30min) {
                    setNotificationFrequency(1300000);
                }
                else if (toggleGroup.getSelectedToggle() == frequency_1hour) {
                    setNotificationFrequency(3600000);
                }
                else {
                    setNotificationFrequency(7200000);
                }
            }
        });

        initObjects();
    }

    /* in charge of setting up data objects */
    private void initObjects() {
        tableData = FXCollections.observableArrayList();
        settings = new UserSettings();
        setUpUserSettings(settings);
    }

    /* If a settings file exists this will read it and fill the settings panel table with the current info*/
    private void setUpUserSettings(UserSettings settings) {

        try {
            parser = new FileParser();

            if (parser.isFileExists()) {
                parser.readSettingsFile(parser.isFileExists(), settings);
                mActivities = settings.getActivities();

                tableData.addAll(mActivities);

            }
            table_view_activities.setItems(tableData);
            table_view_activities.getColumns().addAll(name_table_col, start_time_table_col, end_time_table_col);
        }
        catch(IOException ex) {
            ex.getStackTrace();
        }

    }

    //handles add_button event
    private void addActivity(ActionEvent actionEvent) {
        tableData.add(new Activity(activity_name_text_box.getText(),
                (Integer) start_time_choice_box.getValue(),
                (Integer) end_time_choice_box.getValue()));
        activity_name_text_box.clear();
    }

    //handles remove_button event
    private void removeActivity(ActionEvent actionEvent) {
        Activity selectedItem = table_view_activities.getSelectionModel().getSelectedItem();
        table_view_activities.getItems().remove(selectedItem);
    }

    /*
        Takes info from the table in the settings panel.
        Saves them to a UserSettings object and then uses FileParser
        to save them to a file.
     */
    private void saveUserSettings(ActionEvent actionEvent) {

        mActivities = new Activity[tableData.size()];

        for(int i = 0; i < mActivities.length; i++) {
            mActivities[i] = new Activity(tableData.get(i).getActivityName(),
                    tableData.get(i).getStartTime(), tableData.get(i).getEndTime());
        }

        settings.setActivities(mActivities);

        try {
            parser.writeSettingsFile(settings);
        }
        catch(IOException x) {
            System.out.println("File creation failed.");
        }
    }

    /*
    Starts thread that is in charge of showing notifications, starting timers and
    the GlobalMouseListener.
    */
    private void startBackgroundProcess(ActionEvent action) {
        BackgroundProcess backgroundProcess = new BackgroundProcess();
        backThread = new Thread(backgroundProcess);
        backThread.run();
    }

    //sets the notification message display frequency
    private void setNotificationFrequency(int frequency) {
        UserSettings.setNotification_frequency(frequency);
    }
}
