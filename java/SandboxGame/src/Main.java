import Game.*;
import Render.*;

import java.util.Timer;

/**
 * Main osztály a programban.
 * fix
 */
public class Main {
    public static Game game;
    public static Timer timer;

    /**
     * Main függvény, ez a program belépési pontja.
     * Létrehozza és elindítja a szálakat.
     * Létrehozza az informaciókat tartalmazó Game objektumot.
     */
    public static void main(String[] args) {
        game=new Game();
        timer=new Timer();
        for (UpdateTask ut: game.getUpdateTasks()) {
            timer.scheduleAtFixedRate(ut,0,ut.getUpdateTime());
        }
        Renderer renderer=new Renderer(game,timer);
        renderer.start();
    }
}