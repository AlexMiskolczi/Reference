package Game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ez az osztály Az egyes pályaelemek frissítéséhez szükséges információkat tárolja.
 */
public class Elementinfo implements Serializable {
    /**
     * Frissítési idő
     */
    private long updateTime;
    /**
     * Az elem idje, amire módosítható az elem.
     */
    private int canBeModifiedTo;
    /**
     * Betakarítható-e az elem.
     */
    private boolean harvestAble;
    /**
     * Egy arrayList,ami az elem árát tartalmazza(erőforrások).
     */
    private ArrayList<Integer> price;
    /**
     * Egy arrayList,ami az elem értékét tartalmazza(erőforrások).
     */
    private ArrayList<Integer> value;
    /**
     * Építhető-e az elem.
     */
    private boolean buildAble;

    /**
     * Konstruktor, ami inicializálja a változókat.
      * @param ut Mennyi időnként frissítjük az elemet.
     * @param mod Mire frissítjük az elemet(0, ha csak erőforrást ad)
     * @param h Betakarítható-e.
     * @param p Ár(Ennyiért lehet felépíteni)
     * @param v Érték(Ennyit ér eladásnál/betakarításnál)
     * @param b Megépíthető-e.
     */
    public Elementinfo(long ut,int mod,boolean h,ArrayList<Integer> p,ArrayList<Integer> v,boolean b){
        updateTime=ut;
        canBeModifiedTo=mod;
        harvestAble=h;
        price=p;
        value=v;
        buildAble=b;
    }

    /**
     * Getter az updateTime-hoz
     * @return Menni időnként frissítjük az adott elemet.
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Getter a canBeModifiedTo-hoz.
     * @return Annak az elemnek az id-je, amire frissül ez az elem.
     */
    public int getCanBeModifiedTo() {
        return canBeModifiedTo;
    }

    /**
     * Getter a Harvestable-hez.
     * @return Betakarítható-e a mező.
     */
    public boolean getHarvestAble() {
        return harvestAble;
    }

    /**
     * Getter a price-hoz.
     * @return Egy lista erőforrásokból(arany,fa,kő,étel).
     */
    public ArrayList<Integer> getPrice() {
        return price;
    }

    /**
     * Getter a buildable-höz.
     * @return Megépíthető-e az elem.
     */
    public boolean isBuildAble() {
        return buildAble;
    }
    /**
     * Getter a value-hoz.
     * @return Egy lista erőforrásokból(arany,fa,kő,étel).
     */
    public ArrayList<Integer> getValue() {
        return value;
    }
}
