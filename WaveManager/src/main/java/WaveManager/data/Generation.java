package WaveManager.data;

import objectPool.IObjectPool;

public class Generation {

    private int[] generation;
    private String[] enemies;

    private int avgDistance;

    public Generation(String[] enemies){
        this.generation = new int[enemies.length];
        this.enemies = enemies;
        avgDistance = 0;
    }

    public Generation(int[] last){
//        this.generation = new int[enemies.size()];
//        this.enemies = enemies;
    }

    public void launch(IObjectPool objectPool){
//        Pool pool = objectPool.getPool(EntityType.ENEMY);
//
//        //pool.returnEntityToPool();
//        //pool.getEntityFromPool();
//
//        for(int i = 0; i < this.enemies.size(); i++){
//            EnemyComponentSPI type = this.enemies.get(i);
//
//            for(int j = 0; j < this.generation[i]; j++){
//
//
//                Entity enemy = FXGL.entityBuilder()
//                        .type(EntityType.ENEMY)
//                        .at(100, 100)
//                        .viewWithBBox(new Circle(15, Color.RED))
//                        .with(new CollidableComponent(true))
////                        .with(enemyComponent)
//                        .buildAndAttach();
//                pool.returnEntityToPool(enemy);
//            }
//        }

    }

    public int[] getGeneration() {
        return generation;
    }

//    public List<EnemyComponentSPI> getEnemies() {
//        return enemies;
//    }

    public int getAvgDistance() {
        return avgDistance;
    }
}
