import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.TimerTask;

public class LocationOutroHUD extends HUD {

    LocationOutroHUD(ControlPanel control) {
        super(control);
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, 500);
        ControlPanel.location.getOutroMusic().play();
    }
    public void paintComponent(Graphics2D g2) {
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawCenteredString(g2, new Rectangle(0, 0, ControlPanel.width, ControlPanel.height), ControlPanel.location.getOutro(), font);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            if (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1)) {
                ControlPanel.menusToAdd.add(new LevelSelectHUD(control));
                ControlPanel.menusToRemove.add(this);
                SoundFX.MENU_SELECT.play();
            }
        }
    }
}
