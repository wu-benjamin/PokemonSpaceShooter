import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

public class LevelSelectHUD extends HUD {

    private int level;
    private int numberOfLevels;
    private ArrayList<Pokemon> possibleEncounters;
    private int nextDisplay;
    private boolean containsNewPokemon;

    LevelSelectHUD(ControlPanel control) {
        super(control);
        for (int i = 0; i < ControlPanel.unlockedLocation.length; i++) {
            if (ControlPanel.unlockedLocation[i]) {
                this.level = i;
            }
        }
        this.numberOfLevels = ControlPanel.unlockedLocation.length;
        TimerTask delayTask = new DelayTask();
        timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
        this.possibleEncounters = new ArrayList<>();
        possibleEncounters.add(Location.values()[level].getBoss());
        possibleEncounters.addAll(Arrays.asList(Location.values()[level].getEnemies()));
        this.nextDisplay = 0;
        DisplayTask displayTask = new DisplayTask();
        timer.schedule(displayTask, 0, 500);
        this.containsNewPokemon = false;
        for (int i = 0; i < possibleEncounters.size(); i++) {
            if (!ControlPanel.unlockedPokemon[possibleEncounters.get(i).getIndex()]) {
                this.containsNewPokemon = true;
            }
        }
    }

    private void incrementLevel() {
        level++;
        level %= numberOfLevels;
        /*
        if (!ControlPanel.unlockedPokemon[level]) {
            incrementLevel();
        }
        */
        possibleEncounters.clear();
        possibleEncounters.add(Location.values()[level].getBoss());
        possibleEncounters.addAll(Arrays.asList(Location.values()[level].getEnemies()));

        nextDisplay = 0;
        ControlPanel.clearLevelEncounterDisplay();
        containsNewPokemon = false;
        for (int i = 0; i < possibleEncounters.size(); i++) {
            if (!ControlPanel.unlockedPokemon[possibleEncounters.get(i).getIndex()]) {
                containsNewPokemon = true;
            }
        }
    }

    private void decrementLevel() {
        level += numberOfLevels - 1;
        level %= numberOfLevels;
        /*
        if (!ControlPanel.unlockedPokemon[level]) {
            decrementLevel();
        }
        */
        possibleEncounters.clear();
        possibleEncounters.add(Location.values()[level].getBoss());
        possibleEncounters.addAll(Arrays.asList(Location.values()[level].getEnemies()));
        nextDisplay = 0;
        ControlPanel.clearLevelEncounterDisplay();
        containsNewPokemon = false;
        for (int i = 0; i < possibleEncounters.size(); i++) {
            if (!ControlPanel.unlockedPokemon[possibleEncounters.get(i).getIndex()]) {
                containsNewPokemon = true;
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        FontMetrics metrics = g2.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        g2.drawImage(Location.values()[level].getBackground(), 0, 0, ControlPanel.width, ControlPanel.height, control);
        g2.setColor(ControlPanel.BACKGROUND_TINT);
        g2.fill3DRect(0, 0, ControlPanel.width, ControlPanel.height, false);
        g2.setColor(ControlPanel.TEXT);
        g2.setFont(font);
        drawCenteredString(g2, new Rectangle(0,0,ControlPanel.width,ControlPanel.height), Location.values()[level].getLevelName(), font);
        if (!ControlPanel.unlockedLocation[level]) {
            drawCenteredString(g2, new Rectangle(20, ControlPanel.height - 20 - lineHeight, 200, lineHeight),"Locked", font);
        } else if (containsNewPokemon) {
            drawBorderedString(g2,"Find New Pokémon Here!", 20, ControlPanel.height - 20);
        } else {
            drawBorderedString(g2,"Area Complete!", 20, ControlPanel.height - 20);
        }
        drawCenteredString(g2, new Rectangle(ControlPanel.width - 120, 10, 120, 70), Integer.toString(level), font);
        drawBorderedString(g2, "High Score: " + ControlPanel.highScores[level], 20, 70);
        drawBorderedString(g2, "Max Score: " + (ControlPanel.SCORE_FOR_ENEMY_KILL * (Location.values()[level].getNumberOfEnemies() - 1) + ControlPanel.SCORE_FOR_BOSS_KILL), 20, 150);
        drawBorderedString(g2, "Number of Enemies: " + Location.values()[level].getNumberOfEnemies(), 20, 230);
    }

    public void update(ControlPanel panel) {
        if (!delay) {
            boolean changed = true;
            if (ControlPanel.unlockedLocation[level] && (ControlPanel.input.isKeyDown(KeyEvent.VK_SPACE) || ControlPanel.input.isButtonDown(MouseEvent.BUTTON1))) {
                ControlPanel.location = Location.values()[level];
                timer.cancel();
                timer.purge();
                ControlPanel.clearLevelEncounterDisplay();
                ControlPanel.menusToAdd.add(new LocationIntroHUD(control));
                ControlPanel.menusToRemove.add(this);
                return;
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_LEFT)) {
                decrementLevel();
            } else if (ControlPanel.input.isKeyDown(KeyEvent.VK_RIGHT)) {
                incrementLevel();
            } else {
                changed = false;
            }
            if (changed) {
                delay = true;
                TimerTask delayTask = new DelayTask();
                timer.schedule(delayTask, ControlPanel.MENU_DELAY_TIME);
            }
        }
    }

    // Moves enemies down and moves boss side to side using a parametric function of time
    class DisplayTask extends TimerTask {
        @Override
        public void run() {
            if (ControlPanel.unlockedLocation[level]) {
                ControlPanel.encounterDisplaysToAdd.add(new LocationEncounterDisplay(-possibleEncounters.get(nextDisplay).getWidth() * SMALL_DISPLAY_SCALE - 20,
                        ControlPanel.height * 3 / 4 - possibleEncounters.get(nextDisplay).getHeight() * SMALL_DISPLAY_SCALE / 2,
                        possibleEncounters.get(nextDisplay).getWidth() * SMALL_DISPLAY_SCALE,
                        possibleEncounters.get(nextDisplay).getHeight() * SMALL_DISPLAY_SCALE,
                        ControlPanel.TRANSPARENT, possibleEncounters.get(nextDisplay), control, nextDisplay));
                nextDisplay++;
                if (nextDisplay >= possibleEncounters.size()) {
                    nextDisplay = 0;
                }
            }
        }
    }
}
