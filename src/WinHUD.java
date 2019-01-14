import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class WinHUD extends HUD {

    public WinHUD(ControlPanel control) {
        super(control);
        ControlPanel.unlockedLocation[ControlPanel.location.getLevelIndex() + 1] = true;
        try {
            ControlPanel.save();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ControlPanel.dead = false;
        ControlPanel.win = false;
    }
    public void paintComponent(Graphics2D g2) {
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawCenteredString(g2, new Rectangle(0,0,ControlPanel.width,ControlPanel.height), "End of Level!\nYou scored " + control.getScore() + " points!", font);
    }

    public void update(ControlPanel panel) {
        if (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1)) {
            ControlPanel.menusToAdd.add(new LevelSelectHUD(control));
            ControlPanel.menusToRemove.add(this);
            return;
        }
    }
}
