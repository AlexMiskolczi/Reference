package Input.KeyListeners;

import Render.Map.Map;
import Render.Menu.Menu;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * Ez az osztály a KeyAdapter osztályból öröklődik.
 * A játékfolyamat során a billentyüket kezeli.
 */
public class KeyListenerForMap extends KeyAdapter {
    /**
     * A frame, amire rajzolunk.
     */
    private JFrame frame;
    /**
     * Map objektum, ahonnan az eventek érkeznek.
     */
    private Map map;
    /**
     * Menu objektumok, a megjeleníthető menük.
     */
    private Menu[] menus;

    /**
     * Konstruktor,inicializálja az adatokat.
     * @param f A frame, amire rajzolunk.
     * @param m Map objektum
     * @param me Menu objektumok.
     */
    public KeyListenerForMap(JFrame f,Map m,Menu[] me){
        menus=me;
        frame=f;
        map=m;
    }

    /**
     * Ez a függvény a lenyomott billentyűk alapján hívja meg a megfelelő függvényeket.
     * VK_ESCPAE: Meghívja a megfelelő függvényeket, az exit menü aktiválásához.
     * VK_W: Meghívja a megfelelő függvényeket, hogy egyel feljebb lépjünk a pályán.
     * VK_S: Meghívja a megfelelő függvényeket, hogy egyel lejjebb lépjünk a pályán.
     * VK_A: Meghívja a megfelelő függvényeket, hogy egyel balra lépjünk a pályán.
     * VK_D: Meghívja a megfelelő függvényeket, hogy egyel jobbra lépjünk a pályán.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            map.setShown(false);
            map.getPanel().setVisible(false);
            frame.remove(map.getPanel());
            if(menus[2].getPanel().isVisible()){
                menus[2].getPanel().setVisible(false);
                frame.remove(menus[2].getPanel());
            }
            if(menus[5].getPanel().isVisible()){
                menus[5].getPanel().setVisible(false);
                frame.remove(menus[5].getPanel());
            }
            menus[1].getPanel().setVisible(true);
            frame.add(menus[1].getPanel());
            frame.pack();
            frame.setVisible(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            if(map.getOffset()[1]+1+map.getZoom()*120<=200){
                map.getOffset()[1]+=1;
                map.setOffsetDirection(1);
                map.offsetUpdate();
                map.getPanel().revalidate();
                map.getPanel().repaint();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            if(map.getOffset()[1]>0){
                map.getOffset()[1]-=1;
                map.setOffsetDirection(2);
                map.offsetUpdate();
                map.getPanel().revalidate();
                map.getPanel().repaint();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            if(map.getOffset()[0]+1+map.getZoom()*60<=100){
                map.getOffset()[0]+=1;
                map.setOffsetDirection(3);
                map.offsetUpdate();
                map.getPanel().revalidate();
                map.getPanel().repaint();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_W){
            if(map.getOffset()[0]>0){
                map.getOffset()[0]-=1;
                map.setOffsetDirection(4);
                map.offsetUpdate();
                map.getPanel().revalidate();
                map.getPanel().repaint();
            }
        }
    }
}
