import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;

// Handles HUD for the player
public abstract class HUD extends GameObject {

    private Rectangle2D square;
    ControlPanel control;
    static Font font;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
        }
    }

    public HUD(ControlPanel control) {
        super(0, 0, 1, 1, new Color(0,0,0,0));
        square = new Rectangle2D.Double(0, 0, 1, 1);
        this.control = control;
    }

    public Rectangle2D getObj() {
        return square;
    }

    public abstract void update(ControlPanel panel);

    public abstract void paintComponent(Graphics2D g2);
}