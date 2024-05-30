package Render.Menu.BuildMenuCustomPanel;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import Game.Game;
import Input.ActionListeners.ActionListenerForInGameMenu;

/**
 * Ez az osztály a a JPanel osztályból öröklődik.
 * A BuildMenu-ben belső panel, amin az építhető elemeket jelenítjük meg.
 */
public class BuildMenuCustomPanel extends JPanel {
    /**
     * Egy ArrayList aminek az elemei az építhető elemeket szimbolizáló gombok(Jbutton).
     */
    private ArrayList<JButton> BuildableItems;
    /**
     * Egy ArrayList, amiben a lista balra és jobb léptetéséhez szükséges gombok(JButton) vannak.
     */
    private ArrayList<JButton> buttons;
    /**
     * Az éppen megjelenített elemek közül az elsőnek az indexe a BuildableItems-ben.
     */
    private int idxOfFirstElement;

    /**
     * Konstruktor,ami inicializálja a változókat.
     * @param images Elemek amiket meg jelenítünk(pályaelemek).
     * @param icons Menü icon-ok.
     * @param iconkeys A menü icon-ok hashmap-jából használandó iconok kulcsai.
     * @param game Game objektum, ami alapján megjelenítünk.
     * @param actionListenerForInGameMenu ActionListener a gomboknak.
     */
    public BuildMenuCustomPanel(HashMap<Integer, ArrayList<ImageIcon>> images, HashMap<String,ImageIcon> icons, String[] iconkeys,Game game,ActionListenerForInGameMenu actionListenerForInGameMenu){
        idxOfFirstElement=0;
        this.setBackground(new Color(0,0,255));
        this.setBackground(new Color(158,106,75));
        BuildableItems=new ArrayList<>();
        buttons=new ArrayList<>();
        createBuildableItems(images,icons,iconkeys,game,actionListenerForInGameMenu);
        createButtons(icons,actionListenerForInGameMenu);
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        constructPanel();
    }

    /**
     * Ez a függvény létrehozza a gombokat a buildableItems listába.
     * A megjeleníthető elemek közül mindegyiknek készül egy gomb, amelyik felépíthető.
     * Ezeknek az elemeknek készít egy ImageIcont, amit a gomb megjelenít majd.
     * @param images Elemek amiket meg jelenítünk(pályaelemek).
     * @param icons Menü icon-ok.
     * @param iconkeys A menü icon-ok hashmap-jából használandó iconok kulcsai.
     * @param game Game objektum, ami alapján megjelenítünk.
     * @param actionListenerForInGameMenu ActionListener a gomboknak.
     */
    private void createBuildableItems(HashMap<Integer, ArrayList<ImageIcon>> images, HashMap<String,ImageIcon> icons, String[] iconkeys,Game game,ActionListenerForInGameMenu actionListenerForInGameMenu){
        JButton temp;
        ImageIcon tempIcon;
        for (Integer i:game.getElementInfo().keySet()) {
            if(game.getElementInfo().get(i).isBuildAble()){
                temp=new JButton();
                temp.setMaximumSize(new Dimension(200,100));
                temp.setBackground(new Color(158,106,75));
                temp.setLayout(new BoxLayout(temp,BoxLayout.X_AXIS));
                BufferedImage image=new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D=(Graphics2D) image.getGraphics();
                graphics2D.setPaint(new Color(158,106,75));
                graphics2D.fillRect(0,0,200,100);
                graphics2D.setPaint(new Color(255,255,255));
                graphics2D.setFont(new Font("Arial",Font.PLAIN,25));
                for (ImageIcon icon:images.get(i)) {
                    graphics2D.drawImage(icon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH),0,0,null);
                }
                int idx=0;
                for (String s:iconkeys) {
                    graphics2D.drawImage(icons.get(s).getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH),100,idx*25,null);
                    graphics2D.drawString(Integer.toString(game.getElementInfo().get(i).getPrice().get(idx)),130,(idx+1)*24);
                    idx++;
                }
                tempIcon=new ImageIcon(image);
                temp.setIcon(tempIcon);
                temp.setBorder(new LineBorder(new Color(0,0,0),3));
                temp.setName(Integer.toString(i));
                temp.setActionCommand("BuildSelected");
                temp.addActionListener(actionListenerForInGameMenu);
                BuildableItems.add(temp);
            }
        }
    }

    /**
     * Ez a függvény létrehozza a buttons listába kerülő vezérlő gombokat.
     * @param icons Menü icon-ok.
     * @param actionListenerForInGameMenu ActionListener a gomboknak.
     */
    private void createButtons( HashMap<String,ImageIcon> icons, ActionListenerForInGameMenu actionListenerForInGameMenu){
        String[] keys={"Left","Right"};
        JButton temp;
        for (String s:keys) {
            temp=new JButton();
            temp.setIcon(icons.get(s));
            temp.setActionCommand(s);
            temp.setBorder(null);
            temp.addActionListener(actionListenerForInGameMenu);
            temp.setBackground(new Color(158,106,75));
            buttons.add(temp);
        }
    }

    /**
     * Ez a függvény építi fel a megjelenítendő panel.
     * A két vezérlő gombot és 6 gombot a buildableItems listából
     * ad hozzá a panelhoz.
     */
    public void constructPanel(){
        this.removeAll();
        this.add(buttons.get(0));
        for(int i=idxOfFirstElement;i<(idxOfFirstElement+6);i++){
            this.add(BuildableItems.get(i));
        }
        this.add(buttons.get(1));
    }

    /**
     * Getter az IdxOffFirstElement-hez.
     * @return A megjelenített elemek közül az elsőnek az indexe a buildableItems listában.
     */
    public int getIdxOfFirstElement() {
        return idxOfFirstElement;
    }

    /**
     * Setter az IdxOffFirstElement-hez.
     * @param idxOfFirstElement A megjelenített elemek közül az elsőnek az indexe a buildableItems listában.
     */
    public void setIdxOfFirstElement(int idxOfFirstElement) {
        this.idxOfFirstElement = idxOfFirstElement;
    }

    /**
     * Getter
     * @return A buildableItems lista mérete.
     */
    public int getElementCount(){
        return BuildableItems.size();
    }

    /**
     * Teszteléshez
     */
    public ArrayList<JButton> getBuildableItems() {
        return BuildableItems;
    }

    /**
     * Teszteléshez
     */
    public ArrayList<JButton> getButtons() {
        return buttons;
    }
}
