package FXStringListMultiViews;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class View extends BorderPane {

	private SimpleStringProperty buttonText = new SimpleStringProperty("Enter");
	private Button button = new Button();
	private TextField input = new TextField();

	private ListView<String> list = new ListView<String>();

	public View() {

		HBox box = new HBox(input, button);
		setCenter(list);
		setBottom(box);

		button.textProperty().bind(buttonText);

		// rerender ListCell
		list.setCellFactory(c -> {

			ListCell<String> cell = new ListCell<String>() {
				@Override
				protected void updateItem(String myObject, boolean b) {
					super.updateItem(myObject, myObject == null || b);
					if (myObject != null) {
						setText("..." + myObject + "...");
					} else {
						// wichtig da sonst der Text beim L�schen auch wirklich
						// entfernt wird!
						setText("");
					}
				}

			};
			return cell;

		});
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		button.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public ListView<String> getList() {
		return list;
	}

	public String getInputPaneText() {
		return input.getText();
	}

}
