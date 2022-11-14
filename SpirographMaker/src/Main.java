import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ArrayList<Double> dots = generateDots();
        initUI(stage, dots);
    }

    private void initUI(Stage stage, ArrayList<Double> dots) {

        StackPane root = new StackPane();

        //Creating a line object
        Polyline poly = new Polyline();
        poly.setStroke(Color.BLACK);
        root.getChildren().add(poly);

        AnimationTimer timer = new MyTimer(poly, dots);
        timer.start();

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("SpirographMaker");
        stage.setScene(scene);
        stage.show();


    }

    private class  MyTimer extends AnimationTimer {
        int frame = 0;
        Polyline poly;
        ArrayList<Double> dots;

        public MyTimer(Polyline poly, ArrayList<Double> dots) {
            this.poly = poly;
            this.dots = dots;
        }

        @Override
        public void handle(long arg0) {

            poly.getPoints().addAll(
                    new Double[] {dots.get(frame*2), dots.get(frame*2 + 1)}
            );

            frame ++;

        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    private static ArrayList<Double> generateDots() {
        int R = 144;
        int r = 78;
        int O = 48;

        ArrayList<Double> dots = new ArrayList<Double>();

        for (float t=0; t < 20000; t += 0.1) {
            float x = (float) ((R-r)*Math.cos(t) + O*Math.cos(((float)(R-r)/r)*t));
            float y = (float) ((R-r)*Math.sin(t) - O*Math.sin(((float)(R-r)/r)*t));
            dots.add((double) x);
            dots.add((double) y);
        }

        return dots;
    }
}