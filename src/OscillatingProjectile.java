import java.util.TimerTask;

public class OscillatingProjectile extends Projectile {

    int time = 0;

    OscillatingProjectile (int x, int y, int size, Attack attack, ControlPanel control, Pokemon... enemyPokemon) {
        super(x, y, size, attack, control, enemyPokemon);
        yComponent = (double) attack.getProjectileSpeed() / 2.0;
        MoveTask moveTask = new MoveTask();
        timer.schedule(moveTask, 0, Math.min(Math.max(9 + (15 - attack.getProjectileSpeed()), 5), 30));
    }
    class MoveTask extends TimerTask {
        @Override
        public void run() {
            time++;
            xComponent = (int) (8.0 * Math.cos((double) time / 8.0));
            rawX += xComponent;
            if (enemyPokemon == null) {
                rawY -= yComponent;
            } else {
                rawY += yComponent;
            }
        }
    }
}
