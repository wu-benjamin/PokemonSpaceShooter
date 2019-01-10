import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PowerUp extends GameObject {

    private Rectangle2D square;
    private BufferedImage image;
    private ControlPanel control;
    private Timer timer = new Timer();
    private String desc;
    private PowerUp pow;
    private static int POWER_UP_SIZE = 50;
    private static BufferedImage bomb;
    private static BufferedImage candy;
    private static BufferedImage berry;
    static {
        try {
            URL resource = Pokemon.class.getResource("/Resources/PowerUps/TM.png" );
            bomb = ImageIO.read(new File(resource.toURI()));
            resource = Pokemon.class.getResource("/Resources/PowerUps/RareCandy.png");
            candy = ImageIO.read(new File(resource.toURI()));
            resource = Pokemon.class.getResource("/Resources/PowerUps/OranBerry.png");
            berry = ImageIO.read(new File(resource.toURI()));
        } catch (Exception e) { }
    }

    public PowerUp(int x, int y, int width, int height, Color color, String desc, ControlPanel control) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(x, y, width, height);
        this.control = control;
        this.desc = desc;
        ControlPanel.toAdd.add(this);
        if (desc.equals("Bomb")) {
            image = bomb;
        } else if (desc.equals("RareCandy")) {
            image = candy;
        } else {
            image = berry;
        }
        this.pow = this;
        timer();
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) {
        // Despawns the powerup when off screen
        if (this.getX() < 0 || this.getX() > ControlPanel.width || this.getY() < 0 || this.getY() > ControlPanel.height) {
            ControlPanel.toRemove.add(this);
            timer.cancel();
            timer.purge();
        } else if (checkCollision(Player.getPlayer())) {
            // Effects of powerup take place when collected by player
            ControlPanel.toRemove.add(this);
            timer.cancel();
            timer.purge();
            if (desc.equals("Bomb")) {
                control.incrementBombs();
            } else if (desc.equals("RareCandy")) {
                control.incrementPower();
            } else {
                Player.getPlayer().setHitPoints(Player.getPlayer().getMaxHitPoints());
            }
        }
    }

    public static int getSize() {
        return POWER_UP_SIZE;
    }

    public boolean checkCollision(GameObject obj) {
        return (square.intersects(obj.getObj()));
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.fill(square);
        g2.draw(square);
        g2.drawImage(this.image, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
    }

    public void timer() {
        TimerTask task = new MyTask();
        timer.schedule(task, 0, 1000 / ControlPanel.FRAME_RATE);
    }

    // Moves the powerup down the screen over time
    class MyTask extends TimerTask {
        @Override
        public void run() {
            pow.setY(pow.getY() + 3);
        }
    }
}