package Game;

import java.io.Serializable;
import java.util.TimerTask;

/**
 * Ez az osztály a TimerTask osztályból öröklődik és implementálja a szerializálható interfacet.
 * Azért öröklődik, hogy egy timernek átadhatók legyenek az egyes objektumok, ütemezetten.
 * A szerializálható interfacet a mentés miatt implementálja.
 */
public class UpdateTask extends TimerTask implements Serializable {
    /**
     * Game objektum.
     */
    private Game game;
    /**
     * ElementInfo objektum,ami az adott idjű elemhez tartozik.
     */
    private Elementinfo elementinfo;
    /**
     * Adott elem idje.
     */
    private int elementId;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * @param g Game típusú objektum.
     * @param einfo ElementInfo objektum.
     * @param eI Az adott elem id-je.
     */
    public UpdateTask(Game g,Elementinfo einfo,int eI){
        game=g;
        elementinfo=einfo;
        elementId=eI;
    }

    /**
     * Ez a függvény akkor kerül meghívásra, amikor a timer éppen az ütemezett task-ot hívja.
     * A függvény megnézi, hogy az adott elem frissíthető-e valamire.
     * Ha igen, akkor végignézi az összes elemet és a megfelelőket frissíti.
     * Ha nem, akkor az erőforrásokat frissíti.
     */
    @Override
    public void run() {
        synchronized (game){
            if(elementinfo.getCanBeModifiedTo()!=0){
                for(int i=0;i<100;i++){
                    for (int j=0;j<200;j++){
                        if(game.getMap_Element_id(i,j)==elementId){
                            game.setMap_Element_id(i,j,elementinfo.getCanBeModifiedTo());
                            game.setMapIdsUpdated(true);
                        }
                    }
                }
            }
            else{
                for(int i=0;i<100;i++){
                    for (int j=0;j<200;j++){
                        if(game.getMap_Element_id(i,j)==elementId){
                           for(int k=0;k<4;k++){
                               game.getResources()[k]+=elementinfo.getValue().get(k);
                           }
                           game.setResorcesUpdated(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter az updateTime-hoz
     * @return Adott elem frissítési ideje.
     */
    public long getUpdateTime(){
        return elementinfo.getUpdateTime();
    }
}
