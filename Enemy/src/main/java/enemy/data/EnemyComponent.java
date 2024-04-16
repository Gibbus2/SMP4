package enemy.data;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import common.services.EnemyComponentSPI;

public class EnemyComponent extends Component implements EnemyComponentSPI {
    private int hp;
    private int damage;
    private int speed;
    private int ds;
    private int score;

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
    public EnemyComponentSPI createEnemyComponent(int hp, int damage, int speed, int score) {
        return new EnemyComponent(hp, damage, speed, score);
    }

    @Override
    public void onAdded() {
        state = entity.getComponent(StateComponent.class);
        state.changeState(MOVING);
    }

    private final EntityState MOVING = new EntityState("MOVING") {
        int ds;
        @Override
        public void onUpdate(double tpf) {
            // to be implemented
        }
        @Override
        public void onEntering() {
            ds = speed;
            PointMovementSystemComponent pointMovementSystem = new PointMovementSystemComponent();
            pointMovementSystem.wayPointSystem(entity);
        }
    };
    private final EntityState SLOWED = new EntityState("SLOWED") {
        int ds;
        @Override
        public void onUpdate(double tpf) {
            // to be implemented
        }
        @Override
        public void onEntering() {
            ds = speed / 2;
        }
    };


    private final EntityState STUNNED = new EntityState("STUNNED") {
        int ds;
        @Override
        public void onUpdate(double tpf) {
            // to be implemented
        }
        @Override
        public void onEntering() {
            ds = 0;
        }
    };
    private final EntityState DEAD = new EntityState("DEAD") {
        int ds;
        @Override
        public void onUpdate(double tpf) {
            // do nothing
        }
        @Override
        public void onEntering() {
            ds = 0;
        }
    };


    public int getHp(){
        return hp;
    }

    public int getDamage(){
        return damage;
    }

    public int getSpeed(){
        return speed;
    }

    public int getScore(){
        return score;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
