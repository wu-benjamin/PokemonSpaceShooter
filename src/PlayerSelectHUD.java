import javax.naming.ldap.Control;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerSelectHUD extends HUD {

    int player;
    int numberOfPokemon;
    BufferedImage toShow;
    int width;
    int height;
    int x;
    int y;
    boolean delay = false;
    Timer timer = new Timer();

    public PlayerSelectHUD(ControlPanel control) {
        super(control);
        for (int i = 0; i < ControlPanel.unlockedPokemon.length; i++) {
            if (ControlPanel.unlockedPokemon[i]) {
                this.player = i;
                this.toShow = Pokemon.values()[player].getFront1();
                this.width = this.toShow.getWidth() * DISPLAY_SCALE;
                this.height = this.toShow.getHeight() * DISPLAY_SCALE;
                this.x = ControlPanel.width / 2 - Pokemon.values()[player].getWidth() * DISPLAY_SCALE / 2;
                this.y = ControlPanel.height / 3 - Pokemon.values()[player].getHeight() * DISPLAY_SCALE / 2;
            }
        }
        this.numberOfPokemon = ControlPanel.unlockedPokemon.length;
        TimerTask imageTask = new MyImageTask();
        timer.schedule(imageTask, 0, 300);
        try {
            Thread.sleep(ControlPanel.MENU_DELAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DelayTask extends TimerTask {
        @Override
        public void run() {
            try {
                delay = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        drawCenteredString(g2, new Rectangle(0,ControlPanel.height * 2 / 3, ControlPanel.width, ControlPanel.height / 3), Pokemon.values()[player].getName()
        + "\n" + Pokemon.values()[player].getType1() + "\n" + Pokemon.values()[player].getType2() + "\n" + Pokemon.values()[player].getAttack().getAttackName(), font);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedPokemon[player] && (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.player = Pokemon.values()[player];
                ControlPanel.toAdd.add(new PlayingHUD(control));
                timer.cancel();
                timer.purge();
                ControlPanel.toRemove.add(this);
                return;
            } else if (panel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementPlayer();
            } else if (panel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementPlayer();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY);
                TimerTask imageTask = new MyImageTask();
                timer.schedule(imageTask, 0);
            }
        }
    }
}
