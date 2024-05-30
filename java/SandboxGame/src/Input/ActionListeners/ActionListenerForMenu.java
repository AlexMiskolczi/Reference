package Input.ActionListeners;

import Render.Map.Map;
import Render.Menu.LoadMenu;
import Render.Menu.Menu;
import Render.Menu.SaveMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Ez az osztály implementálja az ActionListener interfacet.
 * A játékfolyamaton kívüli menük action-jeit kezeli.
 */
public class ActionListenerForMenu implements ActionListener {
    /**
     * A frame, amire rajzolunk.
     */
    private JFrame frame;
    /**
     * Menus objektumok, a megjeleníthető menük, innen jöhetnek action-ök.
     */
    private Menu[] menus;
    /**
     * Map objektum, a megjeleníthető map.
     */
    private Map map;

    /**
     * Konstruktor, ami inicializálja az adatokat.
     * @param f A frame, amire rajzolunk.
     * @param me Menu objektumok.
     * @param ma Map objektum.
     */
    public ActionListenerForMenu(JFrame f, Menu[] me, Map ma){
        map=ma;
        frame=f;
        menus=me;
    }

    /**
     * Ez a függvény az action command-ok alapján hívja a megfelelő függvényeket.
     * Exit: Egy új eventet küld a framnek, hogy záródjon be.
     * Start: Meghívja a megfelelő függvényeket, hogy generáljon egy random map-ot és a játékfolyamatot elindítja.
     * Load: Meghívja a megfelelő függvényeket, hogy aktiválja a load menüt.
     * Continue: Meghívja a megfelelő függvényeket, hogy az exit menüből visszalépjen a játéfolyamatba.
     * Save: Meghívja a megfelelő függvényeket, hogy aktiválja a save menüt.
     * Save_as: Meghívja a megfelelő függvényeket, hogy a save menüben elmentsük a játékállást.
     * Back: Meghívja a megfelelő függvényeket, hogy visszalépjen egy menüvel(save/load menüből lép vissza).
     * Delete: Meghívja a megfelelő függvényeket, hogy a save menüben a kijelölt mentést töröljük.
     * Load_save: Meghívja a megfelelő függvényeket, hogy a load menüben a kijelöltt mentést betöltsük.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Exit":{
                frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
                break;
            }
            case "Start":{
                map.getGame().generateRandomMap();
                map.Buildmap();
                menus[0].getPanel().setVisible(false);
                frame.remove(menus[0].getPanel());
                map.getPanel().setVisible(true);
                frame.add(map.getPanel(), BorderLayout.NORTH);
                map.setShown(true);
                menus[2].getPanel().setVisible(true);
                frame.add(menus[2].getPanel(),BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
                map.getPanel().requestFocus();

                break;
            }
            case "Load":{
                menus[0].getPanel().setVisible(false);
                frame.remove(menus[0].getPanel());
                menus[4].getPanel().setVisible(true);
                frame.add(menus[4].getPanel());
                frame.pack();
                frame.setVisible(true);
                break;
            }
            case "Continue":{
                menus[1].getPanel().setVisible(false);
                frame.remove(menus[1].getPanel());
                map.getPanel().setVisible(true);
                frame.add(map.getPanel(),BorderLayout.NORTH);
                map.setShown(true);
                menus[2].getPanel().setVisible(true);
                frame.add(menus[2].getPanel(),BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
                map.getPanel().requestFocus();
                break;
            }
            case "Save":{
                menus[1].getPanel().setVisible(false);
                frame.remove(menus[1].getPanel());
                menus[3].getPanel().setVisible(true);
                frame.add(menus[3].getPanel());
                frame.pack();
                frame.setVisible(true);
                break;
            }
            case "Save_as":{
                SaveMenu saveMenu=(SaveMenu)menus[3];
                String filename=((JTextField)menus[3].getPanel().getComponent(3)).getText();
                if(!filename.isEmpty() && !((DefaultListModel<String>)saveMenu.getList().getModel()).contains(filename)){
                    File newSave=new File("save/"+filename+".save");
                    if(map.getGame().save(newSave)==1){
                        saveMenu.addToList(filename);
                        saveMenu.resetInput();
                        menus[3].getPanel().setVisible(false);
                        frame.remove(menus[3].getPanel());
                        menus[1].getPanel().setVisible(true);
                        frame.add(menus[1].getPanel());
                        frame.pack();
                        frame.setVisible(true);
                    }
                }
                else if(saveMenu.getList().getSelectedValue()!=null){
                    filename=saveMenu.getList().getSelectedValue();
                    saveMenu.getList().clearSelection();
                    File save=new File("save/"+filename+".save");
                    if(map.getGame().save(save)==1){
                        menus[3].getPanel().setVisible(false);
                        frame.remove(menus[3].getPanel());
                        menus[1].getPanel().setVisible(true);
                        frame.add(menus[1].getPanel());
                        frame.pack();
                        frame.setVisible(true);
                    }
                }
                break;
            }
            case "Back":{
                if(menus[3].getPanel().isVisible()){
                    menus[3].getPanel().setVisible(false);
                    frame.remove(menus[3].getPanel());
                    menus[1].getPanel().setVisible(true);
                    frame.add(menus[1].getPanel());
                    frame.pack();
                    frame.setVisible(true);
                    }
                else if(menus[4].getPanel().isVisible()){
                    menus[4].getPanel().setVisible(false);
                    frame.remove(menus[4].getPanel());
                    menus[0].getPanel().setVisible(true);
                    frame.add(menus[0].getPanel());
                    frame.pack();
                    frame.setVisible(true);
                }
                break;
            }
            case "Delete":{
                SaveMenu saveMenu=(SaveMenu) menus[3];
                if(saveMenu.getList().getSelectedValue()!=null){
                    String filename=saveMenu.getList().getSelectedValue();
                    saveMenu.getList().clearSelection();
                    File fileToDelete=new File("save/"+filename+".save");
                    if(fileToDelete.delete()){
                        DefaultListModel<String> defaultListModel=(DefaultListModel<String>)saveMenu.getList().getModel();
                        defaultListModel.remove(defaultListModel.indexOf(filename));
                    }
                }
                break;
            }
            case "Load_save":{
                LoadMenu loadMenu=(LoadMenu) menus[4];
                if(loadMenu.getSaves().getSelectedValue()!=null){
                    String filename=loadMenu.getSaves().getSelectedValue();
                    File fileToLoad=new File("save/"+filename+".save");
                    if(map.getGame().load(fileToLoad)==1){
                        map.Buildmap();
                        menus[4].getPanel().setVisible(false);
                        frame.remove(menus[4].getPanel());
                        map.getPanel().setVisible(true);
                        frame.add(map.getPanel(), BorderLayout.NORTH);
                        map.setShown(true);
                        menus[2].getPanel().setVisible(true);
                        frame.add(menus[2].getPanel(),BorderLayout.SOUTH);
                        frame.pack();
                        frame.setVisible(true);
                        map.getPanel().requestFocus();
                    }
                    else{
                        menus[4].getPanel().setVisible(false);
                        frame.remove(menus[4].getPanel());
                        menus[0].getPanel().setVisible(true);
                        frame.add(menus[0].getPanel());
                        frame.pack();
                        frame.setVisible(true);
                    }
                }
                break;
            }
        }
    }
}
