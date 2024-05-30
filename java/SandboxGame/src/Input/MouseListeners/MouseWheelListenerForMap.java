package Input.MouseListeners;

import Render.Map.Map;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Ez az osztály implementálja a MouseWheelListener inteface-t.
 * A játékfolyamat során az egér görgőjét kezeli.
 */
public class MouseWheelListenerForMap implements MouseWheelListener {
    /**
     * Map objektum, ahonnan az eventek jöhetnek.
     */
    private Map map;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * @param m Map objektum
     */
    public MouseWheelListenerForMap(Map m){
        map=m;
    }

    /**
     * Ez a függvény kezeli az egér görgőjét.
     * Lefelé görgetés: Meghívja a megfelelő függvényeket, hogy a zoomot növelje.
     * felfelé görgetés: Meghívja a megfelelő függvényeket, hogy a zoomot csökkentse.
     * @param e the event to be processed
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation()==(-1) &&map.getZoom()>0.25){
            if(map.getSelectedElementOSC()[0]!=999 && map.getSelectedElementOSC()[1]!=999){
                map.removeSelect();
            }
            map.setZoom(map.getZoom()/2);
            map.Buildmap();
            map.getPanel().revalidate();
            map.getPanel().repaint();
        }
        if(e.getWheelRotation()==1 && map.getZoom()<1){
            if(map.getSelectedElementOSC()[0]!=999 && map.getSelectedElementOSC()[1]!=999){
                map.removeSelect();
            }
            map.setZoom(map.getZoom()*2);
            map.Buildmap();
            map.getPanel().revalidate();
            map.getPanel().repaint();
        }
    }
}
