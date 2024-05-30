package Input.MouseListeners;

import Render.Map.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ez az osztály a MouseAdapter osztályból öröklődik.
 * A játékfolyamat során az gombjait kezeli.
 */
public class MouseListenerForMap extends MouseAdapter {
    /**
     * Map objektum, ahonnan az event jöhet.
     */
    private Map map;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * @param m Map objektum.
     */
    public MouseListenerForMap(Map m){
        map=m;
    }

    /**
     * Ez a függvény kezeli a kattintásokat a játékfolyamat során.
     * Button1: A bal kattintást kezeli, meghívja a megfelelő függvényeket, hogy a kattintás helyén lévő elem kijelölésre kerüljön.
     * Button2: A jobb kattintást kezeli, meghívja a megfelelő függvényt, a kijelölés törlésére.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()){
            case MouseEvent.BUTTON1:{
                if(map.getSelectedElementOSC()[0]!=999 && map.getSelectedElementOSC()[1]!=999){
                    map.removeSelect();
                }
                map.getSelectedElementOSC()[0]=(int)(e.getLocationOnScreen().x/(16*(1/map.getZoom())));
                map.getSelectedElementOSC()[1]=(int)(e.getLocationOnScreen().y/(16*(1/map.getZoom())));
                map.select();
                break;
            }
            case MouseEvent.BUTTON3:{
                if(map.getSelectedElementOSC()[0]!=999 && map.getSelectedElementOSC()[1]!=999){
                    map.removeSelect();
                }
                break;
            }
        }
    }
}
