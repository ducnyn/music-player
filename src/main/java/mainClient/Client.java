package mainClient;

import home.HomeController;
import home.HomeModel;
import home.HomeView;

import interfaces.MediaControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends Application{

    public static void main(String[] args) throws Exception {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {


        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry=LocateRegistry.getRegistry("[ip of server]",5432);
        MediaControl serverController = (MediaControl)Naming.lookup("mediacontrol");


        serverController.link()

        HomeModel homeModel = new HomeModel();
        HomeController controller = new MediaControlClient();
        HomeView homeView = new HomeView();
        Scene scene = new Scene(homeView,1200,640);
        scene.getStylesheets().add("stylesheets/master.css");
        controller.link(homeModel, homeView);

        stage.setTitle("Media Player Client");
        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
    }
}
