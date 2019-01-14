import java.awt.*;

public class PlayingHUD extends HUD {

    private static int numSpawned = 0;
    private static int maxSpawn;
    private static int numRemaining;

    public PlayingHUD(ControlPanel control) {
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
        this.maxSpawn = ControlPanel.location.getNumberOfEnemies();
        ControlPanel.toAdd.add(new Background(0, -ControlPanel.height, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT,ControlPanel.location.getBackground(), control, true));
        ControlPanel.toAdd.add(new Background(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, ControlPanel.location.getBackground(), control, true));
        ControlPanel.toAdd.add(new Player(ControlPanel.width / 2 - ControlPanel.playerPokemon.getWidth() * ControlPanel.PLAYER_SCALE / 2, ControlPanel.height / 2 - ControlPanel.playerPokemon.getHeight() *
                ControlPanel.PLAYER_SCALE / 2, ControlPanel.playerPokemon.getWidth() * ControlPanel.PLAYER_SCALE, ControlPanel.playerPokemon.getHeight() * ControlPanel.PLAYER_SCALE,
                ControlPanel.TRANSPARENT, ControlPanel.playerPokemon, control));
        this.numRemaining = this.maxSpawn + 1;
    }

    public static void decrementNumRemaining(int decrement) {
        numRemaining -= decrement;
    }


    public void paintComponent(Graphics2D g2) {
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawBorderedString(g2, "Score: " + control.getScore(), 20, 70, 200);
        drawBorderedString(g2, "Z-Move: " + control.getBombs(), 20, ControlPanel.height - 25, 200);
        drawBorderedString(g2,"Level: " + control.getPower(), ControlPanel.width - 250, ControlPanel.height - 25, 200);
        drawBorderedString(g2, numRemaining + " Enemies Remain", ControlPanel.width - 480, 70, 200);
    }

    public void update(ControlPanel control) {
        if (ControlPanel.win) {
            ControlPanel.menusToAdd.add(new WinHUD(control));
            ControlPanel.menusToRemove.add(this);
            ControlPanel.clear();
            return;
        }
        if (ControlPanel.dead) {
            ControlPanel.menusToAdd.add(new DeadHUD(control));
            ControlPanel.menusToRemove.add(this);
            ControlPanel.clear();
            return;
        }
        if (numSpawned <= maxSpawn) {
            if (ControlPanel.rand.nextInt((int) (ControlPanel.FRAME_RATE / ControlPanel.SPAWN_PER_SECOND)) == 0) {
                if (numSpawned < maxSpawn) {
                    Pokemon enemy = ControlPanel.location.getEnemies()[ControlPanel.rand.nextInt(ControlPanel.location.getEnemies().length)];
                    ControlPanel.toAdd.add(new Enemy(ControlPanel.rand.nextInt(ControlPanel.width - enemy.getWidth() * ControlPanel.ENEMY_SCALE),
                            -enemy.getHeight() * ControlPanel.ENEMY_SCALE - 5, enemy.getWidth() * ControlPanel.ENEMY_SCALE,
                            enemy.getHeight() * ControlPanel.ENEMY_SCALE, ControlPanel.TRANSPARENT, enemy, control));
                    numSpawned++;
                }
            }
            if (ControlPanel.enemies.size() == 0 && numSpawned == maxSpawn) {
                control.setBossFight(true);
                Pokemon bossPokemon = ControlPanel.location.getBoss();
                Player.setBossWall(bossPokemon.getHeight() * ControlPanel.BOSS_SCALE);
                Boss boss = new Boss(ControlPanel.width / 2 - bossPokemon.getWidth() * ControlPanel.BOSS_SCALE / 2,
                        -bossPokemon.getHeight() * ControlPanel.BOSS_SCALE - 100, bossPokemon.getWidth() * ControlPanel.BOSS_SCALE,
                        bossPokemon.getHeight() * ControlPanel.BOSS_SCALE, ControlPanel.TRANSPARENT, bossPokemon, control);
                ControlPanel.toAdd.add(boss);
            }
        }
    }
}
