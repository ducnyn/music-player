package FXStringListMultiViews;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Model model = new Model();

		View view = new View();

		Controller controller = new Controller();
		controller.link(model, view);

		// JavaFX new
		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		primaryStage.show();

		ViewDelete altView = new ViewDelete();

		ControllerDelete delController = new ControllerDelete();
		delController.link(model, altView);

		Stage stage2 = new Stage();
		Scene scene2 = new Scene(altView);
		stage2.setScene(scene2);
		stage2.show();

	}

}
