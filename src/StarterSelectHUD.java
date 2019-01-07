import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class StarterSelectHUD extends HUD {

    int currentStarterDexNumIndex = 0;
    int[] starterDexNums = {1,4,7};
    Timer timer = new Timer();
    boolean delay = false;

    public StarterSelectHUD(ControlPanel control) {
        super(control);
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

    public void incrementStarter() {
        currentStarterDexNumIndex++;
        currentStarterDexNumIndex %= starterDexNums.length;
    }

    public void decrementStarter() {
        currentStarterDexNumIndex += starterDexNums.length - 1;
        currentStarterDexNumIndex %= starterDexNums.length;
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(ControlPanel.TEXT_BACKGROUND);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawImage(Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getFront1(), ControlPanel.width / 2 - Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getWidth() * ControlPanel.PLAYER_SCALE / 2,
                ControlPanel.height / 2 - Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getHeight() * ControlPanel.PLAYER_SCALE / 2,
                Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getWidth() * ControlPanel.PLAYER_SCALE, Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getHeight() * ControlPanel.PLAYER_SCALE, control);
        //g2.drawString("Pokemon, The Space Shooter", ControlPanel.width / 2 - 250, ControlPanel.height / 2 - 50);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1)) {
                ControlPanel.unlockedPokemon[starterDexNums[currentStarterDexNumIndex]] = true;
                ControlPanel.toAdd.add(new LevelSelectHUD(control));
                ControlPanel.toRemove.add(this);
            } else if (panel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementStarter();
            } else if (panel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementStarter();
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
