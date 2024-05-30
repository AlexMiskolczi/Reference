package Input.ActionListeners;

import Game.Game;
import Render.Map.Map;
import Render.Menu.BuildMenu;
import Render.Menu.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ez az osztály implementálja az ActionListener interface-t.
 * A jaték közbeni menük action-jeit kezeli.
 */
public class ActionListenerForInGameMenu implements ActionListener {
    /**
     * Map objektum, ahonnan action jöhet.
     */
    private Map map;
    /**
     * Game objektum, ami a pálya informnációit tárolja.
     */
    private Game game;
    /**
     * Menu objektumok, a megjeleníthető menük.
     */
    private Menu[] menus;
    /**
     * A frame, amire rajzolunk.
     */
    private JFrame frame;

    /**
     * Konstruktor,ami inicializálja az adatokat.
     * @param m Map objektum
     * @param g Game objektum
     * @param me Menu objektumok
     * @param f A frame, amire rajzolunk.
     */
    public ActionListenerForInGameMenu(Map m,Game g,Menu[] me,JFrame f){
        map=m;
        game=g;
        menus=me;
        frame=f;
    }

    /**
     * Ez a függvény a kapott action command alapján kiválasztja a függvényeket, amik hívásra kerülnek.
     * Delete: Delete függvényt hívja.
     * Harvest: Harvest függvényt hívja.
     * Move: Move függvényt hívja.
     * Build: A BuildMenu-t aktiválja.
     * Back: Visszalép a BuildMenu-ből.
     * Left: A BuildMenu-ben a kirajzolt építhető elemeket egyel balra lépteti.
     * Right:A BuildMenu-ben a kirajzolt építhető elemeket egyel jobbra lépteti.
     * BuildSelected: A kijelölt elemet megépíti, hívja a Build függvényt.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Delete":{
                synchronized (game){
                    game.deleteElement();
                }
                break;
            }
            case "Harvest":{
                synchronized (game){
                    game.Harvest();
                }
                break;
            }
            case "Move":{
                synchronized (game){
                    game.setElementToBeMoved();
                }
                break;
            }
            case "Build":{
                menus[2].getPanel().setVisible(false);
                frame.remove(menus[2].getPanel());
                menus[5].getPanel().setVisible(true);
                frame.add(menus[5].getPanel());
                frame.pack();
                frame.setVisible(true);
                map.getPanel().requestFocus();
                break;
            }
            case "Back":{
                menus[5].getPanel().setVisible(false);
                frame.remove(menus[5].getPanel());
                menus[2].getPanel().setVisible(true);
                frame.add(menus[2].getPanel());
                frame.pack();
                frame.setVisible(true);
                map.getPanel().requestFocus();
                break;
            }
            case "Left":{
                BuildMenu buildMenu=(BuildMenu) menus[5];
                if(buildMenu.getBuildMenuCustomPanel().getIdxOfFirstElement()>0){
                    buildMenu.getBuildMenuCustomPanel().setIdxOfFirstElement(buildMenu.getBuildMenuCustomPanel().getIdxOfFirstElement()-1);
                }
                buildMenu.reconstructBuildPanel();
                break;
            }
            case "Right":{
                BuildMenu buildMenu=(BuildMenu) menus[5];
                if(buildMenu.getBuildMenuCustomPanel().getElementCount()>=buildMenu.getBuildMenuCustomPanel().getIdxOfFirstElement()+7){
                    buildMenu.getBuildMenuCustomPanel().setIdxOfFirstElement(buildMenu.getBuildMenuCustomPanel().getIdxOfFirstElement()+1);
                }
                buildMenu.reconstructBuildPanel();
                break;
            }
            case "BuildSelected":{
                synchronized (game){
                    game.build(Integer.parseInt(((JButton)e.getSource()).getName()));
                }
                break;
            }
        }
        map.getPanel().requestFocus();
    }
}
