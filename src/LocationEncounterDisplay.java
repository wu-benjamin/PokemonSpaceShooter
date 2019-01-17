import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class LocationEncounterDisplay extends Character {

    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage toShow;
    private final int X_COMPONENT = 4;
    private final int Y_COMPONENT = 0;
    private int origY;
    private int encounterTableIndex;
    Timer timer = new Timer();
    Pokemon p;

    LocationEncounterDisplay(int x, int y, int width, int height, Color color, Pokemon p, ControlPanel control, int encounterTableIndex) {
        super(x, y, width, height, color, p, control);
        this.color = color;
        this.control = control;
        this.image1 = p.getFront1();
        this.image2 = p.getFront2();
        this.type1 = p.getType1();
        this.type2 = p.getType2();
        this.encounterTableIndex = encounterTableIndex;
        this.p = p;
        this.square = new Rectangle2D.Double(x, y, this.width, this.height);
        this.origY = y;
        toShow = image1;
        timer();
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) {
        if (x < 0 - width - 500 || x > ControlPanel.width + 500 || y > ControlPanel.height) {
            ControlPanel.encounterDisplaysToRemove.add(this);
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public BufferedImage getToShow() {
        return toShow;
    }

    @Override
    public BufferedImage getImage1() {
        return image1;
    }

    @Override
    public BufferedImage getImage2() {
        return image2;
    }

    public void paintComponent(Graphics2D g2) {
        square.setFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g2.setColor(color);
        g2.fill(square);
        g2.draw(square);
        g2.drawImage(toShow, this.getX(), this.getY(), this.getWidth(), this.getHeight(), control);
        if (encounterTableIndex == 0) {
            g2.drawImage(Attack.HYPERBEAM.getAttackImage(), this.getX() + this.getWidth() / 2 - 10, origY - 20, 20, 20, control);
        }
        if (p.get) {

        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void timer() {
        TimerTask moveTask = new MoveTask();
        TimerTask imageTask = new ImageTask();
        timer.schedule(moveTask, 0, 13);
        timer.schedule(imageTask, 0, 300);
    }

    public Timer getTimer() {
        return timer;
    }

    // Moves enemies down and moves boss side to side using a parametric function of time
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            try {
                x += X_COMPONENT;
                y += Y_COMPONENT;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Animates enemy sprites
    class ImageTask extends TimerTask {
        @Override
        public void run() {
            double scale = height / toShow.getHeight();
            int orgHeight = height;
            int orgWidth = width;
            if (toShow.equals(image1)) {
                toShow = image2;
            } else {
                toShow = image1;
            }
            width = (int) (toShow.getWidth() * scale);
            height = (int) (toShow.getHeight() * scale);
            x += (orgWidth - width) / 2;
            y += (orgHeight - height) / 2;
        }
    }
}