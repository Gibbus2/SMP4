package enemy.data;

import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;

public class EnemyComponent extends com.almasb.fxgl.entity.component.Component{
    private int hp;
    private int damage;
    private int speed;
    private int score;

    private StateComponent state;

    public EnemyComponent(int hp, int damage, int speed, int score){
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.score = score;
    }

    @Override
    public void onAdded() {
        state = entity.getComponent(StateComponent.class);
        state.changeState(MOVING);
    }

    private final EntityState MOVING = new EntityState("MOVING") {
        @Override
        public void onUpdate(double tpf) {
            // to be implemented
        }
        @Override
        public void onEntering() {
            speed = 10;
        }
    };
    private final EntityState SLOWED = new EntityState("SLOWED") {
        @Override
        public void onUpdate(double tpf) {
            // to be implemented
        }
        @Override
        public void onEntering() {
            speed = 5;
        }
    };
    private final EntityState STUNNED = new EntityState("STUNNED") {
        @Override
        public void onUpdate(double tpf) {
            // to be implemented
        }
        @Override
        public void onEntering() {
            speed = 0;
        }
    };
    private final EntityState DEAD = new EntityState("DEAD") {
        @Override
        public void onUpdate(double tpf) {
            // do nothing
        }
        @Override
        public void onEntering() {
            speed = 0;
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
