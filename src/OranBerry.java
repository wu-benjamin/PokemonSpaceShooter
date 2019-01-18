public class OranBerry extends PowerUp {

    OranBerry(int x, int y, int width, int height, ControlPanel control) {
        super(x, y, width, height, control, berryImage);
    }

    public void performEffect(ControlPanel control) {
        Player.getPlayer().setHitPoints(Player.getPlayer().getHitPoints() + BERRY_HEAL_AMOUNT);
        SoundFX.HEAL.play();
    }
}
