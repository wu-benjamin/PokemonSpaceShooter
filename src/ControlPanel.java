import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;

// Currently ~ 2840 lines of code
public class ControlPanel extends JPanel implements Runnable {

    // CONSTANTS
    static final double SPAWN_PER_SECOND = 0.3;
    static final int POWER_UP_DROP_RATE = 250;    // Probability out of 1000
    static final int RECRUIT_RATE = 50;           // Probability out of 1000
    static final Color TRANSPARENT = new Color(0,0,0,0);
    static final Color TEXT_BACKGROUND = new Color(92, 167, 237, 150);
    static final Color TEXT = new Color(255, 203, 5);
    static final Color TEXT_BORDER = new Color(42, 117, 187);
    static final Color BACKGROUND_TINT = new Color(0,0,0,150);
    static final int SCORE_FOR_BOSS_KILL = 200;
    static final int SCORE_FOR_ENEMY_KILL = 200;
    static final int FRAME_RATE = 30;
    static final int BOSS_SCALE = 7;
    static final int ENEMY_SCALE = 3;
    static final int PLAYER_SCALE = 4;
    static final int PLAYER_HEALTH_COEF = 3;
    static final int BOSS_HEALTH_COEF = 15;
    static final int MENU_DELAY_TIME = 150;
    static final int MIN_DAMAGE = 1;

    private static ArrayList<GameObject> objects = new ArrayList<>(); // Contains flashes, backgrounds, and power-ups
    static ArrayList<GameObject> toAdd = new ArrayList<>();
    static ArrayList<GameObject> toRemove = new ArrayList<>();
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
        recruitNotice = new NewRecruitNotice(0,0, 1,1, TRANSPARENT);
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
        bombs = Math.min(bombs, 99);
    }

    void decrementBombs() {
        bombs--;
        bombs = Math.max(bombs, 0);
    }

    public void run() {
        while (run) {
            // Check for collision, draw objects and sleep
            try {
                for (GameObject i : objects) {
                    i.update(this);
                }
                if (player != null) {
                    player.update(this);
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
                recruitNotice.update(this);
                repaint();
                try {
                    Thread.sleep(1000 / FRAME_RATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                objects.addAll(toAdd);
                enemies.addAll(enemiesToAdd);
                playerProjectiles.addAll(playerProjectilesToAdd);
                enemyProjectiles.addAll(enemyProjectilesToAdd);
                menus.addAll(menusToAdd);
                healthBars.addAll(healthBarsToAdd);
                encounterDisplays.addAll(encounterDisplaysToAdd);
                PlayingHUD.decrementNumRemaining(enemiesToRemove.size());
                // Making changes to objects ArrayList at once prevents ConcurrentModificationException
                objects.removeAll(toRemove);
                toRemove.clear();
                toAdd.clear();
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
        try {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            for (GameObject i : objects) {
                i.paintComponent(g2);
            }
            if (player != null) {
                player.paintComponent(g2);
            }
            for (HealthBar h : healthBars) {
                h.paintComponent(g2);
            }
            for (Projectile p : playerProjectiles) {
                p.paintComponent(g2);
            }
            for (Projectile p : enemyProjectiles) {
                p.paintComponent(g2);
            }
            for (HUD h : menus) {
                h.paintComponent(g2);
            }
            for (LocationEncounterDisplay d : encounterDisplays) {
                d.paintComponent(g2);
            }
            recruitNotice.paintComponent(g2);
        } catch (ConcurrentModificationException e) {
            // e.printStackTrace();
        }
    }

    static void clear() {
        objects.clear();
        toAdd.clear();
        toRemove.clear();
        healthBars.clear();
        healthBarsToRemove.clear();
        healthBarsToAdd.clear();
        enemies.clear();
        enemiesToAdd.clear();
        enemiesToRemove.clear();
        playerProjectiles.clear();
        playerProjectilesToAdd.clear();
        playerProjectilesToRemove.clear();
        enemyProjectiles.clear();
        enemyProjectilesToAdd.clear();
        enemyProjectilesToRemove.clear();
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
            // Creates new save file if no save file currently exists or is corrupted
            unlockedLocation[0] = true;
            save();
        } finally {
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
