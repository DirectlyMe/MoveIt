package sample;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

/*
Creates a system notification that displays a activity to the user
 */
public class TrayIconNotification {

    private static boolean isSupported;

    private static TrayIconNotification sTrayIconNotification;

    //returns a single instance of this object
    public static TrayIconNotification get() throws AWTException {
        if (sTrayIconNotification != null || compatibilityCheck()) {
            sTrayIconNotification = new TrayIconNotification();
        }
        return sTrayIconNotification;
    }

    //checks for OS compatibility
    private static boolean compatibilityCheck() throws AWTException {
        isSupported = false;

        if (SystemTray.isSupported()) {
            isSupported = true;
        }

        return isSupported;
    }

    //displays system notification
    public void displayTray(Activity currentActivity) throws AWTException {

        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "Activity");
        trayIcon.setImageAutoSize(true);

        trayIcon.setToolTip("System Tray Icon");

        tray.add(trayIcon);
        trayIcon.displayMessage("Move It!", currentActivity.activityName, MessageType.INFO);
    }

}
