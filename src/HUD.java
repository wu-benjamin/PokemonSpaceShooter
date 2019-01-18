import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

// Handles HUD for the player
public abstract class HUD {

    ControlPanel control;
    static Font font;
    static BufferedImage spaceBackground;
    static BufferedImage heart;
    static BufferedImage leftArrow;
    static BufferedImage rightArrow;
    static {
        URL spaceBackgroundResource = HUD.class.getResource("/Resources/HUD/Space_Background.png");
        URL heartResource = HUD.class.getResource("/Resources/HUD/Heart.png");
        URL leftArrowResource = HUD.class.getResource("/Resources/HUD/LeftArrow.png");
        URL rightArrowResource = HUD.class.getResource("/Resources/HUD/RightArrow.png");
        try {
            spaceBackground = ImageIO.read(new File(spaceBackgroundResource.toURI()));
            heart = ImageIO.read(new File(heartResource.toURI()));
            leftArrow = ImageIO.read(new File(leftArrowResource.toURI()));
            rightArrow = ImageIO.read(new File(rightArrowResource.toURI()));
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static final int BIG_DISPLAY_SCALE = 7;
    static final int SMALL_DISPLAY_SCALE = 4;
    private static final int BORDER_WIDTH = 2;
    public static final int ATTACK_IMAGE_SIZE = 100;
    boolean delay = true;
    Timer timer = new Timer();

    HUD(ControlPanel control) {
        this.control = control;
    }

    public abstract void update(ControlPanel panel);

    public abstract void paintComponent(Graphics2D g2);

    public Timer getTimer() {
        return timer;
    }

    public static void drawCenteredString(Graphics g, Rectangle r, String s,
                             Font font, int... alpha) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);
        String[] lines = s.split("\n");
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        g.setFont(font);
        for (int i = 0; i < lines.length; i++) {
            Rectangle2D r2D = font.getStringBounds(lines[i], frc);
            int rWidth = (int) Math.round(r2D.getWidth());
            int rHeight = (int) Math.round(r2D.getHeight());
            int rX = (int) Math.round(r2D.getX());
            int rY = (int) Math.round(r2D.getY());

            int a = (r.width / 2) - (rWidth / 2) - rX;
            int b = (r.height / 2) - (rHeight / 2) - rY;
            drawBorderedString(g, lines[i], r.x + a, r.y + b - lines.length / 2 * lineHeight + i * lineHeight, alpha);
        }
    }

    static void drawBorderedString(Graphics g, String s, int x, int y, int... alpha) {
        /*
        g.setColor(ControlPanel.TEXT_BACKGROUND);
        g.fill3DRect(x - 15, y - height, width + 30, height + 35, false);
        */
        g.setColor(ControlPanel.TEXT_BORDER);
        if (alpha.length > 0) {
            g.setColor(new Color(ControlPanel.TEXT_BORDER.getRed(), ControlPanel.TEXT_BORDER.getGreen(),
                    ControlPanel.TEXT_BORDER.getBlue(), alpha[0]));
        }
        g.drawString(s, x + BORDER_WIDTH, y + BORDER_WIDTH);
        g.drawString(s, x + BORDER_WIDTH, y - BORDER_WIDTH);
        g.drawString(s, x - BORDER_WIDTH, y + BORDER_WIDTH);
        g.drawString(s, x - BORDER_WIDTH, y - BORDER_WIDTH);
        g.setColor(ControlPanel.TEXT);
        if (alpha.length > 0) {
            g.setColor(new Color(ControlPanel.TEXT.getRed(), ControlPanel.TEXT.getGreen(),
                    ControlPanel.TEXT.getBlue(), alpha[0]));
        }
        g.drawString(s, x, y);
    }

    class DelayTask extends TimerTask {
        @Override
        public void run() {
            try {
                delay = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}