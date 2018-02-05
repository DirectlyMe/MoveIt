package moveIt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

public class GlobalMouseListener implements Runnable {

    private boolean run = false;
    private boolean userActive = true;
    private String USERACTIVITY = "userActive";

    private PropertyChangeListener listener;
    private static Timer activityTimer;

    //listens for user activity and changes userActive to true if movement is detected.
    public void run() {

        run = true;

        System.out.println("Mouse listener running");

        noActivityTimer();

        GlobalMouseHook mouseHook = new GlobalMouseHook();

        mouseHook.addMouseListener(new GlobalMouseAdapter() {
            @Override public void mousePressed(GlobalMouseEvent event)  {
                changeUserActive(true);
            }
            @Override public void mouseReleased(GlobalMouseEvent event)  {
                changeUserActive(true); }
            @Override public void mouseMoved(GlobalMouseEvent event) {
                changeUserActive(true); }
            @Override public void mouseWheel(GlobalMouseEvent event) {
                changeUserActive(true); }
        });

        try {
            while(run) Thread.sleep(128);
        } catch(InterruptedException e) { /* nothing to do here */ }
        finally { mouseHook.shutdownHook();
            System.out.println("MouseListener stopped");}
    }

    /*
    timer changes user activity to false after 5 minutes, this will be changed back very quickly if user
    is still active.
    */
    private void noActivityTimer() {
        System.out.println("noActivityTimer started");
        activityTimer = new Timer();

        activityTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                changeUserActive(false);
            }
        }, 10000, 300000);
    }

    //accepts a boolean that determines user activity then passes it to notifyListeners method
    private void changeUserActive(boolean activity) {
        notifyListeners(this,
                USERACTIVITY,
                this.userActive,
                this.userActive = activity);
    }

    //reports changes to listener
    private void notifyListeners(Object object, String property, boolean oldValue, boolean newValue) {
        listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
    }

    //accepts a listener to report changes to
    public void addChangeListener(PropertyChangeListener newListener) {
        listener = newListener;
    }
}