import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class HealthBar extends GameObject {

    private final Color yesHealth = new Color(66, 244, 66, 240);
    private final Color noHealth = new Color(244, 66, 66, 240);
    private Rectangle2D square;
    private Character c;
    private ControlPanel control;
    private static Font font;
    private double scale;
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(15f);
        } catch (Exception e) {

        }
    }

    public HealthBar(int x, int y, int width, int height, Color color, Character c, ControlPanel control) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.c = c;
        this.control = control;
        if (c.getImage1().equals(c.getPokemon().getBack1()) || c.getImage1().equals(c.getPokemon().getBack2())) {
            this.scale = 4;
        } else {
            if (c.getIsBoss()) {
                this.scale = 7;
            } else {
                this.scale = 3;
            }
        }
        ControlPanel.toAdd.add(this);
    }

    public void update(ControlPanel panel) {
        this.setX(c.getX());
        this.setY(c.getY());
        if (this.getX() < 0 || this.getX() > ControlPanel.width || this.getY() > ControlPanel.height || c.getHitPoints() < 0) {
            ControlPanel.toRemove.add(this);
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
        g2.setColor(noHealth);
        g2.fill(square);
        g2.draw(square);
        // Draws green bar on top
        square.setFrame(this.getX() + (c.getWidth() - avgWidth) / 2, this.getY() + maxHeight / 2 + c.getHeight() / 2, avgWidth * (double) c.getHitPoints() / (double) c.getMaxHitPoints(), 15);
        g2.setColor(yesHealth);
        g2.fill(square);
        g2.draw(square);
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        int currentHealth = Math.max(c.getHitPoints(), 0);
        // Displays numerical health value of Pokemon
        g2.drawString(Integer.toString(currentHealth), this.getX() + (c.getWidth() - avgWidth) / 2, this.getY() + maxHeight / 2 + c.getHeight() / 2 + 15);
    }
}
