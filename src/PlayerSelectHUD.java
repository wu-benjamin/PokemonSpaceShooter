import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class PlayerSelectHUD extends HUD {

    int player;
    int numberOfPokemon;
    BufferedImage toShow;
    int width;
    int height;
    int x;
    int y;

    public PlayerSelectHUD(ControlPanel control) {
        super(control);
        if (ControlPanel.playerPokemon == null) {
            for (int i = 0; i < ControlPanel.unlockedPokemon.length; i++) {
                if (ControlPanel.unlockedPokemon[i]) {
                    this.player = i;
                    this.toShow = Pokemon.values()[player].getFront1();
                    this.width = this.toShow.getWidth() * DISPLAY_SCALE;
                    this.height = this.toShow.getHeight() * DISPLAY_SCALE;
                    this.x = ControlPanel.width / 2 - Pokemon.values()[player].getWidth() * DISPLAY_SCALE / 2;
                    this.y = ControlPanel.height / 4 - Pokemon.values()[player].getHeight() * DISPLAY_SCALE / 2;
                    break;
                }
            }
        } else {
            this.player = ControlPanel.playerPokemon.getIndex();
            this.toShow = Pokemon.values()[player].getFront1();
            this.width = this.toShow.getWidth() * DISPLAY_SCALE;
            this.height = this.toShow.getHeight() * DISPLAY_SCALE;
            this.x = ControlPanel.width / 2 - Pokemon.values()[player].getWidth() * DISPLAY_SCALE / 2;
            this.y = ControlPanel.height / 3 - Pokemon.values()[player].getHeight() * DISPLAY_SCALE / 2;
        }
        this.numberOfPokemon = ControlPanel.unlockedPokemon.length;
        TimerTask imageTask = new MyImageTask();
        timer.schedule(imageTask, 0, 300);
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
    }

    public void incrementPlayer() {
        player++;
        player %= numberOfPokemon;
        /*
        if (!ControlPanel.unlockedPokemon[player]) {
            incrementPlayer();
        }
        */
    }

    public void decrementPlayer() {
        player += numberOfPokemon - 1;
        player %= numberOfPokemon;
        /*
        if (!ControlPanel.unlockedPokemon[player]) {
            decrementPlayer();
        }
        */
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
        FontMetrics metrics = g2.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawImage(toShow, x, y, width, height, control);
        if (!ControlPanel.unlockedPokemon[player]) {
            drawCenteredString(g2, new Rectangle(20, ControlPanel.height - 20 - lineHeight, 200, lineHeight),"LOCKED", font);
        }
        drawCenteredString(g2, new Rectangle(0,ControlPanel.height  / 4, ControlPanel.width, ControlPanel.height * 3 / 4), Pokemon.values()[player].getName()
        + "\n" + Pokemon.values()[player].getType1() + "\n" + Pokemon.values()[player].getType2(), font);
        g2.drawImage(Pokemon.values()[player].getAttack().getAttackImage(), ControlPanel.width * 3 / 4 - ATTACK_IMAGE_SIZE / 2,
                ControlPanel.height / 5 - ATTACK_IMAGE_SIZE, ATTACK_IMAGE_SIZE, ATTACK_IMAGE_SIZE, control);
        drawCenteredString(g2, new Rectangle(ControlPanel.width / 2,ControlPanel.height / 5, ControlPanel.width / 2, ControlPanel.height * 4 / 5),
                Pokemon.values()[player].getAttack().getAttackName() +"\n" + Pokemon.values()[player].getAttack().getType().getName()
                + "\n" + Pokemon.values()[player].getAttack().getAttackPath() + "\nBase Power: " + Pokemon.values()[player].getAttack().getAttackDamage()
                + "\nSize: " + Pokemon.values()[player].getAttack().getProjectileSize()
                + "\nSpeed: " + Pokemon.values()[player].getAttack().getProjectileSpeed() + "\nCooldown: " + Pokemon.values()[player].getAttack().getAttackDelay(), font);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedPokemon[player] && (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.playerPokemon = Pokemon.values()[player];
                ControlPanel.menusToAdd.add(new PlayingHUD(control));
                timer.cancel();
                timer.purge();
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
