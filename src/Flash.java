import java.awt.*;
import java.awt.geom.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Flash extends GameObject {

    Rectangle2D square;
    int time; // Increases by 1 every ms
    Color color;
    Timer timer = new Timer();
    int duration;

    public Flash(int x, int y, int width, int height, Color color, int duration) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(0, 0, ControlPanel.width, ControlPanel.height);
        this.duration = duration;
        this.time = 0;
        timer();
        ControlPanel.toAdd.add(this);
    }

    public Rectangle2D getObj() {
        return square;
    }

    public abstract void update(ControlPanel panel);

    public abstract void paintComponent(Graphics2D g2);

    public void timer() {
        TimerTask opaqueTask = new OpaqueTask();
        timer.schedule(opaqueTask, 0, 1);
    }

    class OpaqueTask extends TimerTask {
        @Override
        public void run() {
            time++;
        }
    }
}