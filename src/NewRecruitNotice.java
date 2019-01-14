import java.awt.*;
import java.util.ArrayList;

public class NewRecruitNotice extends Flash {

    private static ArrayList<String> newRecruitNames = new ArrayList<String>();
    private static ArrayList<String> newRecruitNamesToAdd = new ArrayList<String>();
    private static ArrayList<String> newRecruitNamesToRemove = new ArrayList<String>();
    private static Font font;
    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ControlPanel.getFontFile()).deriveFont(50f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NewRecruitNotice(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color, 2000);
    }

    public static void addNewRecruit(String newRecruitName) {
        time = 0;
        newRecruitNamesToAdd.add(newRecruitName);
    }

    public void update(ControlPanel panel) {
        if (time > duration) {
            if (newRecruitNames.size() > 0) {
                newRecruitNamesToRemove.add(newRecruitNames.get(0));
                time = 0;
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        if (newRecruitNames.size() > 0) {
            HUD.drawCenteredString(g2, new Rectangle(0, 0, ControlPanel.width, ControlPanel.height), "You Befriended " + newRecruitNames.get(0) + "!", font, 150);
        }
        newRecruitNames.removeAll(newRecruitNamesToRemove);
        newRecruitNames.addAll(newRecruitNamesToAdd);
        newRecruitNamesToAdd.clear();
        newRecruitNamesToRemove.clear();
    }
}
