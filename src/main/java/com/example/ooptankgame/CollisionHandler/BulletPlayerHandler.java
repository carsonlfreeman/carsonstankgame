package com.example.ooptankgame.CollisionHandler;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import com.example.ooptankgame.Components.PlayerComponent;
import com.example.ooptankgame.Enums.TankGameType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

/*
"BulletPlayerHandler" class is responsible for handling the interaction
between a Bullet (from enemy) colliding with a Player Tank. You could
say that this class uses Observer Pattern as it tells the GUI to update
removing bullet from world and removing Tanks if health is zero.
 */

public class BulletPlayerHandler extends CollisionHandler {

    public BulletPlayerHandler() {
        super(TankGameType.BULLET, TankGameType.PLAYER);
    }
    public void onCollisionBegin(Entity bullet, Entity player) {
        // Spawns an explosion from our Factory
        spawn("explode", bullet.getCenter().getX() - 32, bullet.getCenter().getY() - 32);

        // Removes the bullet from the game world
        bullet.removeFromWorld();

        HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
        hp.damage(1);
        // If a player tanks HP reaches zero the game is over so we display the Game Over.png
        if(hp.isZero() && geti("lives") != 0) {
            inc("lives", -1);
            hp.setValue(hp.getValue() + 2);

        }
        if(hp.isZero() && geti("lives") == 0){
            player.removeFromWorld();
            set("gameOver", true);
            Texture texture = FXGL.getAssetLoader().loadTexture("ui/GameOver.png");
            texture.setTranslateX((32 * 30) / 2);
            texture.setTranslateY((32 * 30) / 2);
            texture.setScaleX(5);
            texture.setScaleY(5);

            Rectangle darkOverlay = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.rgb(0, 0, 0, .7));
            FXGL.getGameScene().addUINode(darkOverlay);
            FXGL.getGameScene().addUINode(texture);
        }
    }

}
