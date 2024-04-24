package objectPool;

import com.almasb.fxgl.entity.component.Component;

public class PooledObjectComponent extends Component {

    private final Pool pool;

    public PooledObjectComponent(Pool pool) {
        this.pool = pool;
    }

    public void returnToPool() {
        // Moving it so far off-screen that it can't be seen.
        getEntity().setPosition(-10000, -10000);

        pool.returnEntityToPool(getEntity());
    }
}
