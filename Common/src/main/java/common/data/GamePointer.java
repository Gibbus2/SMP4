package common.data;

import org.javatuples.Pair;

public class GamePointer {

    private Pair<Integer, Integer> pos;

    public GamePointer(){
        pos = new Pair<>(0,0);
    }

    public Pair<Integer, Integer> getPos() {
        return pos;
    }

    public void setPos(Pair<Integer, Integer> pos) {
        this.pos = pos;
    }
}
