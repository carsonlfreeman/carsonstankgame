package com.example.ooptankgame.CollisionHandler;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.ooptankgame.Enums.TankGameType;
import static com.almasb.fxgl.dsl.FXGL.spawn;

/*
"EnemyHealthPackHandler" class is responsible for handling the interaction
between an Enemy colliding with a Health Pack. You could
say that this class uses Observer Pattern as it tells the GUI to update
removing bullet from world and removing Tanks if health is zero.
 */

public class EnemyHealthPackHandler extends CollisionHandler {
    public EnemyHealthPackHandler() {
        super(TankGameType.ENEMY, TankGameType.HEALTH_PACK);
    }

    public void onCollisionBegin(Entity enemy, Entity healthPack) {
        // Spawns a heal from our Factory
        spawn("heal", enemy.getCenter().getX() - 32, enemy.getCenter().getY() - 32);

        // Removes the Health Pack from the game world
        healthPack.removeFromWorld();

        // If a enemy tanks HP isn't already at its max capacity we will increment their health by +1
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        if (hp.getValue() < hp.getMaxValue()) {
            System.out.println("Enemy health pack collided!");
            hp.setValue(hp.getValue() + 1);
        }
    }
}