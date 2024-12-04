package com.example.ooptankgame.Factory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.ooptankgame.Components.EnemyAIComponent;
import com.example.ooptankgame.Components.PlayerComponent;
import com.example.ooptankgame.Enums.TankGameType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * The TankEntityFactory class is responsible for creating various game entities
 * in the My Tank Game using the FXGL framework. It implements the EntityFactory
 * interface, providing methods to spawn different types of entities such as walls,
 * players, enemies, bullets, health packs, and visual effects (explosions and healing).
 *
 * Each method is annotated with @Spawns to indicate the type of entity it creates,
 * allowing for easy instantiation based on game events. The factory encapsulates
 * the logic for setting up each entity's properties, including health components,
 * visual representations, collision behavior, and animation channels.
 *
 * Key Responsibilities:
 * - Create wall entities with specified dimensions and physical properties.
 * - Spawn player and enemy tanks with health management and AI behavior.
 * - Generate bullet entities with direction and speed, ensuring proper collision handling.
 * - Produce animated explosion and healing effects that clean up after a set duration.
 * - Provide health pack entities that players can collect to restore health.
 *
 * This design promotes modularity and reusability within the game's architecture,
 * making it easier to manage entity creation and extend functionality as needed.
 */

public class TankEntityFactory implements EntityFactory {

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(TankGameType.WALL)
                .viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.rgb(0, 0, 0,0)))
                .bbox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))) // Ensure this matches the rectangle size
                .with(new PhysicsComponent())
                .collidable()
                .build();
    }
    @Spawns("enemySpawnPoint")
    public Entity enemySpawnPoint(SpawnData data) {
        HealthIntComponent hpComponent = new HealthIntComponent(2);
        ProgressBar hpView = new ProgressBar(false);
        hpView.setFill(Color.RED);
        hpView.setMaxValue(2);
        hpView.setWidth(39);
        hpView.setHeight(8);
        hpView.setTranslateY(39);
        hpView.currentValueProperty().bind(hpComponent.valueProperty());
        return FXGL.entityBuilder(data)
                .type(TankGameType.ENEMY)
                .bbox(BoundingShape.box(32, 30))
                .viewWithBBox("tanks/yellowTank.png")
                .with(new EnemyAIComponent())
                .view(hpView)
                .with(hpComponent)
                .collidable()
                .build();
    }
    @Spawns("playerSpawnPoint")
    public Entity newPlayer(SpawnData data) {
        HealthIntComponent hpComponent = new HealthIntComponent(2);
        ProgressBar hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(2);
        hpView.setWidth(39);
        hpView.setHeight(8);
        hpView.setTranslateY(39);
        hpView.currentValueProperty().bind(hpComponent.valueProperty());
        return FXGL.entityBuilder(data)
                .type(TankGameType.PLAYER)
                .bbox(BoundingShape.box(32, 30))
                .view("tanks/greenTank.png")
                .view(hpView)
                .with(new PlayerComponent())
                .with(hpComponent)
                .collidable()
                .build();
    }
    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        double speed = 350;
        Entity owner = data.get("owner");
        CollidableComponent collidableComponent = new CollidableComponent(true);
        collidableComponent.addIgnoredType(owner.getType());
        return FXGL.entityBuilder(data)
                .type(TankGameType.BULLET)
                .viewWithBBox("bullet/bullet.png")
                .scale(2, 2)
                .with(collidableComponent)
                .with(new ProjectileComponent(data.get("direction"), speed))
                .build();
    }
    private final Duration explodeAnimeTime = Duration.seconds(0.5);
    private final Duration healAnimeTime = Duration.seconds(1);
    private  final AnimationChannel explodeAc = new AnimationChannel(FXGL.image("animations/explosion.png"), explodeAnimeTime,9);
    private  final AnimationChannel healAc = new AnimationChannel(FXGL.image("animations/heal.png"), healAnimeTime,11);

    @Spawns("explode")
    public Entity newExplode(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new AnimatedTexture(explodeAc).play())
                .with(new ExpireCleanComponent(explodeAnimeTime))
                .build();
    }

    @Spawns("heal")
    public Entity newHeal(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new AnimatedTexture(healAc).play())
                .scale(2, 2)
                .with(new ExpireCleanComponent(healAnimeTime))
                .build();
    }

        @Spawns("healthpack")
    public Entity newHealthPack(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(TankGameType.HEALTH_PACK)
                .view("items/healthpack.png")
                .bbox(BoundingShape.box(32, 30))
                .collidable()
                .build();
    }
}
