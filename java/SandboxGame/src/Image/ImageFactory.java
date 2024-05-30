package Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Ez az osztály tölti be és tárolja a kirajzolt elemeket.
 */
public class ImageFactory {
    /**
     * Hashmap, ami a betöltött ImageIcon-okat(képeket) tartalmazza.
     */
    private HashMap<Integer, ImageIcon> Images;
    /**
     * Hashmap, ami az elemekhez tartozó ImageIcon-okat tartalmaz.
     */
    private HashMap<Integer,ArrayList<ImageIcon>> DoneImages;
    /**
     * ImageIcon select-hez.
     */
    private ImageIcon select;

    /**
     * Konstruktor, ami inicializálja az adatokat.
     */
    public ImageFactory(){
        Images=new HashMap<>();
        DoneImages =new HashMap<>();
        init();
        select=Images.get(0);
        generateElementTiles();
    }

    /**
     * Inicializáló függvény, ami beolvassa a szükséges képeket az elemek felépítéséhez.
     * A szükséges képeket az Image.init fájl alapján tölti be.
     * Ha nem található bármelyik fájl a program 999-es kóddal leáll.
     */
    private void init(){
        ArrayList<String> Image_info;
        Image_info=new ArrayList<>();
        Scanner scanner;
        File init_file=new File("init/Image.init");
        File Image_file;
        BufferedImage Whole_Image;
        ImageIcon icon;
        try{
            scanner=new Scanner(init_file);
            while (scanner.hasNextLine()){
                Image_info.add(scanner.nextLine());
            }
            String[] tmp;
            for (String i:Image_info) {
                tmp=i.split(",");
                Image_file=new File(tmp[1]);
                Whole_Image= ImageIO.read(Image_file);
                icon=new ImageIcon(Whole_Image.getSubimage(Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3]),16,16));
                Images.put(Integer.parseInt(tmp[0]),icon);
            }
        }
        catch(FileNotFoundException exception){
            System.err.println("Init fájl hiányzik!");
            System.exit(999);
        }
        catch(IOException exception){
            System.err.println("Hiba kép betöltése közben!");
            System.exit(999);
        }
    }

    /**
     * Ez a függvény a betöltött képekből felépíti a kirajzolható elemeket
     * a GameTile.init fájl alapján.
     * Egy ArrayListben tárolja a szükséges ImageIcon-okat az adott elemhez.
     * Ha a fájl nem található a program leáll 999-es kóddal.
     */
    private void generateElementTiles(){
        ArrayList<String> Tile_info=new ArrayList<>();
        String[] line;
        String[] ImagesInTile;
        File init_file=new File("init/GameTile.init");
        LayeredIcon layeredIcon;
        ArrayList<ImageIcon> icons;
        try{
            Scanner scanner=new Scanner(init_file);
            while (scanner.hasNextLine()){
                Tile_info.add(scanner.nextLine());
            }
            for (String s:Tile_info) {
                line=s.split(" ");
                ImagesInTile=line[1].split(",");
                icons=new ArrayList<>();
                for (String ss:ImagesInTile) {
                 icons.add(Images.get(Integer.parseInt(ss)));
                }
                DoneImages.put(Integer.parseInt(line[0]),icons);
            }
        }
        catch (FileNotFoundException exception){
            System.out.println("Fájl nem található!");
            System.exit(999);
        }
    }

    /**
     * Getter a DoneImages-hez
     * @return Egy hashmap ami a kész kirajzolható elemeket tartalmazza.
     */
    public HashMap<Integer, ArrayList<ImageIcon>> getDoneImages() {
        return DoneImages;
    }

    /**
     * Getter a select-hez
     * @return A select ImageIcon-ját adja vissza.
     */
    public ImageIcon getSelect() {
        return select;
    }
}
