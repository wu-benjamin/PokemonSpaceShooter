import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class PowerUp extends GameObject {

    private Rectangle2D square;
    private BufferedImage image;
    private ControlPanel control;
    private Timer timer = new Timer();
    private PowerUp powerup;
    private static final int POWER_UP_SIZE = 50;
    static BufferedImage bombImage;
    static BufferedImage candyImage;
    static BufferedImage berryImage;
    static final int BERRY_CHANCE = 50;
    static final int CANDY_CHANCE = 25;
    static final int BOMB_CHANCE = 25;
    static final int MAX_BOMB = 99;
    static final int MAX_LEVEL = 5;
    static final int BERRY_HEAL_AMOUNT = 100;


    static {
        try {
            URL resource = Pokemon.class.getResource("/Resources/PowerUps/TM.png" );
            bombImage = ImageIO.read(new File(resource.toURI()));
            resource = Pokemon.class.getResource("/Resources/PowerUps/RareCandy.png");
            candyImage = ImageIO.read(new File(resource.toURI()));
            resource = Pokemon.class.getResource("/Resources/PowerUps/OranBerry.png");
            berryImage = ImageIO.read(new File(resource.toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PowerUp(int x, int y, int width, int height, ControlPanel control, BufferedImage itemImage) {
        super(x, y, width, height, ControlPanel.TRANSPARENT);
        square = new Rectangle2D.Double(x, y, width, height);
        this.control = control;
        this.image = itemImage;
        this.powerup = this;
        ControlPanel.powerUpsToAdd.add(this);
        timer();
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) {
        // De-spawns the power-up when off screen
        if (this.getX() < 0 || this.getX() > ControlPanel.width || this.getY() < 0 || this.getY() > ControlPanel.height) {
            ControlPanel.powerUpsToRemove.add(this);
        } else if (checkCollision(Player.getPlayer())) {
            // Effects of power-up take place when collected by player
            ControlPanel.powerUpsToRemove.add(this);
            performEffect(control);
        }
    }

    public abstract void performEffect(ControlPanel control);

    static int getSize() {
        return POWER_UP_SIZE;
    }

    private boolean checkCollision(GameObject obj) {
        return (square.intersects(obj.getObj()));
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(this.getColor());
        g2.fill(square);
        g2.draw(square);
        g2.drawImage(this.image, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
    }

    private void timer() {
        TimerTask task = new MoveTask();
        timer.schedule(task, 0, 20);
    }

    public Timer getTimer() {
        return timer;
    }

    // Moves the power-up down the screen over time
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            powerup.setY(powerup.getY() + 1);
        }
    }
}