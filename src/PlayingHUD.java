import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingHUD extends HUD {

    private static int numSpawned = 0;
    private static int maxSpawn;
    private static int numRemaining;
    private static boolean longTimeNoSpawn = false;
    private static boolean justSpawned = false;
    private static final int MAX_TIME_NO_SPAWN = 7000; // time in ms
    private static final int MIN_TIME_NO_SPAWN = 1000; // time in ms
    private Timer spawnTimer = new Timer();

    PlayingHUD(ControlPanel control) {
        super(control);
        /*
        // Beings looping music (Context sensitive music to be implemented at a later date)
        try {
            URL resource = Pokemon.class.getResource("/Resources/Sound/102 - palette town theme.4.wav");
            AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(new File(resource.toURI()));
            Clip music = javax.sound.sampled.AudioSystem.getClip();
            music.open(audioIn);
            music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        // Add background first so that it is bottom
        ControlPanel.resetItems();
        maxSpawn = ControlPanel.location.getNumberOfEnemies();
        ControlPanel.toAdd.add(new Background(0, -ControlPanel.height, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT,ControlPanel.location.getBackground(), control, true));
        ControlPanel.toAdd.add(new Background(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, ControlPanel.location.getBackground(), control, true));
        numRemaining = maxSpawn;
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
        LongNoSpawnTask longNoSpawnTask = new LongNoSpawnTask();
        spawnTimer.schedule(longNoSpawnTask, MAX_TIME_NO_SPAWN);
        JustSpawnTask justSpawnTask = new JustSpawnTask();
        spawnTimer.schedule(justSpawnTask, MIN_TIME_NO_SPAWN);
    }

    static void decrementNumRemaining(int decrement) {
        numRemaining -= decrement;
    }


    public void paintComponent(Graphics2D g2) {
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawBorderedString(g2, "Score: " + control.getScore(), 20, 70);
        drawBorderedString(g2, "Z-Move: " + control.getBombs(), 20, ControlPanel.height - 25);
        drawBorderedString(g2,"Level: " + control.getPower(), ControlPanel.width - 250, ControlPanel.height - 25);
        drawBorderedString(g2, numRemaining + " Enemies Remain", ControlPanel.width - 480, 70);
    }

    public void update(ControlPanel control) {
        if (!delay) {
            if (ControlPanel.win && ControlPanel.recruitNotice.size() == 0) {
                ControlPanel.menusToAdd.add(new WinHUD(control));
                ControlPanel.menusToRemove.add(this);
                ControlPanel.clear();
                return;
            }
            if (ControlPanel.dead && ControlPanel.recruitNotice.size() == 0) {
                ControlPanel.menusToAdd.add(new DeadHUD(control));
                ControlPanel.menusToRemove.add(this);
                ControlPanel.clear();
                return;
            }
            if (numSpawned < maxSpawn) {
                if ((ControlPanel.rand.nextInt((int) (ControlPanel.FRAME_RATE / ControlPanel.SPAWN_PER_SECOND)) == 0 || longTimeNoSpawn) && !justSpawned) {
                    if (numSpawned < maxSpawn - 1) {
                        Pokemon enemy = ControlPanel.location.getEnemies()[ControlPanel.rand.nextInt(ControlPanel.location.getEnemies().length)];
                        ControlPanel.toAdd.add(new Enemy(ControlPanel.rand.nextInt(ControlPanel.width - enemy.getWidth() * ControlPanel.ENEMY_SCALE),
                                -enemy.getHeight() * ControlPanel.ENEMY_SCALE - 5, enemy.getWidth() * ControlPanel.ENEMY_SCALE,
                                enemy.getHeight() * ControlPanel.ENEMY_SCALE, ControlPanel.TRANSPARENT, enemy, control));
                        numSpawned++;
                    }
                    LongNoSpawnTask longNoSpawnTask = new LongNoSpawnTask();
                    JustSpawnTask justSpawnTask = new JustSpawnTask();
                    spawnTimer.cancel();
                    spawnTimer.purge();
                    Timer spawnTimer = new Timer();
                    spawnTimer.schedule(longNoSpawnTask, MAX_TIME_NO_SPAWN);
                    spawnTimer.schedule(justSpawnTask, MIN_TIME_NO_SPAWN);
                    longTimeNoSpawn = false;
                    justSpawned = true;
                }
                if (ControlPanel.numEnemies() == 0 && numSpawned == maxSpawn - 1) {
                    control.setBossFight(true);
                    Pokemon bossPokemon = ControlPanel.location.getBoss();
                    Player.setBossWall(bossPokemon.getHeight() * ControlPanel.BOSS_SCALE + 40);
                    Boss boss = new Boss(ControlPanel.width / 2 - bossPokemon.getWidth() * ControlPanel.BOSS_SCALE / 2,
                            -bossPokemon.getHeight() * ControlPanel.BOSS_SCALE - 100, bossPokemon.getWidth() * ControlPanel.BOSS_SCALE,
                            bossPokemon.getHeight() * ControlPanel.BOSS_SCALE, ControlPanel.TRANSPARENT, bossPokemon, control);
                    ControlPanel.toAdd.add(boss);
                }
            }
        }
    }

    class LongNoSpawnTask extends TimerTask {
        @Override
        public void run() {
            longTimeNoSpawn = true;
        }
    }

    class JustSpawnTask extends TimerTask {
        @Override
        public void run() {
            justSpawned = false;
        }
    }
}
