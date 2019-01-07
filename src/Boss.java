import java.awt.*;
import java.util.TimerTask;

public class Boss extends Enemy {

    public Boss(int x, int y, int width, int height, Color color, Pokemon p, ControlPanel control) {
        super(x, y, width, height, color, p, control);
        // Boss is more powerful
        this.setHitPoints(p.getHitPoints() * ControlPanel.BOSS_HEALTH_COEF);
        this.setMaxHitPoints(this.getHitPoints());
    }

    // Ends stage when boss dies
    @Override
    public void enemyDeath(Enemy e, Type type) {
        new Flash(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, 20, false, type);
        // Handles recruiting new Pokemon -- not yet fully implemented
        if (ControlPanel.rand.nextInt(1000) < ControlPanel.RECRUIT_RATE) {
            ControlPanel.unlockedPokemon[e.p.getIndex()] = true;
        }
        control.incrementScore(100);
        ControlPanel.toRemove.add(e.health);
        e.health = null;
        this.health = null;
        e.timer.cancel();
        e.timer.purge();
        ControlPanel.toRemove.add(e);
        enemies.remove(e);
        System.out.print("You win! You scored: " + control.getScore());
        System.exit(0);
        control.setBossFight(false);
    }

    /*
    // Ensures players can at least damage the opponent regardless of type effectiveness
    public void takeDamage(int damage, Type type) {
        this.setHitPoints(this.getHitPoints() - Math.max(5, damage));
        if (e.getHitPoints() <= 0) {
            e.enemyDeath(e, type);
        }
    }
    */

    @Override
    public void timer() {
        TimerTask attackTask = new AttackTask();
        TimerTask moveTask = new MoveTask();
        TimerTask imageTask = new ImageTask();
        timer.schedule(attackTask, 0, (int) (1.5 * attack.getAttackDelay() * (ControlPanel.rand.nextInt(200) + 300) / 100));
        timer.schedule(moveTask, 0, 1000 / ControlPanel.FRAME_RATE);
        timer.schedule(imageTask, 0, 300);
    }

    // Moves enemies down and moves boss side to side using a parametric function of time
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            try {
                if (e.getY() < 0) {
                    e.setY(e.getY() + 3);
                } else {
                    time++;
                    e.setX(e.getX() + (int) (3 * Math.cos((double) time / 90)));
                }
            } catch (Exception e) {

            }
        }
    }
}