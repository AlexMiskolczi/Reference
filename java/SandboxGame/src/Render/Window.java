package Render;

import javax.swing.*;
import java.awt.*;

/**
 * Ez az osztály a megjelenítéshez szükséges framet tartalmaza.
 */
public class Window {
    /**
     * Frame, amin megjelenítünk.
     */
    private final JFrame frame;

    /**
     * Konstruktor, ami inicializálja a változókat.
     */
    public Window(){
        frame=new JFrame();
        frame.setSize(1920,1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Getter a frame-hez
     * @return A frame, amin megjelenítünk.
     */
    public JFrame getFrame() {
        return frame;
    }
}
