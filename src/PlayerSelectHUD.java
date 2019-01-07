import javax.naming.ldap.Control;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerSelectHUD extends HUD {

    int player;
    int numberOfPokemon;
    boolean delay = false;
    Timer timer = new Timer();

    public PlayerSelectHUD(ControlPanel control) {
        super(control);
        for (int i = 0; i < ControlPanel.unlockedPokemon.length; i++) {
            if (ControlPanel.unlockedPokemon[i]) {
                this.player = i;
            }
        }
        this.numberOfPokemon = ControlPanel.unlockedPokemon.length;
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void incrementPlayer() {
        player++;
        player %= numberOfPokemon;
        /*
        if (!ControlPanel.unlockedPokemon[player]) {
            incrementPlayer();
        }
        */
    }

    public void decrementPlayer() {
        player += numberOfPokemon - 1;
        player %= numberOfPokemon;
        /*
        if (!ControlPanel.unlockedPokemon[player]) {
            decrementPlayer();
        }
        */
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(ControlPanel.TEXT_BACKGROUND);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawImage(Pokemon.values()[player].getFront1(), ControlPanel.width / 2 - Pokemon.values()[player].getWidth() * ControlPanel.PLAYER_SCALE / 2,
                ControlPanel.height / 2 - Pokemon.values()[player].getHeight() * ControlPanel.PLAYER_SCALE / 2,
                Pokemon.values()[player].getWidth() * ControlPanel.PLAYER_SCALE, Pokemon.values()[player].getHeight() * ControlPanel.PLAYER_SCALE, control);
        if (!ControlPanel.unlockedPokemon[player]) {
            g2.drawString("LOCKED", 20, ControlPanel.height - 100);
        }
        //g2.drawString("Pokemon, The Space Shooter", ControlPanel.width / 2 - 250, ControlPanel.height / 2 - 50);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedPokemon[player] && (panel.input.isKeyDown(KeyEvent.VK_SPACE) || panel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.player = Pokemon.values()[player];
                ControlPanel.toAdd.add(new PlayingHUD(control));
                ControlPanel.toRemove.add(this);
            } else if (panel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementPlayer();
            } else if (panel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementPlayer();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY);
            }
        }
    }
}
