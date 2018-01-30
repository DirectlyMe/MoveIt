package sample;

import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

public class GlobalMouseListener implements Runnable {
    private static boolean run = true;
    private boolean userActive = false;

    public interface CallBack {
        void setActiveUser(boolean bool);
    }

    public void run() {
        // might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalMouseHook mouseHook = new GlobalMouseHook(); // add true to the constructor, to switch to raw input mode

        System.out.println("Global mouse hook successfully started, press [middle] mouse button to shutdown. Connected mice:");
        for(Entry<Long,String> mouse:GlobalMouseHook.listMice().entrySet())
            System.out.format("%d: %s\n", mouse.getKey(), mouse.getValue());

        mouseHook.addMouseListener(new GlobalMouseAdapter() {
            @Override public void mousePressed(GlobalMouseEvent event)  {
                System.out.println(event);
                if((event.getButtons()&GlobalMouseEvent.BUTTON_LEFT)!=GlobalMouseEvent.BUTTON_NO
                        && (event.getButtons()&GlobalMouseEvent.BUTTON_RIGHT)!=GlobalMouseEvent.BUTTON_NO)
                    System.out.println("Both mouse buttons are currenlty pressed!");
            }
            @Override public void mouseReleased(GlobalMouseEvent event)  {
                userActive = true; }
            @Override public void mouseMoved(GlobalMouseEvent event) {
                userActive = true; }
            @Override public void mouseWheel(GlobalMouseEvent event) {
                userActive = true; }
        });

        try {
            while(run) Thread.sleep(128);
        } catch(InterruptedException e) { /* nothing to do here */ }
        finally { mouseHook.shutdownHook(); }
    }

    private void timerMethod() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {

            }
        }, );
    }
}