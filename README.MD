# Carsons Tank Game

## Introduction
**My Tank Game** is a Java-based program developed using the **FXGL** framework, which operates on top of standard JavaFX. FXGL (FX Game Library) is a 2D game development framework that simplifies the process of game creation in Java. By adding a simple dependency to your Maven project, FXGL makes coding much more user-friendly for newcomers.

## UML Diagram
![image description](https://github.com/carsonlfreeman/carsonstankgame/blob/main/carsontankgameumldiagram.png?raw=true)

## Design Patterns Used 
In this Java-based tank game project, I've employed various object-oriented design patterns to enhance the codebase's structure, improve modularity, and facilitate maintenance and scalability. Below, we detail some of the primary design patterns utilized throughout the project.

### Singleton Pattern
The Tank Game implements Singleton pattern to manage global state and configurations. This ensures there is only one instance of the game or certain components running at any time, which is crucial for consistency across the game's execution. As well as we use Singleton to create our singler PLAYER.

### Factory Method Pattern
The `TankEntityFactory` class demonstrates the Factory Method pattern, centralizing the creation of game entities such as walls, players, enemies, and bullets. This pattern encapsulates the instantiation processes, making the entity creation adaptable and isolated from other code, thereby reducing class coupling and centralizing object creation.

### Component Pattern
The Tank Game leverages the Component pattern extensively through classes like `PlayerComponent` and `EnemyAIComponent`. These components are used to extend the behavior of game entities dynamically, allowing us to compose behaviors (e.g., movement and shooting) rather than inheriting from base or parent classes. This approach avoids deep inheritance hierarchies and enhances flexibility and reusability.

### Observer Pattern
I used the Observer pattern to handle events such as collisions and game state changes effectively. Classes such as `BulletPlayerHandler` and `BulletEnemyHandler` serve as observers to collision events between different game entities, allowing our game to manage state changes and interactions loosely coupled, promoting more manageable and modular event-driven architecture.

### Strategy Pattern
Though not implemented as traditional strategies, the behaviors encapsulated in components like `PlayerComponent` and `EnemyAIComponent` align with the Strategy pattern. These behaviors, which can be dynamically changed during runtime, allow game entities to adapt their actions and responses to the game environment, facilitating complex game logic and interactive gameplay dynamics.


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

1. Download the jar executable labeled OOPTankGame-1.0-SNAPSHOT.jar 

https://github.com/carsonlfreeman/carsonstankgame/blob/main/Jar/OOPTankGame-1.0-SNAPSHOT.jar

2. Locate your path to the JavaFX SDK `lib` directory and copy it to your clipboard.
3. Locate your jar file 
4. Replace "/path/to/javafx-sdk-21/lib" with your path and run the following command (assuming you're inside the directory where you downloaded the .jar):
   ```bash
   java --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml -jar OOPTankGame-1.0-SNAPSHOT.jar
   ```
