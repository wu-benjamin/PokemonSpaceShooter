import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class LevelSelectHUD extends HUD {

    int level;
    int numberOfLevels;
    boolean delay = false;
    Timer timer = new Timer();

    public LevelSelectHUD(ControlPanel control) {
        super(control);
        for (int i = 0; i < ControlPanel.unlockedLocation.length; i++) {
            if (ControlPanel.unlockedLocation[i]) {
                this.level = i;
            }
        }
        this.numberOfLevels = ControlPanel.unlockedLocation.length;
        try {
            Thread.sleep(100);
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

    public void incrementLevel() {
        level++;
        level %= numberOfLevels;
        /*
        if (!ControlPanel.unlockedPokemon[level]) {
            incrementLevel();
        }
        */
    }

    public void decrementLevel() {
        level += numberOfLevels - 1;
        level %= numberOfLevels;
        /*
        if (!ControlPanel.unlockedPokemon[level]) {
            decrementLevel();
        }
        */
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(ControlPanel.TEXT_BACKGROUND);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawString(Location.values()[level].getLevelName(), ControlPanel.width / 2 - 100, ControlPanel.height / 2);
        if (!ControlPanel.unlockedLocation[level]) {
            g2.drawString("LOCKED", 20, ControlPanel.height - 100);
        }
        //g2.drawString("Pokemon, The Space Shooter", ControlPanel.width / 2 - 250, ControlPanel.height / 2 - 50);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedLocation[level] && (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.level = Location.values()[level];
                ControlPanel.toAdd.add(new PlayerSelectHUD(control));
                ControlPanel.toRemove.add(this);
            } else if (panel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementLevel();
            } else if (panel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementLevel();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY);
            }
        }
    }
}
