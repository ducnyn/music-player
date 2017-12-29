package FXStringListMultiViews;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ViewDelete extends BorderPane {

	private SimpleStringProperty buttonText = new SimpleStringProperty("Delete");
	private Button button = new Button("Delete");

	private ListView<String> list = new ListView<String>();

	public ViewDelete() {

		HBox box = new HBox(button);
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
						setGraphic(new Button());
						setText("..." + myObject + "...");
					} else {
						setGraphic(null);
						// wichtig da sonst der Text beim L�schen auch wirklich
						// entfernt wird!
						setText("");
					}
				}

			};
			cell.setContentDisplay(ContentDisplay.LEFT);
			return cell;

		});
	}

	public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
		button.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public ListView<String> getList() {
		return list;
	}

}
