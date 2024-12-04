package com.example.ooptankgame.Components;

import com.almasb.fxgl.core.math.FXGLMath;
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
import java.util.Random;
import static com.example.ooptankgame.Enums.TankGameType.*;
/**
 * The EnemyAIComponent class defines the behavior of enemy entities in the tank game.
 * It extends the FXGL Component class and is responsible for managing enemy movement,
 * shooting mechanics, and collision detection with other entities in the game.
 *
 * Key Responsibilities:
 * - **Movement**: The class implements simple AI movement logic, allowing enemies to
 *   change direction randomly while avoiding downward movement too frequently. The
 *   enemies will move in one of four directions (up, down, left, right) based on a
 *   probability mechanism.
 * - **Shooting**: Enemies can shoot bullets at defined intervals (every 0.35 seconds)
 *   based on random chance. The shooting direction aligns with the current movement direction.
 * - **Collision Detection**: During movement, the component checks for collisions with
 *   other entities (such as walls and players). If a collision is detected, it prevents
 *   further movement in that direction and may trigger shooting or change direction.
 * - **Entity Interaction**: It utilizes lazy loading to efficiently access groups of entities
 *   (like players and walls) for collision checks without incurring unnecessary overhead.
 *
 * This design encapsulates all enemy-related behaviors within a single component, promoting
 * modularity and reusability in the game's architecture. The component interacts with other
 * parts of the game through the FXGL framework, leveraging its capabilities for entity management
 * and game logic.
 */

public class EnemyAIComponent extends Component {

    public BoundingBoxComponent bbox;
    private LocalTimer shootTimer = FXGL.newLocalTimer();
    private Random random = new Random();
    private double speed = 0;
    private Dir moveDir;
    private LazyValue<EntityGroup> blocks = new LazyValue<>(() -> entity.getWorld().getGroup(PLAYER, WALL, HEALTH_PACK));
    private boolean canMove;
    private static Dir[] dirs = Dir.values();

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 100;

        // Change direction based on probability but avoid downward bias
        if (moveDir == Dir.UP && random.nextInt(1000) > 880) {
            moveDir = dirs[random.nextInt(4)];
        } else if (random.nextInt(1000) > 980) {
            // Avoid moving down too frequently
            Dir newDir;
            do {
                newDir = dirs[random.nextInt(4)];
            } while (newDir == Dir.DOWN); // Prevent moving down too often
            moveDir = newDir;
        }

        setMoveDir(moveDir);

        // Random shooting logic remains unchanged
        if (random.nextInt(1000) > 980) {
            shoot();
        }
    }

    public void setMoveDir(Dir moveDir) {
        this.moveDir = moveDir;
        switch (moveDir) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
            default -> {
            }
        }
    }
    public void shoot() {
        if (!shootTimer.elapsed(Duration.seconds(.35))){
            return;
        }
        FXGL.spawn("bullet", new SpawnData(getEntity().getCenter().add(-4, -4))
                .put("direction", moveDir.getVector())
                .put("owner", entity)
        );
        shootTimer.capture();
    }
    private void right() {
        getEntity().setRotation(90);
        move();
    }

    private void left() {
        getEntity().setRotation(270);
        move();

    }

    private void down() {
        getEntity().setRotation(180);
        move();

    }

    private void up() {
        getEntity().setRotation(0);
        move();

    }
    @Override
    public void onAdded() {
        moveDir = dirs[random.nextInt(dirs.length)];
    }
    private Vec2 velocity = new Vec2();

    private void move() {
        velocity.set((float) (moveDir.getVector().getX()*speed), (float) (moveDir.getVector().getY()*speed));
        int length = Math.round(velocity.length());
        velocity.normalizeLocal();

        List<Entity> blockList = blocks.get().getEntitiesCopy();
        for (int i = 0; i < length; i++) {
            entity.translate(velocity.x, velocity.y);
            boolean collision = false;
            Entity entityTemp;
            for (int j = 0; j < blockList.size(); j++) {
                entityTemp = blockList.get(j);
                if (entityTemp == entity) {
                    continue;
                }
                if (entityTemp.getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }
            if (collision) {
                entity.translate(-velocity.x, -velocity.y);
                if (FXGLMath.randomBoolean(0.6)) {
                    shoot();
                }
                if (FXGLMath.randomBoolean(0.3)) {
                    setMoveDir(Dir.values()[random.nextInt(4)]);
                }

                break;
            }
        }
    }
}
