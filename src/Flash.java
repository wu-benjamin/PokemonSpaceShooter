import java.awt.*;
import java.awt.geom.*;
import java.util.Timer;
import java.util.TimerTask;

public class Flash extends GameObject {

    private Rectangle2D square;
    private int opaque = 0;
    private int time;
    private Color color;
    private Timer timer = new Timer();
    private boolean isBomb;
    private Type type;

    public Flash(int x, int y, int width, int height, Color color, int time, boolean isBomb, Type type) {
        super(x, y, width, height, color);
        square = new Rectangle2D.Double(0, 0, ControlPanel.width + 100, ControlPanel.height + 100);
        this.time = time;
        this.isBomb = isBomb;
        this.type = type;
        timer();
        ControlPanel.toAdd.add(this);
    }

    public Rectangle2D getObj() {
        return square;
    }

    public void update(ControlPanel panel) {
        // More dramatic flash for bombs compared to kills
        if (isBomb) {
            // Animates opacity of flash over time
            color = new Color(type.getRed(), type.getGreen(), type.getBlue(), Math.max(255 - Math.abs(opaque - 255), 0));
            if (opaque > 510) {
                timer.cancel();
                timer.purge();
                ControlPanel.toRemove.add(this);
            }
        } else {
            // Animates opacity of flash over time
            color = new Color(type.getRed(), type.getGreen(), type.getBlue(), Math.max(25 - Math.abs(opaque - 25), 0));
            if (opaque > 50) {
                timer.cancel();
                timer.purge();
                ControlPanel.toRemove.add(this);
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(square);
        g2.draw(square);
    }

    public void timer() {
        TimerTask opaqueTask = new OpaqueTask();
        timer.schedule(opaqueTask, 0, 2);
    }

    class OpaqueTask extends TimerTask {
        @Override
        public void run() {
            opaque++;
        }
    }
}