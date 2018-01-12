package mainServer;

import home.HomeController;
import home.HomeModel;
import home.HomeView;

import interfaces.MediaControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends Application{

    public static void main(String[] args) throws RemoteException, MalformedURLException {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new NullSecurityManager());
        }
        HomeController homeController  = new MediaControlServer();
        Registry registry = LocateRegistry.createRegistry(5432);
        HomeController stub = (HomeController) UnicastRemoteObject.exportObject(homeController, 0);
        registry.rebind("mediacontrol", homeController);
//        Naming.rebind("//localhost:1099/MediaControl", homeController);
        System.out.println("MediaControlServer started...");

        HomeModel homeModel = new HomeModel();
        HomeView homeView = new HomeView();
        Scene scene = new Scene(homeView,1200,640);
        scene.getStylesheets().add("stylesheets/master.css");
        homeController.link(homeModel, homeView);

        stage.setTitle("Media Player Server");
        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        HomeModel homeModel = new HomeModel();
//        HomeController controller = new MediaControlServer();
//        HomeView homeView = new HomeView();
//        Scene scene = new Scene(homeView,1200,640);
//        scene.getStylesheets().add("stylesheets/master.css");
//        controller.link(homeModel, homeView);
//
//        stage.setTitle("Media Player Server");
//        stage.setMinHeight(480);
//        stage.setMinWidth(800);
//        stage.setScene(scene);
//        stage.show();
//    }
}
