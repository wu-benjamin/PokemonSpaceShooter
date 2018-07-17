import java.awt.*;
import java.awt.geom.*;

// General GUI class for collision checks and painting display
public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    private final int MINIMUM_BULLET_SPEED = 3;
    private final Color YES_HEALTH = new Color(66, 244, 66, 240);
    private final Color NO_HEALTH = new Color(244, 66, 66, 240);

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinimumBulletSpeed() {
        return MINIMUM_BULLET_SPEED;
    }

    public Color getHealthColor() {
        return YES_HEALTH;
    }

    public Color getDamageColor() {
        return NO_HEALTH;
    }

    public Color getColor() {
        return color;
    }

    public abstract void update(ControlPanel panel);

    public abstract void paintComponent(Graphics2D g2);

    public abstract Rectangle2D getObj();
}


