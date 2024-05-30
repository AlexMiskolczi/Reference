package Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Ez az osztály tölti be és tárolja a menük által használt képeket.
 */
public class IconFactory {
    /**
     * Hashmap, ami a betöltött ImageIcon-okat tartalmazza.
     */
    private HashMap<String, ImageIcon> Icons;

    /**
     * Konstruktor, ami inicializálja az adatokat.
     */
    public IconFactory(){
        Icons=new HashMap();
        loadIcons();
    }

    /**
     * Ez a függvény a megfelelő fájlokból betölti az Icon.init
     * fájl által meghatározott képeket.
     * Ha bármelyik fájl hiányzik a program 999-es kóddal leáll.
     */
    private void loadIcons() {
        File iconFileMenu = new File("Menu.png");
        File iconFileUI=new File("MiniWorldSprites/User Interface/UiIcons.png");
        File iconForCurrency=new File("MiniWorldSprites/User Interface/Icons-Essentials.png");
        File iconForCurrency2=new File("MiniWorldSprites/Nature/Trees.png");
        File IconInit = new File("init/Icon.init");
        ArrayList<String> IconInfo = new ArrayList<>();
        String[] tmp;
        try {
            Scanner scanner = new Scanner(IconInit);
            while (scanner.hasNextLine()){
                IconInfo.add(scanner.nextLine());
            }
            HashMap<String,BufferedImage> images=new HashMap<>();
            images.put("Menu",ImageIO.read(iconFileMenu));
            images.put("UI",ImageIO.read(iconFileUI));
            images.put("Currency",ImageIO.read(iconForCurrency));
            images.put("Currency2",ImageIO.read(iconForCurrency2));
            for (String s:IconInfo) {
                tmp=s.split(" ");
                Icons.put(tmp[1],new ImageIcon(images.get(tmp[0]).getSubimage(
                        Integer.parseInt(tmp[2]),
                        Integer.parseInt(tmp[3]),
                        Integer.parseInt(tmp[4]),
                        Integer.parseInt(tmp[5])).getScaledInstance(Integer.parseInt(tmp[6]),Integer.parseInt(tmp[7]), Image.SCALE_SMOOTH)));
            }

        } catch (IOException e) {
            System.out.println("Hiba betöltéskor!");
            System.exit(999);
        }
    }

    /**
     * Getter az Icons-hoz
     * @return Egy hashmap, ami tartalmazza a betöltött képeket.
     */
    public HashMap<String, ImageIcon> getIcons() {
        return Icons;
    }
}
