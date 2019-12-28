package ru.kopylov.raindrops.analisys.view;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CameraRotationApp extends Application {

    private Parent createContent() throws Exception {
        Sphere sphere = new Sphere(2.5);
        sphere.setMaterial(new PhongMaterial(Color.FORESTGREEN));

        sphere.setTranslateZ(7);
        sphere.setTranslateX(2);

        Box box = new Box(5, 5, 5);
        box.setMaterial(new PhongMaterial(Color.RED));

        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -50)
        );

        // animate the camera position.
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15),
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(box);
        root.getChildren().add(sphere);

        // set the pivot for the camera position animation base upon mouse clicks on objects
        root.getChildren().stream()
                .filter(node -> !(node instanceof Camera))
                .forEach(node ->
                        node.setOnMouseClicked(event -> {
                            pivot.setX(node.getTranslateX());
                            pivot.setY(node.getTranslateY());
                            pivot.setZ(node.getTranslateZ());
                        })
                );

        // Use a SubScene
        SubScene subScene = new SubScene(
                root,
                300,300,
                true,
                SceneAntialiasing.BALANCED
        );
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);

        return group;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        Scene scene = new Scene(createContent());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}