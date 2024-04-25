package enemy.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import enemy.services.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.List;

public class EnemyComponent extends Component implements EnemyComponentSPI {
    private int hp = 0;
    private int damage = 0;
    private int speed = 0;
    private int currentSpeed;
    private double distanceTraveled = 0;
    private int score = 0;
    int currentWayPoint = 0;
    private List<Point2D> wayPoints;
    public ImageView image = FXGL.getAssetLoader().loadTexture("normalEnemy.png");

    //change private when figured out how to access it
    public StateComponent state;

    public EnemyComponent(int hp, int damage, int speed, int score, List<Point2D> wayPoints){
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.score = score;
        this.wayPoints = wayPoints;
    }
    public EnemyComponent(){
        this.wayPoints = null;
    }

    public StateComponent getState(){
        return state;
    }

    public void setState(StateComponent state) {
        this.state = state;
    }
    @Override
    public EnemyComponent createEnemyComponent(List<Point2D> wayPoints) {
        return new EnemyComponent(hp, damage, speed, score, wayPoints);
    }

    @Override
    public void onAdded() {
        state = entity.getComponent(StateComponent.class);
        state.changeState(MOVING);
        entity.getViewComponent().addChild(image);
    }

    private final EntityState MOVING = new EntityState("MOVING") {
        @Override
        public void onEntering() {
            currentSpeed = speed;
        }
    };
    private final EntityState SLOWED = new EntityState("SLOWED") {
        @Override
        public void onEntering() {
            currentSpeed = speed / 2;
        }
    };

    private final EntityState STUNNED = new EntityState("STUNNED") {
        @Override
        public void onEntering() {
            currentSpeed = 0;
        }
    };
    private final EntityState DEAD = new EntityState("DEAD") {
        @Override
        public void onEntering() {
            currentSpeed = 0;
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
        Point2D velocity = direction.multiply(entity.getComponent(EnemyComponent.class).getCurrentSpeed() * tpf);
        entity.translate(velocity);
        distanceTraveled += velocity.magnitude();

        if(entity.getPosition().distance(target) < 3){
            incrementWayPoint();
        }
    }
    //entity.getComponent(EnemyComponent.class).getState().changeState(entity.getComponent(EnemyComponent.class).getSLOWED()); //this should be how to set state


    @Override
    public ImageView getImage() {
        return image;
    }

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
    public int getCurrentSpeed(){return currentSpeed;}
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
    public double getDistanceTraveled(){
        return distanceTraveled;
    }
    public void setDistanceTraveled(double distanceTraveled){
        this.distanceTraveled = distanceTraveled;
    }
}
