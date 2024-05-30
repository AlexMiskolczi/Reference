package Game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Ez az osztály tartalmaz a játék pályájáról és logikájáról információkat.
 */

public class Game implements Serializable{
    /**
     * A pálya elemeinek az idjeit tartalmazza.
     */
    private int[][] mapElementIds;
    /**
     * Az erőforrásokat atartalmazza.
     */
    private int[] resources;
    /**
     * Frissültek-e az erőforrások.
     */
    private boolean resorcesUpdated;
    /**
     * Frissültek-e a pálya idjei.
     */
    private boolean mapIdsUpdated;
    /**
     * A kiválasztott elem indexei.
     */
    private int[] selected;
    /**
     * Hashmap, ami az elemtInfo-kat tárolja.
     */
    private HashMap<Integer,Elementinfo> elementInfo;
    /**
     * Mozgatásra kijelkölt elem indexei.
     */
    private int[] elementToBeMoved;
    /**
     * Arraylist ami az updatetask-ekat tartalmazza.
     */
    private ArrayList<UpdateTask> updateTasks;

    /**
     * Konstruktor, inicializálja a változókat.
     */
    public Game(){
        mapElementIds =new int[100][200];
        resources =new int[4];
        mapIdsUpdated =false;
        selected=new int[2];
        elementInfo =new HashMap<>();
        loadElemetInfo();
        resorcesUpdated =false;
        elementToBeMoved=new int[2];
        elementToBeMoved[0]=999;
        elementToBeMoved[1]=999;
        updateTasks=new ArrayList<>();
        createUpdateTasks();
    }

    /**
     * Ez a függvény genmerál egy random pályát, ami tartalmazza a
     * játékban a fejlődéshez szükséges nyersanyagokat.
     * Generálás után átállítja a megfelelő tagváltozót.
     */
    public void generateRandomMap(){
        synchronized (this){
            int random;
            for(int i=0;i<100;i++){
                for(int j=0;j<200;j++){
                    random= ThreadLocalRandom.current().nextInt(1,100);
                    if(random<=85){
                        mapElementIds[i][j]=0;
                    }
                    if(random<=95 && random>85){
                        mapElementIds[i][j]=2;
                    }
                    if(random>95){
                        mapElementIds[i][j]=3;
                    }
                }
            }
            setMapIdsUpdated(true);
        }
    }

    /**
     * Ez a függvény elmenti az adatokat egy fájlba.
     * @param saveFile A fájl amibe mentünk.
     * @return 0-Sikertelen, 1-Sikeres mentés.
     */
    public int save(File saveFile){
        synchronized (this){
            try{
                FileOutputStream fileOutputStream=new FileOutputStream(saveFile);
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(this);
                objectOutputStream.close();
                fileOutputStream.close();
                return 1;
            }
            catch (FileNotFoundException e){
                return 0;
            }
            catch (IOException e){
                return 0;
            }
        }
    }

    /**
     *Ez a függvény betölt egy mentést egy adott fájlból.
     * @param saveFile A betölteni kívánt fájl.
     * @return 0-Sikertelen, 1-Sikeres betöltés.
     */
    public int load(File saveFile){
        synchronized (this){
            try{
                FileInputStream fileInputStream=new FileInputStream(saveFile);
                ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
                Game in=(Game) objectInputStream.readObject();
                this.setMapElementIds(in.getMapElementIds());
                this.setResources(in.getResources());
                resorcesUpdated =true;
                objectInputStream.close();
                fileInputStream.close();
                return 1;
            }
            catch (FileNotFoundException e){
                return 0;
            }
            catch (IOException e){
                return 0;
            }
            catch (ClassNotFoundException e){
                return 0;
            }
        }
    }

    /**
     * Ez a függvény törli az éppen kiválasztott elemet.
     * A törlés azt jelenti, hogy az alap elemet(fű, id:0) teszi a helyére és
     * az erőforrásokhoz adja az elem értékét.
     */
    public void deleteElement(){
        if(selected[0]!=999 && selected[1]!=999){
            if(elementInfo.containsKey(mapElementIds[selected[1]][selected[0]])) {
                for (int i = 0; i < 4; i++) {
                    resources[i] += elementInfo.get(mapElementIds[selected[1]][selected[0]]).getValue().get(i);
                }
            }
            mapElementIds[selected[1]][selected[0]]=0;
            mapIdsUpdated =true;
            resorcesUpdated =true;
        }
    }

    /**
     * Ez a függvény betakarítja a kijelölt elemet.
     * Betakarítás azt jelenti, hogy az erőforrásokhoz hozzáadja az értékét az elemnek
     * és átállítja egy olyan elemre, amit betakarítás után kaphatunk.
     */
    public void Harvest(){
        if(elementInfo.containsKey(mapElementIds[selected[1]][selected[0]])){
            if(elementInfo.get(mapElementIds[selected[1]][selected[0]]).getHarvestAble()){
                for(int i=0;i<4;i++){
                    resources[i]+= elementInfo.get(mapElementIds[selected[1]][selected[0]]).getValue().get(i);
                }
                mapElementIds[selected[1]][selected[0]]= elementInfo.get(mapElementIds[selected[1]][selected[0]]).getCanBeModifiedTo();
                mapIdsUpdated =true;
                resorcesUpdated =true;
            }
        }
    }

    /**
     * Ez a függvény egy adott elemet mozgat a pályán a kijelölt helyre,
     * ha van mozgatásra kijelölt elem.
     * @return Sikeres volt-e a mozgatás.
     */
    public boolean move(){
        if(mapElementIds[selected[1]][selected[0]]==0){
            mapElementIds[selected[1]][selected[0]]= mapElementIds[elementToBeMoved[1]][elementToBeMoved[0]];
            mapElementIds[elementToBeMoved[1]][elementToBeMoved[0]]=0;
            elementToBeMoved[0]=999;
            elementToBeMoved[1]=999;
            mapIdsUpdated =true;
            return true;
        }
        return false;
    }

    /**
     * Ez a függvény egy adott elemet épít a kijelölt helyre.
     * Építeni csak az üres(fű,Id:0) mezőre lehet.
     * Amennyiben a kijelölt mező üres és a játékosnak van elegendő erőforrása,
     * akkor elveszi a szükséges erőforrásokat és a kijelölt mezőt átállítja az építendő elemre.
     * @param elementId Az építendő elem idje.
     */
    public void build(int elementId){
        if(selected[0]!=999 && selected[1]!=999){
            if(mapElementIds[selected[1]][selected[0]]==0){
                boolean canBeBought=true;
                for (int i=0;i<4;i++){
                    if(resources[i]< elementInfo.get(elementId).getPrice().get(i)){
                        canBeBought=false;
                    }
                }
                if(canBeBought){
                    for (int i=0;i<4;i++){
                        resources[i]-= elementInfo.get(elementId).getPrice().get(i);
                    }
                    mapElementIds[selected[1]][selected[0]]=elementId;
                    mapIdsUpdated =true;
                    resorcesUpdated =true;
                }
            }
        }
    }

    /**
     * Getter a MapIdsUpdated-hez.
     * @return Visszaadja, hogy a map idjei frissültek-e.
     */
    public boolean isMapIdsUpdated() {
        return mapIdsUpdated;
    }

    /**
     * Setter a mapIdsUpdated-hez.
     * @param mapIdsUpdated Erre az értékre allítja a MapIdsUpdate-et
     */
    public void setMapIdsUpdated(boolean mapIdsUpdated) {
        this.mapIdsUpdated = mapIdsUpdated;
    }

    /**
     * Getter a MapElemetIds-hez.
     * @return Egy kétdimenziós tömb, ami tartalmazza az összes elem idjét.
     */
    public int[][] getMapElementIds() {
        return mapElementIds;
    }

    /**
     * Getter a MapElementIds-hez.
     * @param i Első index
     * @param j Második index
     * @return Egy adott elem idjét a MapElemetIds-ből, amit az indexek jelölnek ki.
     */
    public int getMap_Element_id(int i,int j){
        return mapElementIds[i][j];
    }

    /**
     * Getter a Selected-hez.
     * @return Egy tömb, ami tartalmazza a kijelölt mező indexeit.
     */
    public int[] getSelected() {
        return selected;
    }

    /**
     * Getter a Resources-hez.
     * @return Egy tömb, ami tartalmazza a az erőforrásokat(arany,fa,kő,étel).
     */
    public int[] getResources() {
        return resources;
    }

    /**
     * Setter a MapElementIds-hez
     * @param mapElementIds Kétdimeziós tömb, amiben idk vannak.
     */
    public void setMapElementIds(int[][] mapElementIds) {
        this.mapElementIds = mapElementIds;
    }

    /**
     * Setter a MapElementIds-hez.
     * @param i Első index.
     * @param j Második index.
     * @param value Egy érték amire az index álltal kijelölt változó értékét változtatjuk.
     */
    public void setMap_Element_id(int i, int j,int value){
        mapElementIds[i][j]=value;
    }

    /**
     * Setter a resources-hez.
     * @param resources Egy tömb, ami az új erőforrás mennyiségeket tartalmazza.
     */
    public void setResources(int[] resources) {
        this.resources = resources;
    }

    /**
     * Getter a resourcesUpdated-hez.
     * @return Frissültek-e az erőforrás mennyiségek.
     */
    public boolean isResorcesUpdated() {
        return resorcesUpdated;
    }

    /**
     * Setter a resourcesUpdated-hez
     * @param resorcesUpdated Erre az értékre állítja a resourcesUpdated-et.
     */
    public void setResorcesUpdated(boolean resorcesUpdated) {
        this.resorcesUpdated = resorcesUpdated;
    }

    /**
     * Getter a ElementToBeMoved-hoz.
     * @return Egy tömb ami tartalmazza a mozgatásra kijelölt elem indexeit.
     */
    public int[] getElementToBeMoved() {
        return elementToBeMoved;
    }

    /**
     * Getter a ElementInfos-hoz.
     * @return Egy hasmap amiben az elementInfok vannak eltárolva.
     */
    public HashMap<Integer, Elementinfo> getElementInfo() {
        return elementInfo;
    }

    /**
     * Setter a ElementToBeMoved-hoz.
     * A kijelölt elemet mozgatásra kijelölt elemnek állítja át.
     */
    public void setElementToBeMoved() {
        if(selected[0]!=999 && selected[1]!=999 && elementToBeMoved[0]==999 && elementToBeMoved[1]==999){
            elementToBeMoved[0]=selected[0];
            elementToBeMoved[1]=selected[1];
        }
    }

    /**
     * Getter a UpdateTasks-hez.
     * @return Egy arraylist, amiben az update task-ok vannak.
     */
    public ArrayList<UpdateTask> getUpdateTasks() {
        return updateTasks;
    }

    /**
     * Ez a függvény betölti az elementInfo-kat egy fájlból(ElementInfo.init).
     * Ezekból a betöltött adatokból egy hasmap-ot épít fel.
     * Ha a fájl nem található a program leáll 999-es kóddal.
     */
    private void loadElemetInfo(){
        File initFile=new File("init/ElementInfo.init");
        try {
            String in;
            String[] inSplit;
            String[] inValue;
            String[] inPrice;
            ArrayList<Integer> price;
            ArrayList<Integer> values;
            boolean harvestAble=false;
            boolean buildAble=false;
            Scanner scanner=new Scanner(initFile);
            while (scanner.hasNextLine()){
                in=scanner.nextLine();
                inSplit=in.split(" ");
                inPrice=inSplit[4].split(",");
                price=new ArrayList<>();
                for(int i=0; i<inPrice.length;i++){
                    price.add(Integer.parseInt(inPrice[i]));
                }
                values=new ArrayList<>();
                inValue=inSplit[5].split(",");
                for (int i=0;i<inValue.length;i++){
                    values.add(Integer.parseInt(inValue[i]));
                }
                harvestAble=false;
                buildAble=false;
                if(inSplit[3].equals("t")){
                    harvestAble=true;
                }
                if(inSplit[6].equals("t")){
                    buildAble=true;
                }
                elementInfo.put(Integer.parseInt(inSplit[0]),new Elementinfo(Integer.parseInt(inSplit[2]),Integer.parseInt(inSplit[1]),harvestAble,price,values,buildAble));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Init fájl hiányzik!");
            System.exit(999);
        }
    }

    /**
     * Ez a függvény az elementIfo-kból generál updateTask-okat.
     * Ha egy elemnek van frissítési ideje, akkor készül hozzá egy updateTask is.
     */
    private void createUpdateTasks(){
        for (Integer i: elementInfo.keySet()) {
            if(elementInfo.get(i).getUpdateTime()!=0){
                updateTasks.add(new UpdateTask(this, elementInfo.get(i),i));
            }
        }

    }
}
