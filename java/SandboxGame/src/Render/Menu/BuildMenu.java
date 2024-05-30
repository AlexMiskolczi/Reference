package Render.Menu;

import Game.Game;
import Input.ActionListeners.ActionListenerForInGameMenu;
import Render.Menu.BuildMenuCustomPanel.BuildMenuCustomPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ez az osztály implementálja a Menu inteface-t.
 * A játékfolyamat során ez a menü jelenik meg építésnél.
 */
public class BuildMenu implements Menu{
    /**
     * ArrayList, ami JLabel-eket tartalmaz, amik az erőforrásokat jelenítik meg.
     */
    private ArrayList<JLabel> resources;
    /**
     * Game objektum, ami alapján megjelenítünk.
     */
    private Game game;
    /**
     * Egy belső panel, ami megjeleníti az építhető elemeket.
     */
    private BuildMenuCustomPanel buildMenuCustomPanel;
    /**
     * A panel, ami megjelenítünk.
     */
    private JPanel panel;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * Létrehozza a resources listába kerülő Jlabeleket és
     * a hozzájuk tartozó Jlabeleket, amik az erőforrás icon-ját
     * jelenítik meg.
     * Létrehoz továbbá egy gombot is.
     * Majd ezeket hozzáadja a panelhez.
     * @param g Game objektum, ami alapján megjelenítünk.
     * @param images A megjeleníthető elemek icon-jai(pálya elemek)
     * @param icons A menuk icon-jai.
     * @param iconkeys A menuk iconjainak hashmap-jához tartozó kulcsok.
     * @param actionListenerForInGameMenu ActionListener a gomboknak.
     */
    public BuildMenu(Game g, HashMap<Integer, ArrayList<ImageIcon>> images, HashMap<String,ImageIcon> icons, String[] iconkeys, ActionListenerForInGameMenu actionListenerForInGameMenu){
        game=g;
        panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(1920,120));
        panel.setBackground(new Color(158,106,75));
        resources=new ArrayList<>();
        JLabel tmp;
        synchronized (game){
            for(int i=0;i<iconkeys.length;i++){
                tmp=new JLabel();
                tmp.setPreferredSize(new Dimension(120,30));
                tmp.setText(Integer.toString(game.getResources()[i]));
                tmp.setAlignmentX(Component.CENTER_ALIGNMENT);
                tmp.setFont(new Font("Arial",Font.PLAIN,20));
                tmp.setForeground(new Color(255,255,255));
                tmp.setBackground(new Color(158,106,75));
                tmp.setBorder(null);
                resources.add(tmp);
            }
        }
        JPanel resourcePanel=new JPanel();
        resourcePanel.setLayout(new GridLayout(4,2));
        resourcePanel.setBackground(new Color(158,106,75));
        resourcePanel.setMaximumSize(new Dimension(150,120));
        for(int i=0;i<iconkeys.length;i++){
            tmp=new JLabel();
            tmp.setIcon(new ImageIcon((icons.get(iconkeys[i])).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
            tmp.setForeground(new Color(255,255,255));
            tmp.setBackground(new Color(158,106,75));
            tmp.setBorder(null);
            tmp.setPreferredSize(new Dimension(30,30));
            resourcePanel.add(tmp);
            resourcePanel.add(resources.get(i));
        }
        panel.add(resourcePanel);
        JButton backButton=new JButton();
        backButton.setFont(new Font("Arial",Font.PLAIN,50));
        backButton.setText("Back");
        backButton.setActionCommand("Back");
        backButton.setPreferredSize(new Dimension(200,100));
        backButton.setForeground(new Color(255,255,255));
        backButton.setBackground(new Color(130,83,0));
        backButton.addActionListener(actionListenerForInGameMenu);
        backButton.setBorder(null);
        panel.add(backButton);
        panel.add(Box.createRigidArea(new Dimension(170,120)));
        buildMenuCustomPanel=new BuildMenuCustomPanel(images,icons,iconkeys,game,actionListenerForInGameMenu);
        panel.add(buildMenuCustomPanel);
    }

    /**
     * Ez a függvény frissíti az erőforrások mennyiségét megjelenítő Jlabelek text-jét.
     */
    public void update(){
        synchronized (game){
            for(int i=0;i<resources.size();i++){
                resources.get(i).setText(Integer.toString(game.getResources()[i]));
            }
        }

    }

    /**
     * Ez a függvény meghívja a belső panel megfelelő függvényét,
     * majd újra rajzolja azt.
     */
    public void reconstructBuildPanel(){
        buildMenuCustomPanel.constructPanel();
        buildMenuCustomPanel.revalidate();
        buildMenuCustomPanel.repaint();
    }

    /**
     * Getter a Panelhez.
     * @return A panel, amin megjelenítünk.
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Getter a BuildMenuCustomPanelhoz.
     * @return A belső panel, amin a felépíthető elemeket jelenítjük meg.
     */
    public BuildMenuCustomPanel getBuildMenuCustomPanel() {
        return buildMenuCustomPanel;
    }
}
