import java.awt.*;

public class HitFlash extends Flash {
    private int maxOpacity;
    private Type type;

    public HitFlash (int x, int y, int width, int height, Color color, Type type, int duration, int maxOpacity) {
        super(x, y, width, height, color, duration);
        this.type = type;
        if (maxOpacity > 255 || maxOpacity < 0) {
            try {
                throw new Exception();
            } catch (Exception e){
                System.out.println("Opacity must be between 0 and 255 inclusive");
            }
        }
        this.maxOpacity = maxOpacity;
    }

    public void update(ControlPanel panel) {
        // Animates opacity of flash over time
        color = new Color(type.getRed(), type.getGreen(), type.getBlue(),
                (int) Math.max(Math.min((-Math.abs(-maxOpacity + 2 * maxOpacity * (double) time / (double) duration)) + maxOpacity, maxOpacity), 0));
        if (time > duration) {
            timer.cancel();
            timer.purge();
            ControlPanel.toRemove.add(this);
        }
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(square);
        g2.draw(square);
    }
}
