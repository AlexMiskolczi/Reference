package Render.Menu;
import Input.ActionListeners.ActionListenerForMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

/**
 * Ez az osztály implementálja a Menu interface-t.
 * Ez az osztály a sima gombokat tatalmazó menük megjelenítéséhez szükséges.
 */
public class NormalMenu implements Menu {
    /**
     * A panel, amin megjelenítünk.
     */
    private JPanel panel;

    /**
     * Konstruktor, ami inicializálja a változókat.
     * Ezen felül létrehozza a gombokat és felépíti a panelt.
     * @param Button_count Gombok száma
     * @param frame Frame, amin megjelenítünk
     * @param in ActionListener a gombokhoz.
     * @param icons A menuk icon-jai.
     * @param iconKeys A menuk icon-jait tartalmazó hashmap kulcsai.
     */
    public NormalMenu(int Button_count, JFrame frame, ActionListenerForMenu in, HashMap<String,ImageIcon> icons,String[] iconKeys){
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(1920,1080));
        BoxLayout boxLayout=new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.setBackground(new Color(0,0,0));
        int y_offset=(1080-Button_count*200)/(Button_count+1);
        panel.setBorder(new EmptyBorder(new Insets(y_offset,660,100,100)));
        JButton[] buttons;
        buttons=new JButton[Button_count];
        for(int i=0;i<Button_count;i++) {
            buttons[i] = new JButton();
            buttons[i].setActionCommand(iconKeys[i]);
            buttons[i].setBackground(new Color(0, 0, 0));
            buttons[i].setIcon(icons.get(iconKeys[i]));
            buttons[i].setBorder(null);
            buttons[i].addActionListener(in);
            panel.add(buttons[i]);
            panel.add(Box.createRigidArea(new Dimension(0,y_offset)));
        }
    }

    /**
     * Getter a panelhez
     * @return A panel, amin megjelenítünk.
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }
}
