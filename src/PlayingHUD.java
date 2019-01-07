import java.awt.*;

public class PlayingHUD extends HUD {

    public PlayingHUD(ControlPanel control) {
        super(control);
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(ControlPanel.TEXT_BACKGROUND);
        g2.fill3DRect(0,0, 350, 70, false);
        g2.fill3DRect(0,ControlPanel.height - 60, 350, 70, false);
        g2.fill3DRect(ControlPanel.width - 250,ControlPanel.height - 60, 250, 70, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawString("Score: " + control.getScore(), 20,  50);
        g2.drawString("Z-Move: " + control.getBombs(), 20,  ControlPanel.height - 10);
        g2.drawString("Level: " + control.getPower(), ControlPanel.width - 230,  ControlPanel.height - 10);
    }

    public void update(ControlPanel control) {
        return;
    }
}
