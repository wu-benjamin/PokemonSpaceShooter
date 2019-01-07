import javax.naming.ldap.Control;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TitleHUD extends HUD {

    public TitleHUD(ControlPanel control) {
        super(control);
    }
    public void paintComponent(Graphics2D g2) {
        g2.setColor(ControlPanel.TEXT_BACKGROUND);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawString("Pokemon, The Space Shooter", ControlPanel.width / 2 - 250, ControlPanel.height / 2 - 50);
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
        if (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1)) {
            if (haveStarter()) {
                ControlPanel.toAdd.add(new LevelSelectHUD(control));
            } else {
                ControlPanel.toAdd.add(new StarterSelectHUD(control));
            }
            ControlPanel.toRemove.add(this);
        }
    }
}
