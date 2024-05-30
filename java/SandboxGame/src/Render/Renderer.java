package Render;
import Game.Game;
import Input.ActionListeners.ActionListenerForInGameMenu;
import Input.ActionListeners.ActionListenerForMenu;
import Render.Map.Map;
import Render.Menu.*;
import Render.Menu.Menu;
import Image.*;

import java.util.Timer;

/**
 * Ez az osztály a thread osztályból öröklődik.
 * Ez az osztály fogja össze a megjeleníthető objektumokat és jeleníti meg azokat.
 */
public class Renderer extends Thread {
    /**
     * Egy Window objektum, aminek a frame-jén megjelenítünk.
     */
    private Window window;
    /**
     * A megjeleníthető menüket tartalmazó tömb.
     */
    private Menu[] menus;
    /**
     * Game objektum, ami alapján megjelenítünk.
     */
    private Game game;
    /**
     * A megjeleníthető pályát tartalmazó objektum.
     */
    private Map map;
    /**
     * Az updateTask-okat ütemező timer(Csak a leállításhoz szükséges).
     */
    private Timer timer;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * @param g Game objektum, ami alapján megjelenítünk.
     * @param t Timer ami az updateTask-okat ütemezi.
     */
    public Renderer(Game g, Timer t){
        game=g;
        timer=t;
    }

    /**
     * Inicializáló függvény, ami létrehoz:
     * -menüket
     * -pálya megjelenítőt
     * Ezen felül felépíti a framet induláskor.
     */
    public void init(){
        window=new Window();
        menus=new Menu[6];
        map=new Map(window.getFrame(),game,menus);
        IconFactory iconFactory=new IconFactory();
        String[] iconkey={"Start","Load","Exit"};
        String[] iconkey2={"Continue","Save","Exit"};
        String[] iconKeysForInGameMenu={"Delete","Harvest","Build","Move"};
        String[] iconKeysForInGameMenuLabels={"Gold","Wood","Stone","Food"};
        ActionListenerForMenu afm=new ActionListenerForMenu(window.getFrame(),menus,map);
        ActionListenerForInGameMenu actionListenerForInGameMenu=new ActionListenerForInGameMenu(map,game,menus,window.getFrame());
        menus[0]=new NormalMenu(3,window.getFrame(),afm,iconFactory.getIcons(),iconkey);
        menus[1]=new NormalMenu(3,window.getFrame(),afm,iconFactory.getIcons(),iconkey2);
        menus[2]=new InGameMenu(iconFactory.getIcons(),iconKeysForInGameMenu,iconKeysForInGameMenuLabels,game,actionListenerForInGameMenu);
        menus[3]=new SaveMenu(afm);
        menus[4]=new LoadMenu(afm);
        menus[5]=new BuildMenu(game,new ImageFactory().getDoneImages(),iconFactory.getIcons(),iconKeysForInGameMenuLabels,actionListenerForInGameMenu);
        window.getFrame().add(menus[0].getPanel());
        window.getFrame().pack();
        window.getFrame().setVisible(true);
        game.setMapIdsUpdated(true);
    }

    /**
     * Ez a függvény meghívja azokat a függvényeket,amik
     * frissítik a megjelenithető objektumokat, amíg a játék fut.
     */
    @Override
    public void run(){
        init();
        while(window.getFrame().isDisplayable()){
            if(map.isShown()){
                map.update();
                if(game.isResorcesUpdated()){
                    ((InGameMenu)menus[2]).update();
                    ((BuildMenu)menus[5]).update();
                    game.setResorcesUpdated(false);
                }
            }
        }
        timer.cancel();
    }
}
