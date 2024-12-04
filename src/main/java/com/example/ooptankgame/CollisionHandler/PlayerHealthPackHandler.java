package com.example.ooptankgame.CollisionHandler;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.ooptankgame.Enums.TankGameType;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

/*
"PlayerHealthPackHandler" class is responsible for handling the interaction
between a Player colliding with a Health Pack. You could
say that this class uses Observer Pattern as it tells the GUI to update
removing bullet from world and removing Tanks if health is zero.
 */

public class PlayerHealthPackHandler extends CollisionHandler {
    public PlayerHealthPackHandler() {
        super(TankGameType.PLAYER, TankGameType.HEALTH_PACK);
    }
    public void onCollisionBegin(Entity player, Entity healthPack) {
        // Spawns a heal from our Factory
        spawn("heal", player.getCenter().getX() - 32, player.getCenter().getY() - 32);

        // Removes the Health Pack from the game world
        healthPack.removeFromWorld();
        inc("score", 5);

        // If a enemy tanks HP isn't already at its max capacity we will increment their health by +1
        HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
        if (hp.getValue() < hp.getMaxValue()) {
            System.out.println("Player health pack collided!");
            hp.setValue(hp.getValue() + 1);
        }
    }

}
