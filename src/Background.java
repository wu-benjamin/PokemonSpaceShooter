import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class Background extends GameObject {

    private Color color;
    private Rectangle2D square;
    private BufferedImage image;
    private ControlPanel control;
    private static boolean move;

    public Background(int x, int y, int width, int height, Color color, BufferedImage image, ControlPanel control, boolean move) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.image = image;
        this.color = color;
        this.control = control;
        Background.move = move;
    }

    public Rectangle2D getObj() {
        return square;
    }

    public static void setMove(boolean canMove) {
        move = canMove;
    }

    public void update(ControlPanel panel) {
        // Scrolls background while not fighting boss
        if (move) {
            this.setY(this.getY() + 1);
            if (this.getY() == ControlPanel.height) {
                this.setY(-ControlPanel.height);
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(color);
        g2.fill(square);
        g2.draw(square);
        g2.drawImage(this.image, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
        g2.setColor(ControlPanel.BACKGROUND_TINT);
        g2.fill3DRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), false);
    }
}