package com.example.ooptankgame.CollisionHandler;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.ooptankgame.Enums.TankGameType;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.spawn;

/*
"BulletEnemyHandler" class is responsible for handling the interaction
between a Bullet (from player) colliding with an Enemy Tank. You could
say that this class uses Observer Pattern as it tells the GUI to update
removing bullet from world and removing Tanks if health is zero.
 */

public class BulletEnemyHandler extends CollisionHandler {
    public BulletEnemyHandler() {
        super(TankGameType.BULLET, TankGameType.ENEMY);
    }
    protected void onCollisionBegin(Entity bullet, Entity enemy) {
        // Spawns an explosion from our Factory
        spawn("explode", enemy.getCenter().getX() - 32, enemy.getCenter().getY() - 32);

        // Removes the bullet from the game world
        bullet.removeFromWorld();

        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        hp.damage(1);

        // If an enemy tanks HP reaches zero we remove it from the world as its dead
        if(hp.isZero()) {
            enemy.removeFromWorld();
            inc("score", 10);
            inc("destroyedEnemy", 1);
        }
    }

}
