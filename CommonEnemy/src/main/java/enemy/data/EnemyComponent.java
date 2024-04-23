package enemy.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import enemy.services.EnemyComponentSPI;
import javafx.geometry.Point2D;

import java.util.List;

public class EnemyComponent extends Component implements EnemyComponentSPI {
    private int hp;
    private int damage;
    private int speed;
    private int ds;
    private int score;
    int currentWayPoint = 0;
    private List<Point2D> wayPoints = map.Waypoint.fromPolyline().getWaypoints();

    //change private when figured out how to access it
    public StateComponent state;

    public EnemyComponent(int hp, int damage, int speed, int score){
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.score = score;
    }

    public EnemyComponent() {
        this(0,0,0,0);
    }

    public StateComponent getState(){
        return state;
    }

    public void setState(StateComponent state) {
        this.state = state;
    }
    public EnemyComponent createEnemyComponent() {
        return new EnemyComponent(hp, damage, speed, score);
    }

    @Override
    public void onAdded() {
        state = entity.getComponent(StateComponent.class);
        state.changeState(MOVING);
    }

    private final EntityState MOVING = new EntityState("MOVING") {
        @Override
        public void onEntering() {
            ds = speed;
        }
    };
    private final EntityState SLOWED = new EntityState("SLOWED") {
        @Override
        public void onEntering() {
            ds = speed / 2;
        }
    };

    private final EntityState STUNNED = new EntityState("STUNNED") {
        @Override
        public void onEntering() {
            ds = 0;
        }
    };
    private final EntityState DEAD = new EntityState("DEAD") {
        @Override
        public void onEntering() {
            ds = 0;
        }
    };
    @Override
    public void onUpdate(double tpf) {
        if(currentWayPoint >= wayPoints.size()){
            entity.removeFromWorld();
            FXGL.getEventBus().fireEvent(new EnemyReachedEndEvent());
            //uhhhhh send data to take dmg yes
            return;
        }
        //System.out.println(wayPoints);
        Point2D target = wayPoints.get(currentWayPoint);
        //System.out.println("Speed cuh: "+entity.getComponent(EnemyComponent.class).getDs());
        Point2D direction = target.subtract(entity.getPosition()).normalize();
        Point2D velocity = direction.multiply(entity.getComponent(EnemyComponent.class).getDs() * tpf);
        entity.translate(velocity);

        if(entity.getPosition().distance(target) < 3){
            incrementWayPoint();
        }
    }
    //entity.getComponent(EnemyComponent.class).getState().changeState(entity.getComponent(EnemyComponent.class).getSLOWED()); //this should be how to set state

    public int getHp(){
        return hp;
    }
    public int getDamage(){
        return damage;
    }
    public int getSpeed(){
        return speed;
    }
    public void incrementWayPoint() {
        currentWayPoint++;
    }
    public int getDs(){return ds;}
    public int getScore(){
        return score;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public EntityState getDEAD() {
        return DEAD;
    }
    public EntityState getMOVING() {
        return MOVING;
    }
    public EntityState getSLOWED() {
        return SLOWED;
    }
    public EntityState getSTUNNED() {
        return STUNNED;
    }
}
