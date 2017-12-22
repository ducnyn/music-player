import controller.Controller;
import model.Model;
import view.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        model.Model model = new Model();
        controller.Controller controller = new Controller();
        view.View view = new View();
        Scene mainScene = new Scene(view.getgrid(),740,500);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(740);
        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(740);


        controller.link(view, model);

        primaryStage.setTitle("Media Player");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}