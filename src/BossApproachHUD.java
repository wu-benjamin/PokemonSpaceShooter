import java.awt.*;

public class BossApproachHUD extends HUD {

    public BossApproachHUD(ControlPanel control) {
        super(control);
    }
    public void paintComponent(Graphics2D g2) {
        if (control.getBossFight() && ControlPanel.boss.getY() < -5) {
            g2.setColor(ControlPanel.TEXT_BACKGROUND);
            g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
            g2.setColor(ControlPanel.TEXT);
            g2.setFont(font);
            g2.drawString("Boss Approaching!", ControlPanel.width / 2 - 210, ControlPanel.height / 2);
        }
    }

    public void update(ControlPanel control) {
        return;
    }
}
