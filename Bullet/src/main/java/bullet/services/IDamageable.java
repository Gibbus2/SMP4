package bullet.services;

public interface IDamageable extends IBullet{
    /*private*/int health = 0;

    public void damage(bullet.services.IBullet bullet);

}
