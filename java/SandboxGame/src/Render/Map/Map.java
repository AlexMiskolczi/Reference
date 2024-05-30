package Render.Map;
import Image.*;

import Game.Game;
import Input.KeyListeners.KeyListenerForMap;
import Input.MouseListeners.MouseListenerForMap;
import Input.MouseListeners.MouseWheelListenerForMap;
import Render.Menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Ez az osztály a pálya megjelenítését végzi.
 */
public class Map {
    /**
     * A panel amin megjelenítünk
     */
    private JPanel panel;
    /**
     * Game objektum, ami a pálya információkat tartalmazza.
     */
    private Game game;
    /**
     * A zoom nagysága
     */
    private double zoom;
    /**
     * Meg van-e jelenítve a pálya panelja.
     */
    private boolean isShown;
    /**
     * Billentyűket kezeli a pálya panelján.
     */
    private KeyListenerForMap keyListenerForMap;
    /**
     * Egeret kezeli a pálya panelján.
     */
    private MouseListenerForMap mouseListenerForMap;
    /**
     * Görgőt kezeli a pálya panelján.
     */
    private MouseWheelListenerForMap mouseWheelListenerForMap;
    /**
     * Kijelölt elem koordinátái a megjelenített koordináták alapján.
     */
    private int[] selectedElementOSC;
    /**
     * A megjeleníthető elemeket betöltő objektum.
     */
    private ImageFactory imageFactory;
    /**
     * A Game objektum idjeinek 0,0 indexű eleméhez képest a megjelenített elemek eltolása.
     */
    private int[] offset;
    /**
     * Eltolás iránya
     * 1: balra
     * 2: jobbra
     * 3: le
     * 4: fel
     */
    private int offsetDirection;

    /**
     * Konstruktor, ami inicializálja az adatokat.
     * @param frame A frame amire rajzolunk.
     * @param g Game objektum.
     * @param menus A megjeleníthető menük.
     */
    public Map(JFrame frame, Game g, Menu[] menus){
        offset=new int[2];
        mouseWheelListenerForMap=new MouseWheelListenerForMap(this);
        imageFactory=new ImageFactory();
        keyListenerForMap=new KeyListenerForMap(frame,this,menus);
        game=g;
        mouseListenerForMap=new MouseListenerForMap(this);
        isShown=false;
        zoom=1;
        selectedElementOSC=new int[2];
        selectedElementOSC[0]=999;
        selectedElementOSC[1]=999;
        offsetDirection=0;
    }

    /**
     * Ez a függvény felépíti a megjelenítendő pályát,
     * illetve átméretezésnél újra építi azt.
     * Az egyes pálya elemek külön componentként kerülnek hozzáadásra
     * a panelhoz.
     */
    public void Buildmap(){
        if(panel==null){
            panel=new JPanel();
            panel.addKeyListener(keyListenerForMap);
            panel.addMouseListener(mouseListenerForMap);
            panel.addMouseWheelListener(mouseWheelListenerForMap);
            panel.setPreferredSize(new Dimension(1920,960));
            panel.setBackground(new Color(0,0,0));
            panel.setFocusable(true);
            panel.setLayout(new GridLayout((int)(60*zoom),(int)(120*zoom)));
        }
        else{
            panel.removeAll();
            panel.setLayout(new GridLayout((int)(60*zoom),(int)(120*zoom)));
            correctOffset();
        }
        synchronized (game){
            for(int i=offset[0];i<((int)(60*zoom))+offset[0];i++){
                for(int j=offset[1];j<((int)(120*zoom))+offset[1];j++){
                    LayeredIcon iconToAdd=new LayeredIcon(imageFactory.getDoneImages(),imageFactory.getSelect(),zoom);
                    iconToAdd.setName(Integer.toString(game.getMap_Element_id(i,j)));
                    panel.add(iconToAdd);
                }
            }
        }
        isShown=true;
    }

    /**
     * Ez a függvény átméretezésnél kijavíítja az offset-et.
     * így mindig a megfelelő indexek között jelenítünk meg.
     */
    private void correctOffset(){
        if(offset[1]+zoom*120>=200){
            offset[1]=(int)(200-zoom*120);
        }
        if(offset[0]+zoom*60>=100){
            offset[0]=(int)(100-zoom*60);
        }
    }

    /**
     * A függvény leveszi a kijelölést a megjelenítésről, ezt követően
     * az offset irányától függően elmozdítja a pályát abba az irányba egy mezővel.
     */
    public void offsetUpdate(){
        LayeredIcon icon;
        if(selectedElementOSC[0]!=999 && selectedElementOSC[1]!=999){
            removeSelect();
        }
        synchronized (game){
            switch(offsetDirection){
                case 1:{
                    for(int i=0;i<zoom*60;i++){
                        panel.remove((int)(i*zoom*120));
                        icon=new LayeredIcon(imageFactory.getDoneImages(),imageFactory.getSelect(),zoom);
                        icon.setName(Integer.toString(game.getMap_Element_id(i+offset[0],(int)(zoom*120+offset[1])-1)));
                        panel.add(icon,(int)((i+1)*zoom*120-1));
                    }
                    break;
                }
                case 2:{
                    for(int i=0;i<zoom*60;i++){
                        panel.remove((int)((i+1)*zoom*120-1));
                        icon=new LayeredIcon(imageFactory.getDoneImages(),imageFactory.getSelect(),zoom);
                        icon.setName(Integer.toString(game.getMap_Element_id(i+offset[0],offset[1])));
                        panel.add(icon,(int)(i*zoom*120));
                    }
                    break;
                }
                case 3:{
                    for(int i=0;i<zoom*120;i++){
                        panel.remove(0);
                        icon=new LayeredIcon(imageFactory.getDoneImages(),imageFactory.getSelect(),zoom);
                        icon.setName(Integer.toString(game.getMap_Element_id((int)(60*zoom+offset[0])-1,i+offset[1])));
                        panel.add(icon);
                    }
                    break;
                }
                case 4:{
                    for (int i=0;i<zoom*120;i++){
                        panel.remove((int)((zoom*60)*(zoom*120))-(i+1));
                    }
                    for (int i=0;i<zoom*120;i++){
                        icon=new LayeredIcon(imageFactory.getDoneImages(),imageFactory.getSelect(),zoom);
                        icon.setName(Integer.toString(game.getMap_Element_id(offset[0],i+offset[1])));
                        panel.add(icon,i);
                    }
                    break;
                }
        }

        }


    }

    /**
     * Ez a függvény frissíti a megjelenített pályarészt a
     * Game objektum idjei alapján, ha azok metváltoztak.
     * Az optimális működés miatt, csak az adott componenteket frissíti és rajzolja újra.
     */
    public void update(){
        synchronized (game) {
            if (game.isMapIdsUpdated()) {
                int k = 0;
                for (int i = offset[0]; i < (int) (60 * zoom) + offset[0]; i++) {
                    for (int j = offset[1]; j < (int) (120 * zoom) + offset[1]; j++) {
                        if (!(panel.getComponent(k)).getName().equals(Integer.toString(game.getMap_Element_id(i, j)))) {
                            panel.getComponent(k).setName(Integer.toString(game.getMap_Element_id(i, j)));
                            panel.getComponent(k).revalidate();
                            panel.getComponent(k).repaint();
                        }
                        k++;
                    }
                }
                game.setMapIdsUpdated(false);
            }
        }
    }

    /**
     * Ez a függvény a kijelölt mezőről leveszi a kijelölést.
     */
    public void removeSelect(){
        game.getSelected()[0]=999;
        game.getSelected()[1]=999;
        LayeredIcon icon=(LayeredIcon) panel.getComponent((int)((selectedElementOSC[1])*zoom*120+(selectedElementOSC[0])));
        icon.setSelected(false);
        icon.revalidate();
        icon.repaint();
        selectedElementOSC[0]=999;
        selectedElementOSC[1]=999;

    }

    /**
     * A függvény megnézi van-e mozgatásra kijelölt mező, ha van akkor a kijelölt mezőre mozgatja
     * a mozgatásra kijelölt mezőt, és ez lesz az új kijelölt.
     * Ha nincs akkor simán kijelöl egy mezőt és újra rajzolja azt.
     */
    public void select(){
        if(game.getElementToBeMoved()[0]!=999 && game.getElementToBeMoved()[1]!=999){
            game.getSelected()[0]=selectedElementOSC[0]+offset[1];
            game.getSelected()[1]=selectedElementOSC[1]+offset[0];
            if(game.move()){
                LayeredIcon icon=(LayeredIcon) panel.getComponent((int)(selectedElementOSC[1]*zoom*120+selectedElementOSC[0]));
                icon.setSelected(true);
                icon.revalidate();
                icon.repaint();
            }
        }
        else {
            game.getSelected()[0]=selectedElementOSC[0]+offset[1];
            game.getSelected()[1]=selectedElementOSC[1]+offset[0];
            LayeredIcon icon=(LayeredIcon) panel.getComponent((int)(selectedElementOSC[1]*zoom*120+selectedElementOSC[0]));
            icon.setSelected(true);
            icon.revalidate();
            icon.repaint();
        }
    }

    /**
     * Getter a Panel-hez.
     * @return A panel amin megjelenítünk..
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Setter a shown-hoz.
     * @param shown Látható-e a panel.
     */
    public void setShown(boolean shown) {
        isShown = shown;
    }

    /**
     * Getter a shown-hoz
     * @return Látható-e a panel.
     */
    public boolean isShown() {
        return isShown;
    }

    /**
     * Getter a selectedElementOSC-hez
     * @return Egy tömb ami a kijelölt elem koordinátáit tartalmazza a megjelenítőn.
     */
    public int[] getSelectedElementOSC() {
        return selectedElementOSC;
    }

    /**
     * Getter a game-hez.
     * @return Game objektum, ami alapján megjelenítünk.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter a zoomhoz.
     * @return A zoom nagysága.
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * Setter a zoomhoz.
     * @param zoom A zoom nagysága.
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    /**
     * Getter az offset-hez.
     * @return Egy tömb, ami az offset-et tartalmazza.
     */
    public int[] getOffset() {
        return offset;
    }

    /**
     * Setter az offsetdirection-höz.
     * @param offsetDirection Az irány (1:bal,2:jobb,3:le,4:fel)
     */
    public void setOffsetDirection(int offsetDirection) {
        this.offsetDirection = offsetDirection;
    }
}
