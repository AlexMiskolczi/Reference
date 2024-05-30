package Render.Menu;

import Game.Game;
import Input.ActionListeners.ActionListenerForInGameMenu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import javax.swing.tree.FixedHeightLayoutCache;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ez az osztály implementálja a Menu interface-t.
 * A játékfolyamat során ez a menü jelenik meg, innen tud a felhasználó bizonyos műveleteket elvégezni.(építés,betakarítás)
 */
public class InGameMenu implements Menu {
    /**
     * A panel, amin megjelenítünk.
     */
    private JPanel panel;
    /**
     * ArrayList ami JLabeleket tartalmaz.
     * Ezek a labelek az erőforrások mennyiségét jelenítik meg.
     */
    private ArrayList<JLabel> labels;
    /**
     * Game objektum, ami alapján megjelenítünk.
     */
    private Game game;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * A labels listához létrehozza és hozzáadja a JLabeleket.
     * Létrehozza ezekhez a labelekhez tartozó Jlabeleket, az erőforrások icon-jaival.
     * Létrehozza a menüben megjelenő gombokat.
     * Majd ezekből felépíti a panelt.
     * @param icons Menu icon-jai.
     * @param iconkeys A menuk icon-jainak hashmap-jához tartozó kulcsok.(gombok icon-jai)
     * @param labelIconKeys A menuk icon-jainak hashmap-jához tartozó kulcsok.(erőforrások icon-jai)
     * @param g Game objektum, ami alapján megjelenítünk.
     * @param alfigm ActionListener a gomboknak.
     */

    public InGameMenu(HashMap<String,ImageIcon> icons, String[] iconkeys, String[] labelIconKeys, Game g, ActionListenerForInGameMenu alfigm){
        game=g;
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(1920,120));
        panel.setBackground(new Color(158,106,75));
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        labels=new ArrayList<>();
        JLabel tmp;
        synchronized (game){
            for(int i=0;i<labelIconKeys.length;i++){
                tmp=new JLabel();
                tmp.setPreferredSize(new Dimension(300,50));
                tmp.setText(Integer.toString(game.getResources()[i]));
                tmp.setFont(new Font("Arial",Font.PLAIN,50));
                tmp.setForeground(new Color(255,255,255));
                tmp.setBackground(new Color(158,106,75));
                tmp.setBorder(null);
                labels.add(tmp);
            }
        }
        JPanel insidePanel=new JPanel();
        insidePanel.setLayout(new GridLayout(2,4));
        insidePanel.setBackground(new Color(158,106,75));
        insidePanel.setMaximumSize(new Dimension(700,120));
        int id=0;
        for (String s:labelIconKeys) {
            tmp=new JLabel();
            tmp.setIcon(icons.get(s));
            tmp.setForeground(new Color(255,255,255));
            tmp.setBackground(new Color(158,106,75));
            tmp.setBorder(null);
            tmp.setPreferredSize(new Dimension(50,50));
            insidePanel.add(tmp);
            insidePanel.add(labels.get(id));
            id++;
        }
        panel.add(insidePanel);
        panel.add(Box.createRigidArea(new Dimension(820,120)));
        JButton[] buttons=new JButton[4];
        for(int i=0;i<4;i++){
            buttons[i]=new JButton();
            buttons[i].setPreferredSize(new Dimension(100,100));
            buttons[i].setBackground(new Color(158,106,75));
            buttons[i].setActionCommand(iconkeys[i]);
            buttons[i].addActionListener(alfigm);
            buttons[i].setIcon(icons.get(iconkeys[i]));
            buttons[i].setBorder(null);
            buttons[i].setFocusPainted(false);
            panel.add(buttons[i]);
        }
    }

    /**
     * Ez a függvény az erőforrások mennyiségét megjelenítő JLabeleket frissíti.
     */
    public void update(){
        synchronized (game){
            for(int i=0;i<labels.size();i++){
                labels.get(i).setText(Integer.toString(game.getResources()[i]));
            }
        }

    }

    /**
     * Getter a panelhez.
     * @return A panel, amin megjelenítünk.
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }
}
