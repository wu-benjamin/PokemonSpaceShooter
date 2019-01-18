import java.awt.*;

public class BossApproachNotice {

    private ControlPanel control;
    private static Font font;
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BossApproachNotice(ControlPanel control) {
        this.control = control;
    }
    public void paintComponent(Graphics2D g2) {
        if (control.getBossFight() && ControlPanel.boss.getY() < -ControlPanel.boss.getHeight() - 30) {
            HUD.drawCenteredString(g2, new Rectangle(0, 0, ControlPanel.width, ControlPanel.height), "Boss Approaching!", font);
        }
    }
}
