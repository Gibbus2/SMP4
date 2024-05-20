package common.data;

public class GameData {
    private int displayWidth  = 1440;
    private int displayHeight = 810;

    public boolean debug = false;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    public boolean getDebug() {
        return debug;
    }


    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

}
