import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class ControlPanel extends JPanel implements Runnable {

    static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    static ArrayList<GameObject> toAdd = new ArrayList<GameObject>();
    static ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
    static boolean[] unlockedPokemon = new boolean[Pokemon.values().length]; // Unlocking Pokemon to be added with main menu
    static boolean[] unlockedLocation = new boolean[Location.values().length]; // Unlocking Locations to be added with main menu
    static int[] highScores = new int[Location.values().length]; // High scores to be added with location selection
    private static boolean run = false;
    protected static Input input = new Input();
    static int width;
    static int height;
    static Random rand = new Random();
    private int score = 0;
    private int bombs = 3;
    private int power = 1;
    // Probability out of 1000
    public static final int POWER_UP_DROP_RATE = 500;
    public static final int RECRUIT_RATE = 25;
    public static final Color TRANSPARENT = new Color(0,0,0,0);
    public static final Color TEXT_BACKGROUND = new Color(42, 117, 187, 200);
    public static final Color TEXT = new Color(255, 203, 5);
    private static JFrame frame = new JFrame("Pokemon Space Shooter");
    private ControlPanel control;
    private static File fontFile;
    private int maxNumberOfEnemies = 25; // Temporary
    private int currentNumberOfEnemies;
    private boolean bossFight;
    static Enemy boss;
    static int FRAME_RATE = 30;

    static Pokemon player;

    // Adds listeners
    public ControlPanel() {
        this.addKeyListener(input);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        this.control = this;
        bossFight = false;
    }

    public void start() {
        Thread thread = new Thread(this);
        run = true;
        thread.start();
    }

    public static void stop() {
        objects.clear();
        run = false;
    }

    public void incrementScore(int increase) {
        score += increase;
    }

    public int getScore() {
        return score;
    }

    public void incrementPower() {
        power++;
        power = Math.min(5, power);
    }

    public int getPower() {
        return power;
    }

    public int getBombs() {
        return bombs;
    }

    public void incrementBombs() {
        bombs++;
    }

    public void decrementBombs() {
        bombs--;
    }

    public void run() {
        while (run) {
            // Check for collision, draw objects and sleep
            try {
                for (GameObject i : objects) {
                    i.update(this);
                    repaint();
                }
                try {
                    Thread.sleep(1000 / FRAME_RATE);
                } catch (Exception e) {
                }
                // Randomly generates enemies
                int i = rand.nextInt(151);
                if (currentNumberOfEnemies < maxNumberOfEnemies && Enemy.enemies.size() <= 5) {
                    if (rand.nextInt(1000) < 15) {
                        toAdd.add(new Enemy(rand.nextInt(width - Pokemon.values()[i].getWidth() * 3), -200, Pokemon.values()[i].getWidth() * 3, Pokemon.values()[i].getHeight() * 3, TRANSPARENT, Pokemon.values()[i], this, false));
                        currentNumberOfEnemies++;
                    }
                    // Randomly generates a boss at the end of the level
                } else if (currentNumberOfEnemies == maxNumberOfEnemies && Enemy.enemies.size() == 0) {
                    Background.setMove(false);
                    this.boss = new Enemy(width / 2 - Pokemon.values()[i].getWidth() * 9 / 2, -700, Pokemon.values()[i].getWidth() * 9, Pokemon.values()[i].getHeight() * 9, TRANSPARENT, Pokemon.values()[i], this, true);
                    toAdd.add(boss);
                    currentNumberOfEnemies++;
                    bossFight = true;
                    Player.setBossWall(boss.getHeight() + 20);
                }
                for (GameObject j : toAdd) {
                    objects.add(j);
                }
                // Making changes to objects ArrayList at once prevents ConcurrentModificationException
                objects.removeAll(toRemove);
                toRemove.clear();
                toAdd.clear();
            } catch (ConcurrentModificationException e) {
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
        } catch (ConcurrentModificationException e) {

        }
    }

    public boolean getBossFight() {
        return bossFight;
    }

    public void setBossFight(boolean set) {
        bossFight = set;
    }

    public static void main(String[] args) {
        // Dynamically sizes board
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        height = dim.height - 100;
        width = dim.width - 100;
        ControlPanel.loadFont();
        ControlPanel control = new ControlPanel();
        setUp(control);
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        control.setPreferredSize(new Dimension(width, height));
        control.setFocusable(true);
        frame.setBackground(Color.BLACK);
        frame.add(control);
        frame.pack();
        control.start();
        frame.addKeyListener(input);
        frame.addMouseListener(input);
        frame.addMouseMotionListener(input);
    }

    // Loads Pokemon font resource
    public static void loadFont() {
        try {
            URL resource = Pokemon.class.getResource("/Resources/Pokemon Solid.ttf");
            fontFile = new File(resource.toURI());
        } catch (Exception e) {
        }
    }

    // Gives other classes access to Pokemon font resource
    public static File getFontFile() {
        return  fontFile;
    }

    private static void setUp(ControlPanel control) {
        // Beings looping music (Context sensitive music to be implemented at a later date)
        /*try {
            URL resource = Pokemon.class.getResource("/Resources/Sound/102 - palette town theme.4.wav");
            AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(new File(resource.toURI()));
            Clip music = javax.sound.sampled.AudioSystem.getClip();
            music.open(audioIn);
            music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }*/
        // Chooses a random Pokemon as the player character
        int random = rand.nextInt(151);
        player = Pokemon.values()[random];
        // Add background first so that it is bottom
        objects.add(new Background(0, -height, width + 25, height, TRANSPARENT,"Lake", control, true));
        objects.add(new Background(0, 0, width + 25, height, TRANSPARENT, "Lake", control, true));
        objects.add(new Player(width / 2, height / 2, player.getWidth() * 4, player.getHeight() * 4, TRANSPARENT, player, control));
        objects.add(new Counter(0, 0, 350, 70, TEXT, "Score: ", control));
        objects.add(new Counter(0, height - 60, 350, 70, TEXT, "Z-Move: ", control));
        objects.add(new Counter(width - 250, height - 60, 250, 70, TEXT, "Level: ", control));
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
        } catch (FileNotFoundException e) {
            // Creates new save file if no save file currently exists
            unlockedLocation[0] = true;
            save();
        } finally {
            if (in != null) {
                // Closes reader
                in.close();
            }
        }
    }

    private static void save() throws IOException {
        // Records progress
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

        } finally {
            if (out != null) {
                // Closes writer
                out.close();
            }
        }
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

    public boolean keyUsed() {
        // Checks if any key is pressed
        for (int i = 0; i < keys.length - 1; i ++) {
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

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    public boolean isButtonDown(int buttonCode) {
        return buttons[buttonCode];
    }

    // Checks if the mouse is on screen
    public boolean isMouseOn() {
        return mouseOnPanel;
    }
}
