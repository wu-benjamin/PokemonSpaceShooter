import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class DeadHUD extends HUD {

    public DeadHUD(ControlPanel control) {
        super(control);
        try {
            ControlPanel.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(ControlPanel.MENU_DELAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics2D g2) {
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawString("Game Over!", ControlPanel.width / 2 - 150, ControlPanel.height / 2 - 50);
        g2.drawString("You scored " + control.getScore() + " points!", ControlPanel.width / 2 - 250, ControlPanel.height / 2 + 50);
    }

    public void update(ControlPanel panel) {
        if (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1)) {
            ControlPanel.toAdd.add(new LevelSelectHUD(control));
            ControlPanel.toRemove.add(this);
            return;
        }
    }
}
