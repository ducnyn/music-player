package FXStringListMultiViews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private ObservableList<String> strings = FXCollections
			.observableArrayList();

	public void addString(String s) {
		strings.add(s);
	}

	public ObservableList<String> getStrings() {
		return strings;
	}

	public void delete() {
		strings.clear();
	}

}
