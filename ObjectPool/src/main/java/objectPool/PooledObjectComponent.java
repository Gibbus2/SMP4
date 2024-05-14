package objectPool;

import com.almasb.fxgl.entity.component.Component;

public class PooledObjectComponent extends Component {

    private final Pool pool;

    private boolean isPooled;

    public PooledObjectComponent(Pool pool) {
        this.pool = pool;
    }

    public void returnToPool() {
        if (!isPooled) {
            // Moving it so far off-screen that it can't be seen.
            getEntity().setPosition(-10000, -10000);

            isPooled = true;

            pool.returnEntityToPool(getEntity());
        }
    }

    public boolean isPooled() {
        return isPooled;
    }

    public void setIsPooled(boolean b) {
        isPooled = b;
    }
}
