package main;

import home.HomeController;
import home.HomeModel;
import home.HomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Method;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HomeModel homeModel = new HomeModel();
        HomeController controller = new HomeController();
        HomeView homeView = new HomeView();
        Scene scene = new Scene(homeView,1200,640);
        scene.getStylesheets().add("stylesheets/master.css");
        controller.link(homeModel, homeView);

        Class<Stage> newClass = Stage.class;
        stage.setTitle("Media Player");
        for (Method m: newClass.getDeclaredMethods()){
            System.out.println(m.toString());
        }

        System.out.println(newClass.getMethod("getTitle").invoke(stage));

        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
    }
}
