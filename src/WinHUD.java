import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class WinHUD extends HUD {

    public WinHUD(ControlPanel control) {
        super(control);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics2D g2) {
        g2.setColor(ControlPanel.TEXT_BACKGROUND);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawString("End of Level!", ControlPanel.width / 2 - 150, ControlPanel.height / 2 - 50);
        g2.drawString("You scored " + control.getScore() + " points!", ControlPanel.width / 2 - 250, ControlPanel.height / 2 + 50);
    }

    public void update(ControlPanel panel) {
        if (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1)) {
            ControlPanel.toAdd.add(new LevelSelectHUD(control));
            ControlPanel.toRemove.add(this);
        }
    }
}
