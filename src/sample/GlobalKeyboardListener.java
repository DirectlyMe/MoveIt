package sample;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.Map;
import java.util.Observable;

public class GlobalKeyboardListener implements Runnable {


    private boolean run = true;
    public boolean userActive = false;

    public void run() {
        // might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input

        System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");
        for(Map.Entry<Long,String> keyboard:GlobalKeyboardHook.listKeyboards().entrySet())
            System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed(GlobalKeyEvent event) {
                System.out.println(event);
                setUserActive(true);
                run = false;
                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE)
                    run = false;
            }
            @Override public void keyReleased(GlobalKeyEvent event) {
                System.out.println(event); }
        });

        try {
            while(run) Thread.sleep(128);
        } catch(InterruptedException e) { /* nothing to do here */ }
        finally {
            keyboardHook.shutdownHook(); }
    }

    public void setUserActive(boolean run) {
        userActive = run;
    }

    public boolean getUserActive() {
        return userActive;
    }
}
