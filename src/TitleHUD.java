import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TitleHUD extends HUD {

    public TitleHUD(ControlPanel control) {
        super(control);
    }
    public void paintComponent(Graphics2D g2) {
        /*
        Font currentFont = g2.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
        g2.setFont(newFont);
        */
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
