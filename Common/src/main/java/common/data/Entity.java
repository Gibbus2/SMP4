package common.data;

import org.javatuples.Pair;

import java.util.UUID;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private Pair<Double, Double> pos;
    private double rotation;

    public UUID getID() {
        return ID;
    }

    public double getRotation() {
        return rotation;
    }

    public Pair<Double, Double> getPos() {
        return pos;
    }

    public void setPos(Pair<Double, Double> pos) {
        this.pos = pos;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
