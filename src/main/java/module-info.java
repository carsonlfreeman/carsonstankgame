module com.example.ooptankgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.ooptankgame to javafx.fxml;
    // Loading Images and Animations
    opens assets.levels.tmx;
    opens assets.textures.bullet;
    opens assets.textures.ui;
    opens assets.textures.items;
    opens assets.textures.tanks;
    opens assets.textures.animations;
    // End Load Images and Animations

    exports com.example.ooptankgame;
    exports com.example.ooptankgame.Components;
    opens com.example.ooptankgame.Components to javafx.fxml;
    exports com.example.ooptankgame.CollisionHandler;
    opens com.example.ooptankgame.CollisionHandler to javafx.fxml;
    exports com.example.ooptankgame.Factory;
    opens com.example.ooptankgame.Factory to javafx.fxml;
    exports com.example.ooptankgame.Enums;
    opens com.example.ooptankgame.Enums to javafx.fxml;
}