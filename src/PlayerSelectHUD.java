import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class PlayerSelectHUD extends HUD {

    int player;
    private int numberOfPokemon;
    private BufferedImage toShow;
    int width;
    int height;
    int x;
    int y;
    private String level;

    PlayerSelectHUD(ControlPanel control) {
        super(control);
        if (ControlPanel.player == null) {
            for (int i = 0; i < ControlPanel.unlockedPokemon.length; i++) {
                if (ControlPanel.unlockedPokemon[i]) {
                    this.player = i;
                    break;
                }
            }
        } else {
            this.player = ControlPanel.player.getPokemon().getIndex();
        }
        this.toShow = Pokemon.values()[player].getFront1();
        this.width = this.toShow.getWidth() * BIG_DISPLAY_SCALE;
        this.height = this.toShow.getHeight() * BIG_DISPLAY_SCALE;
        this.x = ControlPanel.width / 2 - Pokemon.values()[player].getWidth() * BIG_DISPLAY_SCALE / 2;
        this.y = ControlPanel.height / 4 - Pokemon.values()[player].getHeight() * BIG_DISPLAY_SCALE / 2;
        this.level = Pokemon.appearsIn(Pokemon.values()[player]);
        this.numberOfPokemon = ControlPanel.unlockedPokemon.length;
        TimerTask imageTask = new MyImageTask();
        timer.schedule(imageTask, 0, 300);
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
    }

    private void incrementPlayer() {
        player++;
        player %= numberOfPokemon;
        level = Pokemon.appearsIn(Pokemon.values()[player]);
    }

    private void decrementPlayer() {
        player += numberOfPokemon - 1;
        player %= numberOfPokemon;
        level = Pokemon.appearsIn(Pokemon.values()[player]);
    }

    // Animates sprite
    class MyImageTask extends TimerTask {
        @Override
        public void run() {
            double scale = height / toShow.getHeight();
            int orgHeight = height;
            int orgWidth = width;
            if (toShow.equals(Pokemon.values()[player].getFront1())) {
                toShow = Pokemon.values()[player].getFront2();
            } else {
                toShow = Pokemon.values()[player].getFront1();
            }
            width = (int) (toShow.getWidth() * scale);
            height = (int) (toShow.getHeight() * scale);
            x = x + (orgWidth - width) / 2;
            y = y + (orgHeight - height) / 2;
        }
    }

    public void paintComponent(Graphics2D g2) {
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawImage(toShow, x, y, width, height, control);
        if (!ControlPanel.unlockedPokemon[player]) {
            drawBorderedString(g2,"Locked (Find in " + level + ")", 20, ControlPanel.height - 20);
        } else {
            drawBorderedString(g2,"Visit me in " + level + "!", 20, ControlPanel.height - 20);
        }
        drawCenteredString(g2, new Rectangle(0,ControlPanel.height  / 4, ControlPanel.width, ControlPanel.height * 3 / 4), Pokemon.values()[player].getName()
        + "\n" + Pokemon.values()[player].getType1() + "\n" + Pokemon.values()[player].getType2(), font);
        g2.drawImage(Pokemon.values()[player].getAttack().getAttackImage(), ControlPanel.width * 4 / 5 - ATTACK_IMAGE_SIZE / 2,
                ControlPanel.height / 5 - ATTACK_IMAGE_SIZE, ATTACK_IMAGE_SIZE, ATTACK_IMAGE_SIZE, control);
        drawCenteredString(g2, new Rectangle(ControlPanel.width * 3 / 5,ControlPanel.height / 5, ControlPanel.width * 2 / 5,
                        ControlPanel.height * 4 / 5),
                Pokemon.values()[player].getAttack().getAttackName() +"\n" + Pokemon.values()[player].getAttack().getType().getName()
                + "\n" + Pokemon.values()[player].getAttack().getAttackPath() + "\nBase Power: " + Pokemon.values()[player].getAttack().getAttackDamage()
                + "\nSize: " + Pokemon.values()[player].getAttack().getProjectileSize()
                + "\nSpeed: " + Pokemon.values()[player].getAttack().getProjectileSpeed() + "\nCooldown: " + Pokemon.values()[player].getAttack().getAttackDelay(), font);
        drawCenteredString(g2, new Rectangle(0,ControlPanel.height / 5, ControlPanel.width * 2 / 5, ControlPanel.height * 4 / 5),
                Pokemon.values()[player].getName() + "\nHit Points: " + Pokemon.values()[player].getHitPoints() + "\nAttack: "
                + (int) Pokemon.values()[player].getAttackPower() +"\nSpeed: " + (int) Pokemon.values()[player].getBaseSpeed(),font);
        drawCenteredString(g2, new Rectangle(ControlPanel.width - 120, 10, 120, 70), Integer.toString(Pokemon.values()[player].getIndex()), font);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedPokemon[player] && (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.player = new Player(ControlPanel.width / 2 - Pokemon.values()[player].getWidth() * ControlPanel.PLAYER_SCALE / 2, ControlPanel.height / 2 - Pokemon.values()[player].getHeight() *
                                ControlPanel.PLAYER_SCALE / 2, Pokemon.values()[player].getWidth() * ControlPanel.PLAYER_SCALE, Pokemon.values()[player].getHeight() * ControlPanel.PLAYER_SCALE,
                        ControlPanel.TRANSPARENT, Pokemon.values()[player], control);
                ControlPanel.menusToAdd.add(new PlayingHUD(control));
                ControlPanel.menusToRemove.add(this);
                return;
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementPlayer();
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementPlayer();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
                TimerTask imageTask = new MyImageTask();
                timer.schedule(imageTask, 0);
            }
        }
    }
}
