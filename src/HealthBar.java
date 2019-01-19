import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HealthBar extends GameObject {

    private final Color YES_HEALTH = new Color(66, 244, 66, 240);
    private final Color NO_HEALTH = new Color(244, 66, 66, 240);
    private Rectangle2D square;
    private Character c;
    // private ControlPanel control;
    private static Font font;
    private double scale;
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(15f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HealthBar(int x, int y, int width, int height, Color color, Character c/*, ControlPanel control*/) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.c = c;
        // this.control = control;
        if (c instanceof Player) {
            this.scale = ControlPanel.PLAYER_SCALE;
        } else if (c instanceof Boss) {
            this.scale = ControlPanel.BOSS_SCALE;
        } else {
            this.scale = ControlPanel.ENEMY_SCALE;
        }
        ControlPanel.healthBarsToAdd.add(this);
    }

    public void update(ControlPanel panel) {
        this.setX(c.getX());
        this.setY(c.getY());
        if (this.getY() > ControlPanel.height || c.getHitPoints() <= 0) {
            ControlPanel.healthBarsToRemove.add(this);
        }
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void paintComponent(Graphics2D g2) {
        int avgWidth = (int) (scale * ((double) c.getImage1().getWidth() + (double) c.getImage2().getWidth()) / (double) 2 + 0.5);
        int maxHeight = (int) (scale * (double) Math.max(c.getImage1().getHeight(), c.getImage2().getHeight()));
        // Draws red of health bar
        square.setFrame(this.getX() + (c.getWidth() - avgWidth) / 2, this.getY() + maxHeight / 2 + c.getHeight() / 2, avgWidth, 15);
        g2.setColor(NO_HEALTH);
        g2.fill(square);
        g2.draw(square);
        // Draws green bar on top
        square.setFrame(this.getX() + (c.getWidth() - avgWidth) / 2, this.getY() + maxHeight / 2 + c.getHeight() / 2, avgWidth * (double) c.getHitPoints() / (double) c.getMaxHitPoints(), 15);
        g2.setColor(YES_HEALTH);
        g2.fill(square);
        g2.draw(square);
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        // Displays numerical health value of Pokemon
        g2.drawString(Integer.toString(Math.max(c.getHitPoints(), 0)), this.getX() + (c.getWidth() - avgWidth) / 2, this.getY() + maxHeight / 2 + c.getHeight() / 2 + 15);
        g2.setColor(ControlPanel.TRANSPARENT);
    }
}
