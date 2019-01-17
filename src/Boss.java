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
        new HitFlash(0, 0, ControlPanel.width, ControlPanel.height, ControlPanel.TRANSPARENT, type, 500, 100);
        if (ControlPanel.rand.nextInt(1000) < ControlPanel.RECRUIT_RATE) {
            ControlPanel.unlockedPokemon[e.p.getIndex()] = true;
            ControlPanel.recruitNotice.addNewRecruit(e.getPokemon().getName());
        }
        ControlPanel.win = true;
        control.incrementScore(ControlPanel.SCORE_FOR_BOSS_KILL);
        timer.cancel();
        timer.purge();
        ControlPanel.enemiesToRemove.add(e);
        control.setBossFight(false);
        Background.setMove(false);
        ControlPanel.boss = null;
    }

    @Override
    public void timer() {
        TimerTask attackTask = new AttackTask();
        TimerTask moveTask = new MoveTask();
        TimerTask imageTask = new ImageTask();
        timer.schedule(attackTask, 0, (int) (1.5 * attack.getAttackDelay() * (ControlPanel.rand.nextInt(200) + 300) / 100));
        timer.schedule(moveTask, 0, (int) (10.0 + p.getMovementSpeed() / 80.0 * 7.0));
        timer.schedule(imageTask, 0, 300);
    }

    // Moves enemies down and moves boss side to side using a parametric function of time
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            try {
                if (y < 0) {
                    y += 1;
                } else {
                    if (Math.abs(Player.getPlayer().getX() + Player.getPlayer().getWidth() / 2 - (x + width / 2)) > 5) {
                        if (Player.getPlayer().getX() + Player.getPlayer().getWidth() / 2 < x + width / 2) {
                            x -= 1;
                        }
                        if (Player.getPlayer().getX() + Player.getPlayer().getWidth() / 2 > x + width / 2) {
                            x += 1;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}