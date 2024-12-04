package com.example.ooptankgame.Components;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.time.LocalTimer;
import com.example.ooptankgame.Enums.Dir;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.example.ooptankgame.Enums.TankGameType.*;
/**
 * The PlayerComponent class represents the behavior and properties of the player entity
 * in the tank game. It extends the FXGL Component class and is responsible for handling
 * player movement, shooting mechanics, and collision detection with other entities in the game.
 *
 * Key Responsibilities:
 * - **Movement**: The class allows the player to move in four directions (up, down, left, right)
 *   by updating the entity's position based on user input. It handles movement speed and direction
 *   while ensuring that the player cannot move more than once per frame.
 * - **Shooting**: The player can shoot bullets at a defined interval (0.35 seconds). When shooting,
 *   it spawns a bullet entity in the direction the player is facing.
 * - **Collision Detection**: The component checks for collisions with other entities (such as walls,
 *   enemies, and health packs) during movement. If a collision is detected, it prevents further movement
 *   in that direction.
 * - **Entity Interaction**: It utilizes lazy loading to access groups of entities (like enemies and walls)
 *   for efficient collision checks without unnecessary overhead.
 *
 * This design encapsulates all player-related behaviors in a single component, promoting modularity
 * and reusability within the game's architecture. The component interacts with other parts of the game
 * through the FXGL framework, leveraging its capabilities for entity management and game logic.
 */

public class PlayerComponent extends Component {

    private boolean movedThisFrame = false;
    private double speed = 0;
    private Vec2 velocity = new Vec2();
    public BoundingBoxComponent bbox;
    private LazyValue<EntityGroup> blocks = new LazyValue<>(() -> entity.getWorld().getGroup(ENEMY, WALL, HEALTH_PACK));
    private final LocalTimer shootTimer = FXGL.newLocalTimer();
    private Dir moveDir = Dir.UP;

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 100;
        movedThisFrame = false;
    }
    public void right() {
        if (movedThisFrame) {
            return;
        }
        movedThisFrame = true;
        getEntity().setRotation(90);
        moveDir = Dir.RIGHT;
        move();
    }

    public void left() {
        if (movedThisFrame) {
            return;
        }
        movedThisFrame = true;
        getEntity().setRotation(270);
        moveDir = Dir.LEFT;
        move();

    }

    public void down() {
        if (movedThisFrame) {
            return;
        }
        movedThisFrame = true;
        getEntity().setRotation(180);
        moveDir = Dir.DOWN;
        move();
    }

    public void up() {
        if (movedThisFrame) {
            return;
        }
        movedThisFrame = true;
        getEntity().setRotation(0);
        moveDir = Dir.UP;
        move();
    }

    private void move() {
        if (!getEntity().isActive()) {
            return;
        }
        velocity.set((float) (moveDir.getVector().getX()*speed), (float) (moveDir.getVector().getY()*speed));
        int length = Math.round(velocity.length());
        velocity.normalizeLocal();
        List<Entity> blockList  = blocks.get().getEntitiesCopy();
        for (int i = 0; i < length; i++) {
            entity.translate(velocity.x, velocity.y);
            boolean collision = false;
            for (Entity value : blockList) {
                if (value.getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }
            if (collision) {
                entity.translate(-velocity.x, -velocity.y);
                break;
            }
        }
    }

    public void shoot() {
        if (!shootTimer.elapsed(Duration.seconds(0.35))) {
            return;
        }
        System.out.println("Pew pew");
        spawn("bullet", new SpawnData(getEntity().getCenter().add(-4, -4.5))
                .put("direction", moveDir.getVector())
                .put("owner", entity));
        shootTimer.capture();
    }

}
