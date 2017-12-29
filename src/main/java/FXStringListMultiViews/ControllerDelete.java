package FXStringListMultiViews;

public class ControllerDelete {

	public void link(Model model, ViewDelete view) {
		view.getList().setItems(model.getStrings());
		view.addEventHandler(e -> {
			model.delete();
		});
	}

}
