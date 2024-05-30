package Image;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ez az osztály valósítja meg a kirajzolható elemek kirajzolását.
 * Az osztály a Jlabel osztályból öröklődik és a megfelelő függvényeit írja felül.
 */
public class LayeredIcon extends JLabel{
    /**
     * Hashmap, ami tartalmazza az elemekhez tartozó ImageIcon-okat.
     */
    private HashMap<Integer,ArrayList<ImageIcon>> icons;
    /**
     * Zoom mértéke.
     */
    private double zoom;
    /**
     * ImageIcon select-hez.
     */
    private ImageIcon selectIcon;
    /**
     * Változó, hogy az adott elem ki van-e jelölve.
     */
    private boolean selected;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * @param i ImageFactory DoneImages-ei.(kirajzolható elemek)
     * @param sel Select ImageIcon-ja
     * @param z Zoom
     */
    public LayeredIcon(HashMap<Integer,ArrayList<ImageIcon>> i,ImageIcon sel,double z){
        icons=i;
        zoom=z;
        selected=false;
        selectIcon=sel;
    }

    /**
     * Ez a függvény végzi az adott elem kirajzolását.
     * Kiválasztja a megfelelő ImageIconokat, amik az adott elemhez tartoznak.
     * Át méretezi az ImageIconok képeit a zoomnak megfelelően,
     * majd kirajzolja az elemet.
     * Ha az elem éppen ki van jelölve, akkor a select Icon-t is kirajzolja.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<ImageIcon> iconsToShow=icons.get(Integer.parseInt(this.getName()));
        for (ImageIcon icon:iconsToShow) {
            g.drawImage(icon.getImage().getScaledInstance((int)(16*(1/zoom)),(int)(16*(1/zoom)),Image.SCALE_SMOOTH), 0, 0, (img, infoflags, x, y, width, height) -> false);
        }
        if(selected){
            g.drawImage(selectIcon.getImage().getScaledInstance((int) (16 * (1 / zoom)), (int) (16 * (1 / zoom)), Image.SCALE_SMOOTH), 0, 0, (img, infoflags, x, y, width, height) -> false);
        }
    }

    /**
     * Setter a selected-hez.
     * @param selected Erre állítjuk a selected étékét.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Teszteléshez getter
     * @return Selected-e
     */
    public boolean isSelected() {
        return selected;
    }
}
