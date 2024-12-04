package com.example.ooptankgame.CollisionHandler;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.ooptankgame.Enums.TankGameType;
import static com.almasb.fxgl.dsl.FXGL.spawn;

/*
"BulletWallHandler" class is responsible for handling the interaction
between a Bullet (from anyone) colliding with a Wall. You could
say that this class uses Observer Pattern as it tells the GUI to update
removing bullet from world and removing Tanks if health is zero.
 */

public class BulletWallHandler extends CollisionHandler {

    public BulletWallHandler() {
        super(TankGameType.BULLET, TankGameType.WALL);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity wall) {

        // Spawns an explosion from our Factory
        spawn("explode", bullet.getCenter().getX() - 32, bullet.getCenter().getY() - 32);

        // Removes the bullet from the game world
        bullet.removeFromWorld();
    }
}