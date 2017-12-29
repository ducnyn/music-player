package main;

import home.HomeController;
import home.HomeModel;
import home.HomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        stage.setTitle("Media Player");
        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
    }
}
