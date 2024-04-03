package enemy.data;

import enemy.services.IState;

import java.util.concurrent.ConcurrentHashMap;

public class EnemyController {
    ConcurrentHashMap<EnemyState, IState> states = new ConcurrentHashMap<>();

}
