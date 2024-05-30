package Render.Menu;

import Input.ActionListeners.ActionListenerForMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Ez az osztály implementálja a Menu interface-t
 * Ez az osztály a Save menü megjelenítéséhez szükséges.
 */
public class SaveMenu implements Menu{
    /**
     * A panel, amin megjelenítünk
     */
    private JPanel panel;
    /**
     * JList, ami a save fájlok neveit tartalmazza.
     */
    private JList<String> saves;
    /**
     * JTextField, ami egy input mezőként funkcionál.
     */
    private JTextField input;

    /**
     * Konstruktor, ami meghívja a megfelelő inicializáló függvényt.
     * @param actionListenerForMenu ActionListener a gomboknak
     */
    public SaveMenu(ActionListenerForMenu actionListenerForMenu){
        init(actionListenerForMenu);
    }

    /**
     * Ez a függvény létrehoz:
     * -JTextField-eket amik fix szöveget tartalmaznak.
     * -JListet, ami a mentések neveit tartalmazza.
     * -Jbutton-okot, amik a végrehajtható műveleteket tartalmazzák.
     * Ezen felül inicializálja a változókat és felépíti a panelt.
     * @param actionListenerForMenu ActionListener a gomboknak
     */
    private void init(ActionListenerForMenu actionListenerForMenu){
        Font arial50=new Font("Arial",Font.PLAIN,50);
        Font arial80=new Font("Arial",Font.PLAIN,80);
        Color background=new Color(0,0,0);
        Color foreground=new Color(255,255,255);
        File dir=new File("save");
        String tmp;

        panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(1920,1080));
        panel.setBackground(background);
        panel.setVisible(false);

        JLabel fixText1=new JLabel();
        fixText1.setText("Choose an already existing save to overwrite!");
        fixText1.setFont(arial80);
        fixText1.setForeground(foreground);
        fixText1.setBackground(background);
        fixText1.setBorder(null);
        fixText1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(fixText1);

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

        JLabel fixText2=new JLabel();
        fixText2.setText("Or create a new save!");
        fixText2.setFont(arial80);
        fixText2.setForeground(foreground);
        fixText2.setBackground(background);
        fixText2.setBorder(null);
        fixText2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(fixText2);

        input=new JTextField();
        input.setFont(arial50);
        input.setBackground(background);
        input.setForeground(foreground);
        input.setEditable(true);
        input.setPreferredSize(new Dimension(1920,100));
        input.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(input);

        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(1920,200));

        JButton save =new JButton("Save");
        save.setBackground(background);
        save.setForeground(foreground);
        save.setFont(arial80);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setActionCommand("Save_as");
        save.addActionListener(actionListenerForMenu);
        buttonPanel.add(save);

        JButton delete =new JButton("Delete");
        delete.setBackground(background);
        delete.setForeground(foreground);
        delete.setFont(arial80);
        delete.setAlignmentX(Component.CENTER_ALIGNMENT);
        delete.setActionCommand("Delete");
        delete.addActionListener(actionListenerForMenu);
        buttonPanel.add(delete);

        JButton back=new JButton("Back");
        back.setBackground(background);
        back.setForeground(foreground);
        back.setFont(arial80);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setActionCommand("Back");
        back.addActionListener(actionListenerForMenu);
        buttonPanel.add(back);

        panel.add(buttonPanel);
    }

    /**
     * Getter a panelhez
     * @return A panel, amin megjelenítünk
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Ez a függvény a paraméterül kapot stringet hozzáadja a JList-hez.
     * @param s String, amit hozzáadunk
     */
    public void addToList(String s){
        DefaultListModel<String> model=(DefaultListModel<String>) saves.getModel();
        model.addElement(s);
    }

    /**
     * Getter a Jlist-hez.
     * @return A save fájlok neveit tartalmazó JList.
     */
    public JList<String> getList(){
        return saves;
    }

    /**
     * Ez a függvény az input JTextField szövegét alaphelyzetbe állítja.
     */
    public void resetInput(){
        input.setText("");
    }
}
