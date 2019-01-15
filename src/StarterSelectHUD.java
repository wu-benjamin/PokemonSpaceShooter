import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class StarterSelectHUD extends HUD {

    private int currentStarterDexNumIndex = 0;
    private int[] starterDexNums = {1,4,7};
    boolean delay = false;
    private BufferedImage toShow;
    int width;
    int height;
    int x;
    int y;

    StarterSelectHUD(ControlPanel control) {
        super(control);
        this.toShow = Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getFront1();
        this.width = this.toShow.getWidth() * BIG_DISPLAY_SCALE;
        this.height = this.toShow.getHeight() * BIG_DISPLAY_SCALE;
        this.x = ControlPanel.width / 2 - Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getWidth() * BIG_DISPLAY_SCALE / 2;
        this.y = ControlPanel.height / 4 - Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getHeight() * BIG_DISPLAY_SCALE / 2;
        TimerTask imageTask = new MyImageTask();
        timer.schedule(imageTask, 0, 300);
        TimerTask delayTask = new HUD.DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
    }

    // Animates sprite
    class MyImageTask extends TimerTask {
        @Override
        public void run() {
            double scale = height / toShow.getHeight();
            int orgHeight = height;
            int orgWidth = width;
            if (toShow.equals(Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getFront1())) {
                toShow = Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getFront2();
            } else {
                toShow = Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getFront1();
            }
            width = (int) (toShow.getWidth() * scale);
            height = (int) (toShow.getHeight() * scale);
            x = x + (orgWidth - width) / 2;
            y = y + (orgHeight - height) / 2;
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

    private void incrementStarter() {
        currentStarterDexNumIndex++;
        currentStarterDexNumIndex %= starterDexNums.length;
    }

    private void decrementStarter() {
        currentStarterDexNumIndex += starterDexNums.length - 1;
        currentStarterDexNumIndex %= starterDexNums.length;
    }

    public void paintComponent(Graphics2D g2) {
        g2.drawImage(HUD.spaceBackground, 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        g2.drawImage(toShow, x, y, width, height, control);
        drawCenteredString(g2, new Rectangle(0,ControlPanel.height / 4, ControlPanel.width,
                ControlPanel.height * 3 / 4), Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getName()
                + "\n" + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getType1() + "\n"
                + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getType2(), font);
        g2.drawImage(Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getAttackImage(), ControlPanel.width * 3 / 4 - ATTACK_IMAGE_SIZE / 2,
                ControlPanel.height / 5 - ATTACK_IMAGE_SIZE, ATTACK_IMAGE_SIZE, ATTACK_IMAGE_SIZE, control);
        drawCenteredString(g2, new Rectangle(ControlPanel.width * 3 / 5,ControlPanel.height / 5, ControlPanel.width * 2 / 5, ControlPanel.height * 4 / 5),
                Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getAttackName() +"\n"
                        + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getType().getName()
                        + "\n" + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getAttackPath()
                        + "\nBase Power: " + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getAttackDamage()
                        + "\nSize: " + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getProjectileSize()
                        + "\nSpeed: " + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getProjectileSpeed()
                        + "\nCooldown: " + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttack().getAttackDelay(), font);
        drawCenteredString(g2, new Rectangle(0,ControlPanel.height / 5, ControlPanel.width * 2 / 5, ControlPanel.height * 4 / 5),
                Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getName() + "\nHit Points: " + Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getHitPoints() + "\nAttack: "
                        + (int) Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getAttackPower() +"\nSpeed: " + (int) Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getBaseSpeed(),font);
        drawCenteredString(g2, new Rectangle(ControlPanel.width - 120, 10, 120, 70), Integer.toString(Pokemon.values()[starterDexNums[currentStarterDexNumIndex]].getIndex()), font);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1)) {
                try {
                    ControlPanel.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ControlPanel.unlockedPokemon[starterDexNums[currentStarterDexNumIndex]] = true;
                ControlPanel.menusToAdd.add(new LevelSelectHUD(control));
                ControlPanel.menusToRemove.add(this);
                return;
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementStarter();
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementStarter();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
                TimerTask imageTask = new MyImageTask();
                timer.schedule(imageTask, 0);
            }
        }
    }
}
