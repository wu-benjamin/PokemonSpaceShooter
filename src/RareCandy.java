public class RareCandy extends PowerUp {

    RareCandy(int x, int y, int width, int height, ControlPanel control) {
        super(x, y, width, height, control, candyImage);
    }

    public void performEffect(ControlPanel control) {
        control.incrementPower();
        Player.getPlayer().incrementSpeed(1);
        AudioPlayer.playSoundFX(Audio.LEVEL_UP.getAudioIn());
    }
}
