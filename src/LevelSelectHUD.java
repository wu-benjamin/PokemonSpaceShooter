import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.TimerTask;

public class LevelSelectHUD extends HUD {

    int level;
    int numberOfLevels;

    public LevelSelectHUD(ControlPanel control) {
        super(control);
        for (int i = 0; i < ControlPanel.unlockedLocation.length; i++) {
            if (ControlPanel.unlockedLocation[i]) {
                this.level = i;
            }
        }
        this.numberOfLevels = ControlPanel.unlockedLocation.length;
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
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
        FontMetrics metrics = g2.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        g2.drawImage(Location.values()[level].getBackground(), 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.BACKGROUND_TINT);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawCenteredString(g2, new Rectangle(0,0,ControlPanel.width,ControlPanel.height), Location.values()[level].getLevelName(), font);
        if (!ControlPanel.unlockedLocation[level]) {
            drawCenteredString(g2, new Rectangle(20, ControlPanel.height - 20 - lineHeight, 200, lineHeight),"LOCKED", font);
        }
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedLocation[level] && (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.location = Location.values()[level];
                ControlPanel.menusToAdd.add(new PlayerSelectHUD(control));
                ControlPanel.menusToRemove.add(this);
                return;
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementLevel();
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementLevel();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
            }
        }
    }
}
