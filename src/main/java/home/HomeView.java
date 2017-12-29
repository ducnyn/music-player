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




        MenuItem settings = new MenuItem("Settings...");
        MenuItem importPlaylist = new MenuItem("Import Playlist...");
        MenuItem exportPlaylist = new MenuItem("Export Playlist...");
            Menu file = new Menu("File",null, settings, importPlaylist, exportPlaylist);
        MenuItem about = new MenuItem("About");
            Menu help = new Menu("Help", null, about);
                MenuBar menubar = new MenuBar(file, help);

    Label libraryLabel = new Label("YOUR LIBRARY");
    ListView libraryChooser = new ListView();
    Label playlistLabel = new Label("PLAYLISTS");
    ListView playlistChooser = new ListView();
            VBox listBox = new VBox(libraryLabel, libraryChooser,playlistLabel,playlistChooser);
            ListView mainList = new ListView();
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

        VBox.setVgrow(menubar, Priority.NEVER);
        VBox.setVgrow(globalHBox, Priority.ALWAYS);
        VBox.setVgrow(mediaButtons, Priority.NEVER);

        VBox.setVgrow(libraryChooser, Priority.NEVER);
        VBox.setVgrow(playlistChooser, Priority.ALWAYS);

        HBox.setHgrow(mainBox, Priority.ALWAYS);
        VBox.setVgrow(mainList, Priority.ALWAYS);

        listBox.setMinWidth(220);
        listBox.setMaxWidth(220);
        listBox.setPadding(new Insets(0,0,0,20));
        listBox.getStyleClass().add("menu-list");
        libraryChooser.getStyleClass().add("menu-list");
        playlistChooser.getStyleClass().add("menu-list");

        mainBox.setPadding(new Insets(-1,0,-1,0));
        mainList.getStyleClass().add("main-list");

        detailsBox.setMinWidth(220);
        detailsBox.setMaxWidth(220);
        detailsBox.getStyleClass().add("menu-list");
        details.getStyleClass().add("menu-list");

        mediaButtons.setAlignment(Pos.CENTER);
        mediaButtons.setMinHeight(70);
        mediaButtons.setSpacing(12);

        libraryChooser.setContextMenu(libraryContext);
        playlistChooser.setContextMenu(playlistContext);
        mainList.setContextMenu(mainListContext);


    }
}
