//Made by Benjamin Wu

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class ControlPanel extends JPanel implements Runnable {

    // CONSTANTS
    static final double SPAWN_PER_SECOND = 0.3;
    static final int POWER_UP_DROP_RATE = 250;    // Probability out of 1000
    static final int RECRUIT_RATE = 50;           // Probability out of 1000
    static final Color TRANSPARENT = new Color(0,0,0,0);
    static final Color TEXT = new Color(255, 203, 5);
    static final Color TEXT_BORDER = new Color(42, 117, 187);
    static final Color BACKGROUND_TINT = new Color(0,0,0,150);
    static final int SCORE_FOR_BOSS_KILL = 200;
    static final int SCORE_FOR_ENEMY_KILL = 200;
    static final int FRAME_RATE = 30;
    static final int BOSS_SCALE = 5;
    static final int ENEMY_SCALE = 3;
    static final int PLAYER_SCALE = 4;
    static final int PLAYER_HEALTH_COEF = 3;
    static final int BOSS_HEALTH_COEF = 10;
    static final int MENU_DELAY_TIME = 150;
    static final int MIN_DAMAGE = 1;

    private static ArrayList<PowerUp> powerUps = new ArrayList<>();
    static ArrayList<PowerUp> powerUpsToAdd = new ArrayList<>();
    static ArrayList<PowerUp> powerUpsToRemove = new ArrayList<>();
    private static ArrayList<Flash> flashes = new ArrayList<>();
    static ArrayList<Flash> flashesToAdd = new ArrayList<>();
    static ArrayList<Flash> flashesToRemove = new ArrayList<>();
    private static ArrayList<HealthBar> healthBars = new ArrayList<>();
    static ArrayList<HealthBar> healthBarsToAdd = new ArrayList<>();
    static ArrayList<HealthBar> healthBarsToRemove = new ArrayList<>();
    private static ArrayList<Enemy> enemies = new ArrayList<>();
    static ArrayList<Enemy> enemiesToAdd = new ArrayList<>();
    static ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    private static ArrayList<LocationEncounterDisplay> encounterDisplays = new ArrayList<>();
    static ArrayList<LocationEncounterDisplay> encounterDisplaysToAdd = new ArrayList<>();
    static ArrayList<LocationEncounterDisplay> encounterDisplaysToRemove = new ArrayList<>();
    private static ArrayList<Projectile> playerProjectiles = new ArrayList<>();
    static ArrayList<Projectile> playerProjectilesToAdd = new ArrayList<>();
    static ArrayList<Projectile> playerProjectilesToRemove = new ArrayList<>();
    private static ArrayList<Projectile> enemyProjectiles = new ArrayList<>();
    static ArrayList<Projectile> enemyProjectilesToAdd = new ArrayList<>();
    static ArrayList<Projectile> enemyProjectilesToRemove = new ArrayList<>();
    private static ArrayList<HUD> menus = new ArrayList<>();
    static ArrayList<HUD> menusToAdd = new ArrayList<>();
    static ArrayList<HUD> menusToRemove = new ArrayList<>();
    static NewRecruitNotice recruitNotice;
    private static BossApproachNotice bossNotice;

    private static boolean run = false;

    static Input input = new Input();
    static int width;
    static int height;

    static Random rand = new Random();
    private static int score = 0;
    private static int bombs = 3;
    private static int power = 1;
    static boolean[] unlockedPokemon = new boolean[Pokemon.values().length]; // Unlocking Pokemon to be added with main menu
    static boolean[] unlockedLocation = new boolean[Location.values().length]; // Unlocking Locations to be added with main menu
    static int[] highScores = new int[Location.values().length]; // High scores to be added with location selection

    private static JFrame frame = new JFrame("Pokémon Space Shooter");
    private static File fontFile;

    static Player player = null;
    static Location location = Location.values()[0];
    static Boss boss;
    static Background background = null;

    private boolean bossFight;
    static boolean dead = false;
    static boolean win = false;

    // Adds listeners
    public ControlPanel() {
        this.addKeyListener(input);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        bossFight = false;
    }

    private void start() {
        Thread thread = new Thread(this);
        run = true;
        thread.start();
    }

    // Gives other classes access to Pokemon font resource
    static File getFontFile() {
        return  fontFile;
    }

    private static void setUp(ControlPanel control) {
        try {
            loadSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
        menus.add(new TitleHUD(control));
        recruitNotice = new NewRecruitNotice();
        bossNotice = new BossApproachNotice(control);
    }

    static void resetItems() {
        score = 0;
        bombs = 3;
        power = 1;
    }

    void incrementScore(int increase) {
        score += increase;
        score = Math.min(score, 999999);
    }

    void incrementPower() {
        power++;
        power = Math.min(5, power);
    }

    int getScore() {
        return score;
    }

    int getPower() {
        return power;
    }

    int getBombs() {
        return bombs;
    }

    void incrementBombs() {
        bombs++;
        bombs = Math.min(bombs, PowerUp.MAX_BOMB);
    }

    void decrementBombs() {
        bombs--;
        bombs = Math.max(bombs, 0);
    }

    public void run() {
        while (run) {
            // Check for collision, draw objects and sleep
            try {
                for (Flash f : flashes) {
                    f.update(this);
                }
                for (PowerUp p : powerUps) {
                    p.update(this);
                }
                if (background != null) {
                    background.update(this);
                }
                if (player != null) {
                    player.update(this);
                }
                for (Enemy e : enemies) {
                    e.update(this);
                }
                for (HealthBar h : healthBars) {
                    h.update(this);
                }
                for (Projectile p : playerProjectiles) {
                    p.update(this);
                }
                for (Projectile p : enemyProjectiles) {
                    p.update(this);
                }
                for (HUD h : menus) {
                    h.update(this);
                }
                for (LocationEncounterDisplay d : encounterDisplays) {
                    d.update(this);
                }
                ArrayList<LocationEncounterDisplay> encounterDisplaysToRemoveCopy = new ArrayList<>(encounterDisplaysToRemove);
                for (LocationEncounterDisplay d : encounterDisplaysToRemoveCopy) {
                    d.getTimer().purge();
                    d.getTimer().cancel();
                }
                ArrayList<Flash> flashesToRemoveCopy = new ArrayList<>(flashesToRemove);
                for (Flash f : flashesToRemoveCopy) {
                    f.getTimer().purge();
                    f.getTimer().cancel();
                }
                ArrayList<PowerUp> powerUpsToRemoveCopy = new ArrayList<>(powerUpsToRemove);
                for (PowerUp p : powerUpsToRemoveCopy) {
                    p.getTimer().purge();
                    p.getTimer().cancel();
                }
                ArrayList<HUD> menusToRemoveCopy = new ArrayList<>(menusToRemove);
                for (HUD h : menusToRemoveCopy) {
                    h.getTimer().purge();
                    h.getTimer().cancel();
                }
                ArrayList<Enemy> enemiesToRemoveCopy = new ArrayList<>(enemiesToRemove);
                for (Enemy e : enemiesToRemoveCopy) {
                    e.getTimer().purge();
                    e.getTimer().cancel();
                }
                ArrayList<Projectile> playerProjectilesToRemoveCopy = new ArrayList<>(playerProjectilesToRemove);
                for (Projectile p : playerProjectilesToRemoveCopy) {
                    p.getTimer().purge();
                    p.getTimer().cancel();
                }
                ArrayList<Projectile> enemyProjectilesToRemoveCopy = new ArrayList<>(enemyProjectilesToRemove);
                for (Projectile p : enemyProjectilesToRemoveCopy) {
                    p.getTimer().purge();
                    p.getTimer().cancel();
                }
                recruitNotice.update(this);
                repaint();
                try {
                    Thread.sleep(1000 / FRAME_RATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                flashes.addAll(flashesToAdd);
                powerUps.addAll(powerUpsToAdd);
                enemies.addAll(enemiesToAdd);
                playerProjectiles.addAll(playerProjectilesToAdd);
                enemyProjectiles.addAll(enemyProjectilesToAdd);
                menus.addAll(menusToAdd);
                healthBars.addAll(healthBarsToAdd);
                encounterDisplays.addAll(encounterDisplaysToAdd);

                PlayingHUD.decrementNumRemaining(enemiesToRemove.size());

                flashes.removeAll(flashesToRemove);
                flashesToRemove.clear();
                flashesToAdd.clear();
                powerUps.removeAll(powerUpsToRemove);
                powerUpsToRemove.clear();
                powerUpsToAdd.clear();
                healthBars.removeAll(healthBarsToRemove);
                healthBarsToRemove.clear();
                healthBarsToAdd.clear();
                enemies.removeAll(enemiesToRemove);
                enemiesToRemove.clear();
                enemiesToAdd.clear();
                playerProjectiles.removeAll(playerProjectilesToRemove);
                playerProjectilesToRemove.clear();
                playerProjectilesToAdd.clear();
                enemyProjectiles.removeAll(enemyProjectilesToRemove);
                enemyProjectilesToRemove.clear();
                enemyProjectilesToAdd.clear();
                menus.removeAll(menusToRemove);
                menusToAdd.clear();
                menusToRemove.clear();
                encounterDisplays.removeAll(encounterDisplaysToRemove);
                encounterDisplaysToAdd.clear();
                encounterDisplaysToRemove.clear();
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
    }

     public void paintComponent(Graphics g) throws ConcurrentModificationException {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
         if (background != null) {
             background.paintComponent(g2);
         }
         ArrayList<Flash> flashesCopy = new ArrayList<>(flashes);
         for (Flash f : flashesCopy) {
             f.paintComponent(g2);
         }
         ArrayList<HealthBar> healthBarsCopy = new ArrayList<>(healthBars);
         for (HealthBar h : healthBarsCopy) {
            h.paintComponent(g2);
        }
         ArrayList<PowerUp> powerUpsCopy = new ArrayList<>(powerUps);
         for (PowerUp p : powerUpsCopy) {
             p.paintComponent(g2);
         }
         if (player != null) {
             player.paintComponent(g2);
         }
         ArrayList<Enemy> enemiesCopy = new ArrayList<>(enemies);
         for (Enemy e : enemiesCopy) {
             e.paintComponent(g2);
         }
         ArrayList<Projectile> playerProjectilesCopy = new ArrayList<>(playerProjectiles);
         for (Projectile p : playerProjectilesCopy) {
            p.paintComponent(g2);
        }
         ArrayList<Projectile> enemyProjectilesCopy = new ArrayList<>(enemyProjectiles);
         for (Projectile p : enemyProjectilesCopy) {
            p.paintComponent(g2);
        }
         ArrayList<HUD> menusCopy = new ArrayList<>(menus);
         for (HUD h : menusCopy) {
            h.paintComponent(g2);
        }
        ArrayList<LocationEncounterDisplay> encounterDisplaysCopy = new ArrayList<>(encounterDisplays);
        for (LocationEncounterDisplay d : encounterDisplaysCopy) {
            d.paintComponent(g2);
        }
        recruitNotice.paintComponent(g2);
        bossNotice.paintComponent(g2);
    }

    static void clear() {
        powerUpsToRemove.addAll(powerUps);
        powerUpsToAdd.clear();
        flashesToRemove.addAll(flashes);
        flashesToAdd.clear();
        background = null;
        healthBarsToRemove.addAll(healthBars);
        healthBarsToAdd.clear();
        enemiesToRemove.addAll(enemies);
        enemiesToAdd.clear();
        playerProjectilesToRemove.addAll(playerProjectiles);
        playerProjectilesToAdd.clear();
        enemyProjectilesToRemove.addAll(enemyProjectiles);
        enemyProjectilesToAdd.clear();
        player.getTimer().purge();
        player.getTimer().cancel();
        player = null;
    }

    static void clearLevelEncounterDisplay() {
        encounterDisplaysToRemove.addAll(encounterDisplays);
        encounterDisplaysToAdd.clear();
    }

    static int numEnemies() {
        return enemies.size();
    }

    static ArrayList<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    boolean getBossFight() {
        return bossFight;
    }

    void setBossFight(boolean set) {
        bossFight = set;
    }

    // Loads Pokemon font resource
    private static void loadFont() {
        try {
            URL resource = Pokemon.class.getResource("/Resources/Pokemon Solid.ttf");
            fontFile = new File(resource.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reads save data (Will be useful when Pokemon are checked if unlocked and progress is tracked)
    private static void loadSave() throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader((new FileReader("SaveData.txt")));
            String[] line = in.readLine().split(",");
            for (int i = 0; i < unlockedPokemon.length; i++) {
                unlockedPokemon[i] = Boolean.parseBoolean(line[i]);
            }
            line = in.readLine().split(",");
            for (int i = 0; i < unlockedLocation.length; i++) {
                unlockedLocation[i] = Boolean.parseBoolean(line[i]);
            }
            line = in.readLine().split(",");
            for (int i = 0; i < highScores.length; i++) {
                highScores[i] = Integer.parseInt(line[i]);
            }
        } catch (Exception e) {
            unlockedLocation[0] = true;
            save();
        } finally {
            unlockedLocation[0] = true;
            save();
            if (in != null) {
                // Closes reader
                in.close();
            }
        }
    }

    static void save() throws IOException {
        // Records progress
        if (score > highScores[location.getLevelIndex()]) {
            highScores[location.getLevelIndex()] = score;
        }
        FileWriter out = null;
        try {
            out = new FileWriter("SaveData.txt");
            for (int i = 0; i < unlockedPokemon.length; i++) {
                out.write(String.valueOf(unlockedPokemon[i]));
                if (i < unlockedPokemon.length - 1) {
                    out.write(",");
                } else {
                    out.write("\r\n");
                }
            }
            for (int i = 0; i < unlockedLocation.length; i++) {
                out.write(String.valueOf(unlockedLocation[i]));
                if (i < unlockedLocation.length - 1) {
                    out.write(",");
                } else {
                    out.write("\r\n");
                }
            }
            for (int i = 0; i < highScores.length; i++) {
                out.write(String.valueOf(highScores[i]));
                if (i < highScores.length - 1) {
                    out.write(",");
                } else {
                    out.write("\r\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                // Closes writer
                out.close();
            }
        }
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        // Dynamically sizes board
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        height = dim.height- 100;
        width = dim.width;
        ControlPanel.loadFont();
        ControlPanel control = new ControlPanel();
        setUp(control);
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        control.setPreferredSize(new Dimension(width, height));
        control.setFocusable(true);
        control.setDoubleBuffered(true);
        frame.setBackground(Color.BLACK);
        frame.add(control);
        frame.pack();
        control.start();
        frame.addKeyListener(input);
        frame.addMouseListener(input);
        frame.addMouseMotionListener(input);
        //frame.setAlwaysOnTop(true);
    }
}

class Input implements KeyListener, MouseListener, MouseMotionListener {

    private boolean[] keys = new boolean[256];
    private boolean[] buttons = new boolean[4];
    private boolean mouseOnPanel = false;
    public int x, y;
    boolean mouseMoved = false;

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        // Prioritises keyboard over mouse control if a key is pressed
        mouseMoved = false;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    public void keyTyped(KeyEvent e) {
        // System.out.println(e.getKeyCode());

    }

    public void mouseClicked(MouseEvent e) {
        // System.out.print(e.getButton());
        // System.out.println("X: " + e.getX());
        // System.out.println("Y: " + e.getY());
    }

    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    public void mouseEntered(MouseEvent e) {
        mouseOnPanel = true;
    }

    boolean keyUsed() {
        // Checks if any key is pressed
        for (int i = 0; i < keys.length - 1; i++) {
            if (keys[i]) {
                return true;
            }
        }
        return false;
    }

    public void mouseMoved(MouseEvent e) {
        // Determines if the mouse has moved from a previous position
        if (x != e.getX() || y != e.getY()) {
            mouseMoved = true;
        }
        x = e.getX();
        y = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void mouseExited(MouseEvent e) {
        mouseOnPanel = false;
    }

    boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    boolean isButtonDown(int buttonCode) {
        return buttons[buttonCode];
    }

    // Checks if the mouse is on screen
    boolean isMouseOn() {
        return mouseOnPanel;
    }
}
