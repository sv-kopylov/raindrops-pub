package ru.kopylov.raindrops.analisys.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.kopylov.raindrops.model.InputDataSet;
import ru.kopylov.raindrops.model.RainSpace;

import java.util.ArrayList;

public class MainView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("raindrops");
//
//        Box box = new Box(50, 70, 100);
//        box.setTranslateX(0);
//        box.setTranslateY(0);
//        box.setTranslateZ(0);

        ArrayList<Sphere> drops = createDrops();
        Sphere[] dropsArray = new Sphere[drops.size()];
        drops.toArray(dropsArray);

        Group root = new Group(dropsArray);

        Scene scene = new Scene(root, 600, 300);


        Translate pivot = new Translate();

        pivot.setX(50);
        pivot.setY(50);
        pivot.setZ(50);

        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-15, Rotate.X_AXIS),
                new Translate(-300, -150, 20)
        );

        scene.setCamera(camera);

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



        //Setting title to the Stage
        stage.setTitle("Drawing a Sphere");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

    }

    public static ArrayList<Sphere> createDrops(){
        RainSpace rainSpace = new RainSpace();
        InputDataSet dataSet = InputDataSet.getInstance();
        for(int i=0;i<dataSet.getSpaceHeight();i++){
            rainSpace.updateTopLayer();
        }

        byte[][][] arr = rainSpace.getSpace();

        ArrayList<Sphere> drops = new ArrayList<>();
        Sphere sphere;
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                for(int k=0; k<arr[0][0].length; k++){
                    if(arr[i][j][k]>0){
                        sphere = new Sphere(2.0);
                        sphere.setTranslateX(k*3);
                        sphere.setTranslateY(j*3);
                        sphere.setTranslateZ(i*3);
                        sphere.setMaterial(new PhongMaterial(Color.AQUA));
                        drops.add(sphere);
                    }
                }
            }
        }
        return drops;
    }


}
