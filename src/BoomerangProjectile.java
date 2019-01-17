import java.awt.*;
import java.util.TimerTask;

public class BoomerangProjectile extends Projectile {

    int boomerangIndex;
    int time = 0;

    BoomerangProjectile(int x, int y, int size, Attack attack, ControlPanel control, int boomerangIndex, Pokemon... enemyPokemon) {
        super(x, y, size, attack, control, enemyPokemon);
        this.boomerangIndex = boomerangIndex;
        MoveTask moveTask = new MoveTask();
        timer.schedule(moveTask, 0, Math.min(Math.max(17 + (15 - attack.getProjectileSpeed()), 5), 30));
    }

    class MoveTask extends TimerTask {
        @Override
        public void run() {
            time++;
            double timeTemp = (double) time / 10.0 + 1.0;
            switch (boomerangIndex) {
                case 0:
                    xComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    yComponent = -(-2 * timeTemp + 10);
                    break;
                case 1:
                    xComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    yComponent = -(-2 * timeTemp + 10);
                    break;
                case 2:
                    yComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    xComponent = (-2 * timeTemp + 10);
                    break;
                case 3:
                    yComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    xComponent = -(-2 * timeTemp + 10);
                    break;
                /*
                case 4:
                    xComponent = (- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    yComponent = (-2 * timeTemp + 10);
                    break;
                case 5:
                    xComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    yComponent = (-2 * timeTemp + 10);
                    break;
                case 6:
                    yComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    xComponent = (-2 * timeTemp + 10);
                    break;
                case 7:
                    yComponent = -(- 5 / 2) * Math.pow(timeTemp, Math.cos(timeTemp / 4) - 1) * (timeTemp * Math.log10(timeTemp) * Math.sin(timeTemp / 4) - 4 * Math.cos(timeTemp / 4));
                    xComponent = -(-2 * timeTemp + 10);
                    break;
                */
                default:
                    try {
                        throw new IndexOutOfBoundsException();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid boomerang index given.");
                }
                    break;
            }
            rawX += xComponent;
            if (enemyPokemon == null) {
                rawY -= yComponent;
            } else {
                rawY += yComponent;
            }
        }
    }
}