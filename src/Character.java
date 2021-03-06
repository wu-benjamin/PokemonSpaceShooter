import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

// Abstract Pokemon Character for methods shared by player and enemies such as health checks
public abstract class Character extends GameObject {

    ControlPanel control;
    Rectangle2D square;
    Type type1;
    Type type2;
    private int hitPoints;
    private int maxHitPoints;
    private Pokemon p;

    Character(int x, int y, int width, int height, Color color, Pokemon p, ControlPanel control) {
        super(x, y, width, height, color);
        this.square = new Rectangle2D.Double(x,y,width,height);
        this.control = control;
        // this.attack = p.getAttack();
        this.type1 = p.getType1();
        this.type2 = p.getType2();
        this.p = p;
    }

    public Pokemon getPokemon() {
        return p;
    }

    int getHitPoints() {
        return this.hitPoints;
    }

    void setHitPoints(int newHealth) {
        this.hitPoints = Math.max(0, newHealth);
        this.hitPoints = Math.min(this.maxHitPoints, this.hitPoints);
    }

    int getMaxHitPoints() {
        return maxHitPoints;
    }

    void setMaxHitPoints(int newHealth) {
        this.maxHitPoints = Math.max(1, newHealth);
        this.hitPoints = Math.min(this.maxHitPoints, this.hitPoints);
    }

    Type getType1() {
        return type1;
    }

    Type getType2() {
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