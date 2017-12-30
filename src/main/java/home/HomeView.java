package home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

public class HomeView extends VBox{

    boolean isPlaying = false;

            Label libraryLabel = new Label("Library");
            Label playlistLabel = new Label("Playlists");
            ListView<interfaces.Playlist> playlistChooser = new ListView();
        VBox listBox = new VBox(libraryLabel,playlistLabel,playlistChooser);
            ListView<interfaces.Song> mainList = new ListView(); //wieso Interfaces.Song?
        VBox mainBox = new VBox(mainList);
            ListView details = new ListView();
        VBox detailsBox = new VBox(details);
    HBox globalHBox = new HBox(listBox, mainBox, detailsBox);


            Button playButton = new Button("", new ImageView(new Image("/mediabuttons/play.png")));//https://www.flaticon.com/authors/vaadin
            Button prevButton = new Button("", new ImageView(new Image("/mediabuttons/prev.png")));
            Button skipButton = new Button("", new ImageView(new Image("/mediabuttons/skip.png")));
    HBox mediaButtons = new HBox(prevButton, playButton, skipButton);



        MenuItem addLibrary = new MenuItem("Add Library...");
    ContextMenu libraryContext = new ContextMenu(addLibrary);
        MenuItem createPlaylist = new MenuItem("create Playlist...");
    ContextMenu playlistContext = new ContextMenu(createPlaylist);
        Menu addToPlaylist = new Menu("Add to Playlist");
        MenuItem removeFromPlaylist = new MenuItem("Remove from this Playlist");
    ContextMenu mainListContext = new ContextMenu(addToPlaylist, removeFromPlaylist);




    public HomeView(){

        this.getChildren().addAll( globalHBox, mediaButtons);

        VBox.setVgrow(globalHBox, Priority.ALWAYS);
        VBox.setVgrow(mediaButtons, Priority.NEVER);

        VBox.setVgrow(playlistChooser, Priority.ALWAYS);

        HBox.setHgrow(mainBox, Priority.ALWAYS);
        VBox.setVgrow(mainList, Priority.ALWAYS);

        listBox.setMinWidth(220);
        listBox.setMaxWidth(220);
        listBox.setPadding(new Insets(20,0,0,20));
        listBox.setSpacing(20);
        listBox.getStyleClass().add("menu-list");
        playlistChooser.getStyleClass().add("menu-list");

        mainBox.setPadding(new Insets(-1,0,-1,0));
        mainList.getStyleClass().add("main-list");
        mainList.setPadding(new Insets(15, 1, 0, 1));

        detailsBox.setMinWidth(220);
        detailsBox.setMaxWidth(220);
        detailsBox.getStyleClass().add("menu-list");
        details.getStyleClass().add("menu-list");

        mediaButtons.setAlignment(Pos.CENTER);
        mediaButtons.setMinHeight(70);
        mediaButtons.setSpacing(12);

        libraryLabel.setContextMenu(libraryContext);
        playlistChooser.setContextMenu(playlistContext);
        mainList.setContextMenu(mainListContext);

        mainList.setCellFactory(c -> {
            ListCell<interfaces.Song> cell = new ListCell<interfaces.Song>() {
                @Override
                protected void updateItem(interfaces.Song myObject, boolean b) { //wieso Interfaces.Song?
                    super.updateItem(myObject, myObject == null || b);
                    if (myObject != null) {
                        setText(myObject.getTitle());
                    } else {
                        setText("");
                    }
                }
            };
            return cell;
        });
    }
}
