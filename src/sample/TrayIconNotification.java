package sample;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class TrayIconNotification {

    private static boolean isSupported;

    private static TrayIconNotification sTrayIconNotification;

    public static TrayIconNotification get() throws AWTException {
        if (sTrayIconNotification != null || compatibilityCheck()) {
            sTrayIconNotification = new TrayIconNotification();
        }
        return sTrayIconNotification;
    }

    private static boolean compatibilityCheck() throws AWTException {
        isSupported = false;

        if (SystemTray.isSupported()) {
            isSupported = true;
        }

        return isSupported;
    }

    public void displayTray(Activity currentActivity) throws AWTException {

        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "Activity");
        trayIcon.setImageAutoSize(true);

        trayIcon.setToolTip("System Tray Icon");

        tray.add(trayIcon);
        trayIcon.displayMessage("Activity", currentActivity.activityName, MessageType.INFO);
    }

}
