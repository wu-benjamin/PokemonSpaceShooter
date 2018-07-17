import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class HealthBar extends GameObject {

    private final Color yesHealth = new Color(66, 244, 66, 240);
    private final Color noHealth = new Color(244, 66, 66, 240);
    private Rectangle2D square;
    private Character c;
    private ControlPanel control;

    public HealthBar(int x, int y, int width, int height, Color color, Character c, ControlPanel control) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.c = c;
        this.control = control;
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
        // Draws red of health bar
        if (c.getToShow().equals(c.getImage1())) {
            square.setFrame(this.getX(), this.getY() + c.getHeight(), this.getWidth(), 15);
        } else {
            // Prevents health bar jumping around when Pokemon animation sprite changes (keeps x and y of bar fixed to that of image1 of the Pokemon)
            double scale = c.getHeight() / c.getToShow().getHeight();
            square.setFrame(this.getX() + scale * (c.getImage2().getWidth() - c.getImage1().getWidth()) / 2, this.getY() - scale * (c.getImage2().getHeight() - c.getImage1().getHeight()) / 2 + c.getHeight(), c.getImage1().getWidth() * scale, 15);
        }
        g2.setColor(noHealth);
        g2.fill(square);
        g2.draw(square);
        // Draws green bar on top
        if (c.getToShow().equals(c.getImage1())) {
            square.setFrame(this.getX(), this.getY() + c.getHeight(), this.getWidth() * (double) c.getHitPoints() / (double) c.getMaxHitPoints(), 15);
        } else {
            double scale = c.getHeight() / c.getToShow().getHeight();
            square.setFrame(this.getX() + scale * (c.getImage2().getWidth() - c.getImage1().getWidth()) / 2, this.getY() - scale * (c.getImage2().getHeight() - c.getImage1().getHeight()) / 2 + c.getHeight(), c.getImage1().getWidth() * scale * (double) c.getHitPoints() / (double) c.getMaxHitPoints(), 15);
        }
        g2.setColor(yesHealth);
        g2.fill(square);
        g2.draw(square);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(15f);
            g2.setFont(font);
        } catch (IOException e) {
        } catch (FontFormatException e) {
        }
        g2.setColor(Color.BLACK);
        int currentHealth = Math.max(c.getHitPoints(), 0);
        // Displays numerical health value of Pokemon
        if (c.getToShow().equals(c.getImage1())) {
            g2.drawString(Integer.toString(currentHealth), this.getX() , this.getY() + c.getHeight() + 15);
        } else {
            double scale = c.getHeight() / c.getToShow().getHeight();
            g2.drawString(Integer.toString(currentHealth), (int) (this.getX() + scale * (c.getImage2().getWidth() - c.getImage1().getWidth()) / 2),  (int) (this.getY() - scale * (c.getImage2().getHeight() - c.getImage1().getHeight()) / 2) + c.getHeight() + 15);
        }
    }
}
