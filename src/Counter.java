import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;

// Handles HUD display for the player
public class Counter extends GameObject {

    private String desc;
    private Rectangle2D square;
    private Color color;
    private ControlPanel control;
    private static Font font;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
        }
    }

    public Counter(int x, int y, int width, int height, Color color, String desc, ControlPanel control) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.desc = desc;
        this.color = color;
        this.control = control;
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) { }

    public void paintComponent(Graphics2D g2) {
        if (!desc.equals("Boss") || control.getBossFight() && ControlPanel.boss.getY() < -5) {
            g2.setColor(ControlPanel.TEXT_BACKGROUND);
            g2.fill(square);
            g2.draw(square);
            g2.setColor(ControlPanel.TEXT);
            g2.setFont(font);
        }
        switch (desc) {
            case "Score: ":
                g2.drawString(desc + control.getScore() * 100, this.getX() + 20, this.getY() + 50);
                break;
            case "Z-Move: ":
                g2.drawString(desc + control.getBombs(), this.getX() + 20, this.getY() + 50);
                break;
            case "Level: ":
                g2.drawString(desc + control.getPower(), this.getX() + 20, this.getY() + 50);
                break;
            case "Win":
                g2.drawString("End of Level!", ControlPanel.width / 2 - 150, ControlPanel.height / 2 - 50);
                g2.drawString("You scored " + control.getScore() * 100 + " points!", ControlPanel.width / 2 - 250, ControlPanel.height / 2 + 50);
                break;
            case "Die":
                // To be completed
                break;
            case "Boss":
                if (control.getBossFight() && ControlPanel.boss.getY() < -5) {
                    g2.drawString("Boss Approaching!", ControlPanel.width / 2 - 210, ControlPanel.height / 2);
                }
                break;
        }
    }
}