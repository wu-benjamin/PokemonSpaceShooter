import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Background extends GameObject {

    private Rectangle2D square;
    private BufferedImage image;
    private ControlPanel control;
    private static boolean move;
    private Timer timer;

    Background(int x, int y, int width, int height, Color color, BufferedImage image, ControlPanel control, boolean move) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.image = image;
        this.color = color;
        this.control = control;
        Background.move = move;
        timer = new Timer();
        MoveTask moveTask = new MoveTask();
        timer.schedule(moveTask, 0, 17);
    }

    public Rectangle2D getObj() {
        return square;
    }

    static void setMove(boolean canMove) {
        move = canMove;
    }

    public Timer getTimer() {
        return timer;
    }

    public void update(ControlPanel panel) {
        // Scrolls background while not fighting boss

    }

    public void paintComponent(Graphics2D g2) {
        g2.drawImage(this.image, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
        g2.drawImage(this.image, this.getX(), this.getY() + this.getHeight(), this.getWidth(), this.getHeight(), control);
        g2.setColor(ControlPanel.BACKGROUND_TINT);
        g2.fill3DRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), false);
        g2.fill3DRect(this.getX(), this.getY() + this.getHeight(), this.getWidth(), this.getHeight(), false);
    }

    class MoveTask extends TimerTask {
        @Override
        public void run() {
            if (move) {
                y++;
                if (y >= 0) {
                    y = -ControlPanel.height;
                }
            }
        }
    }
}