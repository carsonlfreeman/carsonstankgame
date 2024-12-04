# My Tank Game

## Introduction
**My Tank Game** is a Java-based program developed using the **FXGL** framework, which operates on top of standard JavaFX. FXGL (FX Game Library) is a 2D game development framework that simplifies the process of game creation in Java. By adding a simple dependency to your Maven project, FXGL makes coding much more user-friendly for newcomers.

## Repository Structure
```bash
OOPTankGame/
├── main/java/com/example/ooptankgame 
│   ├── CollisionHandler/    # Houses the handlers for interactions between two Entities
│   ├── Components/           # Houses the PlayerComponent and EnemyAIComponent
│   ├── Enums/                # Houses the Enum types for the game 
│   ├── Factory/              # Houses the Tank Game Entity Factory
│   └── TankGameApp           # Main entry point of the application  
└── resources/
    ├── assets/
    │   ├── levels/tmx/       # Houses the map TMX files and associated sprites 
    │   └── textures/
    │       ├── animations/    # Houses animations such as explosions and healing
    │       ├── bullet/        # Houses images for missiles
    │       ├── items/         # Houses Health Pack images 
    │       ├── tanks/         # Houses images for both player and enemy tanks
    │       └── ui/            # Houses interface images 
```

## Key Features

### 1. Tank Movement
- Implements "WASD" movement controls as well as Arrow Key navigation.
- Handles collisions with walls, inner walls, trees, and enemy tanks.

### 2. Missile Mechanics
- Both players and enemies can fire missiles that trigger an explosion sprite upon impact.
- Players can fire missiles by pressing either the "F" key or the "Space" bar.

### 3. Player Health
- Both players and enemies start with a health value of 2; missile collisions reduce health by 1.

### 4. Player Lives
- Players have a total of 3 lives; running out of lives results in game over.

### 5. Health Packs
- There are three Health Packs on the map that can be used by both players and enemies, increasing health by +1 upon collision.

### 6. AI Enemy Tanks
- Enemy tanks utilize random number generation to determine their movement and shooting intervals, providing a surprisingly challenging gameplay experience.

## Running the Program

1. Locate your path to the JavaFX SDK `lib` directory.
2. Once you have copied that path, run the following command:
   ```bash
   java --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml -jar OOPTankGame-1.0-SNAPSHOT.jar
   ```
