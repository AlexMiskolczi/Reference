package Render.Menu;

import Input.ActionListeners.ActionListenerForMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Ez az osztály implementálja a Menu interface-t
 * A betöltéshez szükséges menüt valósítja meg.
 */
public class LoadMenu implements Menu{
    /**
     * A panel, amin megjelenítünk.
     */
    private JPanel panel;
    /**
     * Egy JList, ami a save fájlok neveit tartalmazza.
     */
    private JList<String> saves;

    /**
     * Konstruktor, ami meghívja a megfelelő inicializáló függvényt.
     * @param actionListenerForMenu ActionListener a gomboknak.
     */
    public LoadMenu(ActionListenerForMenu actionListenerForMenu){
        init(actionListenerForMenu);
    }

    /**
     * Ez a függvény létrehoz:
     * -JLabeleket, amik a fix szövegeket jelenítik meg.
     * -Jlistet, ami a save fájlok neveit tartalmazza.
     * -Jbuttonokat, amik a végrehajtható műveleteket tartalmazzák.
     * Majd ezekből felépíti a panelt.
     * @param actionListenerForMenu ActionListener a gombokhoz.
     */
    private void init(ActionListenerForMenu actionListenerForMenu){
        Font arial50=new Font("Arial",Font.PLAIN,50);
        Font arial80=new Font("Arial",Font.PLAIN,80);
        Color background=new Color(0,0,0);
        Color foreground=new Color(255,255,255);

        panel=new JPanel();
        panel.setPreferredSize(new Dimension(1920,1080));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(background);
        panel.setVisible(false);

        JLabel fixText=new JLabel();
        fixText.setText("Choose a save to load!");
        fixText.setFont(arial80);
        fixText.setBorder(null);
        fixText.setBackground(background);
        fixText.setForeground(foreground);
        fixText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(fixText);

        File dir=new File("save");
        String tmp;
        DefaultListModel<String> model=new DefaultListModel<>();
        for (File f:dir.listFiles()) {
            tmp=f.getName().substring(0,f.getName().indexOf("."));
            model.addElement(tmp);
        }
        saves=new JList<>(model);
        DefaultListCellRenderer renderer=(DefaultListCellRenderer) saves.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        saves.setFont(arial50);
        saves.setBackground(background);
        saves.setForeground(foreground);
        JScrollPane scrollPane=new JScrollPane(saves);
        scrollPane.setPreferredSize(new Dimension(1920,600));
        scrollPane.setBackground(background);
        panel.add(scrollPane);

        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(1920,200));

        JButton save =new JButton("Load");
        save.setBackground(background);
        save.setForeground(foreground);
        save.setFont(arial80);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setActionCommand("Load_save");
        save.addActionListener(actionListenerForMenu);
        buttonPanel.add(save);

        JButton delete =new JButton("Back");
        delete.setBackground(background);
        delete.setForeground(foreground);
        delete.setFont(arial80);
        delete.setAlignmentX(Component.CENTER_ALIGNMENT);
        delete.setActionCommand("Back");
        delete.addActionListener(actionListenerForMenu);
        buttonPanel.add(delete);

        panel.add(buttonPanel);
    }

    /**
     * Getter a panelhez.
     * @return A panel, amin megjelenítünk.
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Getter a save-hez.
     * @return Egy JList, ami a save-ek neveit tartalmazza.
     */
    public JList<String> getSaves() {
        return saves;
    }
}
