import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.TimerTask;

public class TitleHUD extends HUD {

    static {
        URL spaceBackgroundResource = HUD.class.getResource("/Resources/Space_Background.png");
        try {
            spaceBackground = ImageIO.read(new File(spaceBackgroundResource.toURI()));
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TitleHUD(ControlPanel control) {
        super(control);
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
    }
    public void paintComponent(Graphics2D g2) {
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        drawCenteredString(g2, new Rectangle(0, 0, ControlPanel.width, ControlPanel.height), "Pokémon, The Space Shooter", font);
    }

    public boolean haveStarter() {
        for (int i = 0; i < ControlPanel.unlockedPokemon.length; i++) {
            if (ControlPanel.unlockedPokemon[i]) {
                return true;
            }
        }
        return false;
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            if (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1)) {
                if (haveStarter()) {
                    ControlPanel.menusToAdd.add(new LevelSelectHUD(control));
                } else {
                    ControlPanel.menusToAdd.add(new StarterSelectHUD(control));
                }
                ControlPanel.menusToRemove.add(this);
            }
            return;
        }
    }
}
