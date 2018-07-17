import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;

// Abstract Pokemon Character for methods shared by player and enemies such as health checks
public abstract class Character extends GameObject {

    protected Rectangle2D square;
    protected ControlPanel control;
    protected Attack attack;
    protected Type type1;
    protected Type type2;
    private int hitPoints;
    private int maxHitPoints;

    public Character(int x, int y, int width, int height, Color color, Pokemon p, ControlPanel control) {
        super(x, y, width, height, color);
        this.square = new Rectangle2D.Double(x,y,width,height);
        this.control = control;
        this.attack = p.getAttack();
        this.type1 = p.getType1();
        this.type2 = p.getType2();
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int newHealth) {
        hitPoints = newHealth;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int newHealth) {
        maxHitPoints = newHealth;
    }

    public Type getType1() {
        return type1;
    }

    public Type getType2() {
        return type2;
    }

    public abstract  BufferedImage getToShow();

    public abstract BufferedImage getImage1();

    public abstract BufferedImage getImage2();

    public abstract void update(ControlPanel panel);

    public abstract void paintComponent(Graphics2D g2);

    public Rectangle2D getObj() {
        return square;
    }
}