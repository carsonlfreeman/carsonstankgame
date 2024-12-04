package com.example.ooptankgame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.example.ooptankgame.CollisionHandler.*;
import com.example.ooptankgame.Components.PlayerComponent;
import com.example.ooptankgame.Factory.TankEntityFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import kotlin.Unit;
import java.util.Map;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.ooptankgame.Enums.TankGameType.PLAYER;

/**
 * The TankApp class serves as the main application for the tank game, extending the
 * FXGL GameApplication class. It is responsible for initializing game settings,
 * managing game state, handling user input, and setting up the user interface (UI).
 *
 * Key Responsibilities:
 * - **Game Initialization**: Sets up game settings such as title and dimensions,
 *   initializes game variables, and loads the game level from a TMX map file.
 * - **Player Management**: Retrieves and manages the player entity and its associated
 *   component for handling player actions.
 * - **User Interface**: Displays player lives and score on the screen, updating
 *   dynamically based on game events.
 * - **Input Handling**: Maps keyboard inputs to player actions (movement and shooting).
 * - **Collision Handling**: Registers collision handlers to manage interactions
 *   between bullets, players, enemies, and health packs.
 */
public class TankApp extends GameApplication {

    private Entity player; // The player entity
    private PlayerComponent playerComponent; // Component to manage player behavior

    @Override
    protected void initSettings(GameSettings settings) {
        // Set the title and dimensions of the game window
        settings.setTitle("Carsons Tank Game");
        settings.setWidth(32 * 30);
        settings.setHeight(32 * 30);
    }

    @Override
    protected void initGame() {
        // Add entity factory for creating game entities
        getGameWorld().addEntityFactory(new TankEntityFactory());
        // Load the game level from a TMX map file
        setLevelFromMap("tmx/tankgamemapnewwalls.tmx");

        // Retrieve the player component for managing player actions
        playerComponent = getGameWorld().getSingleton(PLAYER).getComponent(PlayerComponent.class);

        // Listener to check when all enemies are destroyed
        getip("destroyedEnemy").addListener((ob, ov, nv) -> {
            if (nv.intValue() == 6) { // Check if 6 enemies have been destroyed
                set("gameOver", true); // Set game over state
                Texture texture = FXGL.getAssetLoader().loadTexture("ui/GameOver.png");
                texture.setTranslateX((32 * 30) / 2); // Center the Game Over texture
                texture.setTranslateY((32 * 30) / 2);
                texture.setScaleX(5);
                texture.setScaleY(5);

                Rectangle darkOverlay = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.rgb(0, 0, 0, .7));
                FXGL.getGameScene().addUINode(darkOverlay); // Add overlay for Game Over effect
                FXGL.getGameScene().addUINode(texture); // Add Game Over texture to scene
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // Initialize game variables such as lives and score
        vars.put("spawnedEnemy", 6);
        vars.put("gameOver", false);
        vars.put("destroyedEnemy", 0);
        vars.put("lives", 3);
        vars.put("score", 0);
    }

    @Override
    protected void initUI() {
        // Create UI elements to display lives and score
        Text livesText = new Text();
        livesText.textProperty().bind(FXGL.getip("lives").asString("Lives: %d"));
        livesText.setFont(Font.font(24));
        livesText.setTranslateX(32);
        livesText.setTranslateY(30);
        livesText.setScaleX(1.5);
        livesText.setScaleY(1.5);
        livesText.setFill(Color.BLACK);

        Text scoreText = new Text();
        scoreText.textProperty().bind(FXGL.getip("score").asString("Score: %d"));
        scoreText.setFont(Font.font(24));
        scoreText.setTranslateX(getAppWidth() - 135);
        scoreText.setTranslateY(30);
        scoreText.setScaleX(1.5);
        scoreText.setScaleY(1.5);
        scoreText.setFill(Color.BLACK);

        FXGL.addUINode(livesText); // Add lives text to UI
        FXGL.addUINode(scoreText); // Add score text to UI
    }

    @Override
    protected void initInput() {
        // Map keyboard inputs to player actions for movement and shooting
        onKey(KeyCode.W, () -> { moveUpAction(); return Unit.INSTANCE; });
        onKey(KeyCode.UP, () -> { moveUpAction(); return Unit.INSTANCE; });

        onKey(KeyCode.S, () -> { moveDownAction(); return Unit.INSTANCE; });
        onKey(KeyCode.DOWN, () -> { moveDownAction(); return Unit.INSTANCE; });

        onKey(KeyCode.A, () -> { moveLeftAction(); return Unit.INSTANCE; });
        onKey(KeyCode.LEFT, () -> { moveLeftAction(); return Unit.INSTANCE; });

        onKey(KeyCode.D, () -> { moveRightAction(); return Unit.INSTANCE; });
        onKey(KeyCode.RIGHT, () -> { moveRightAction(); return Unit.INSTANCE; });

        onKey(KeyCode.SPACE, () -> { shootAction(); return Unit.INSTANCE; });
        onKey(KeyCode.F, () -> { shootAction(); return Unit.INSTANCE; });
    }

    private boolean tankIsReady() {
        return !getb("gameOver"); // Check if the tank is ready to perform actions
    }

    private void shootAction() {
        if (tankIsReady()) {
            playerComponent.shoot(); // Call shoot method from PlayerComponent
        }
    }

    private void moveRightAction() {
        if (tankIsReady()) {
            playerComponent.right(); // Move right if tank is ready
        }
    }

    private void moveLeftAction() {
        if (tankIsReady()) {
            playerComponent.left(); // Move left if tank is ready
        }
    }

    private void moveDownAction() {
        if (tankIsReady()) {
            playerComponent.down(); // Move down if tank is ready
        }
    }

    private void moveUpAction() {
        if (tankIsReady()) {
            playerComponent.up(); // Move up if tank is ready
        }
    }

    @Override
    protected void initPhysics() {
        // Register collision handlers for different interactions in the game world
        getPhysicsWorld().addCollisionHandler(new BulletWallHandler());
        getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new BulletPlayerHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerHealthPackHandler());
        getPhysicsWorld().addCollisionHandler(new EnemyHealthPackHandler());
    }

    public static void main(String[] args) {
        launch(args); // Launch the game application
    }
}
