import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingHUD extends HUD {

    private static int numSpawned;
    private static int maxSpawn;
    private static int numRemaining;
    private static boolean longTimeNoSpawn = false;
    private static boolean justSpawned = false;
    private static final int MAX_TIME_NO_SPAWN = 7000; // time in ms
    private static final int MIN_TIME_NO_SPAWN = 1000; // time in ms
    private Timer spawnTimer = new Timer();

    PlayingHUD(ControlPanel control) {
        super(control);
        ControlPanel.resetItems();
        numSpawned = 0;
        maxSpawn = ControlPanel.location.getNumberOfEnemies();
        numRemaining = maxSpawn;

        ControlPanel.background = new Background(0, -ControlPanel.height, ControlPanel.width, ControlPanel.height,
                ControlPanel.TRANSPARENT,ControlPanel.location.getBackground(), control, true);
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
        LongNoSpawnTask longNoSpawnTask = new LongNoSpawnTask();
        spawnTimer.schedule(longNoSpawnTask, MAX_TIME_NO_SPAWN);
        JustSpawnTask justSpawnTask = new JustSpawnTask();
        spawnTimer.schedule(justSpawnTask, MIN_TIME_NO_SPAWN);
        ControlPanel.location.getLevelMusic().play();
    }

    static void decrementNumRemaining(int decrement) {
        numRemaining -= decrement;
    }


    public void paintComponent(Graphics2D g2) {
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawBorderedString(g2, "Score: " + control.getScore(), 20, 70);
        drawBorderedString(g2, "Z-Move: " + control.getBombs(), ControlPanel.width - 270, ControlPanel.height - 25);
        drawBorderedString(g2,"Level: " + control.getPower(), ControlPanel.width - 250, 70);
        drawBorderedString(g2, numRemaining + " Enemies Remain", 20, ControlPanel.height - 25);
    }

    public void update(ControlPanel control) {
        if (!delay) {
            if (ControlPanel.win && ControlPanel.recruitNotice.size() == 0) {
                ControlPanel.menusToAdd.add(new WinHUD(control));
                ControlPanel.menusToRemove.add(this);
                return;
            }
            if (ControlPanel.dead && ControlPanel.recruitNotice.size() == 0) {
                ControlPanel.menusToAdd.add(new DeadHUD(control));
                ControlPanel.menusToRemove.add(this);
                return;
            }
            if (numSpawned < maxSpawn) {
                if ((ControlPanel.rand.nextInt((int) (ControlPanel.FRAME_RATE / ControlPanel.SPAWN_PER_SECOND)) == 0 || longTimeNoSpawn) && !justSpawned) {
                    if (numSpawned < maxSpawn - 1) {
                        Pokemon enemy = ControlPanel.location.getEnemies()[ControlPanel.rand.nextInt(ControlPanel.location.getEnemies().length)];
                        ControlPanel.enemiesToAdd.add(new Enemy(ControlPanel.rand.nextInt(ControlPanel.width - enemy.getWidth() * ControlPanel.ENEMY_SCALE),
                                -enemy.getHeight() * ControlPanel.ENEMY_SCALE - 100, enemy.getWidth() * ControlPanel.ENEMY_SCALE,
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
                } else if (ControlPanel.numEnemies() == 0 && numSpawned == maxSpawn - 1) {
                    control.setBossFight(true);
                    Pokemon bossPokemon = ControlPanel.location.getBoss();
                    Player.setBossWall(bossPokemon.getHeight() * ControlPanel.BOSS_SCALE + 40);
                    Boss boss = new Boss(ControlPanel.width / 2 - bossPokemon.getWidth() * ControlPanel.BOSS_SCALE / 2,
                            -bossPokemon.getHeight() * ControlPanel.BOSS_SCALE - 200, bossPokemon.getWidth() * ControlPanel.BOSS_SCALE,
                            bossPokemon.getHeight() * ControlPanel.BOSS_SCALE, ControlPanel.TRANSPARENT, bossPokemon, control);
                    ControlPanel.enemiesToAdd.add(boss);
                    ControlPanel.boss = boss;
                    numSpawned++;
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
