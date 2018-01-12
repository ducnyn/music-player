package home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HomeView extends VBox{
    private static Playlist selectedPlaylist;


            Button libraryButton = new Button("Library");
            Button playlistButton = new Button("Playlist");
        VBox sideBar = new VBox(libraryButton,playlistButton);
            ListView<interfaces.Song> mainList = new ListView();
            ListView<interfaces.Song> emptyList = new ListView();
        VBox mainBox = new VBox(mainList, emptyList);
            Label titleLabel = new Label("Title");
            TextField titleField = new TextField();
            Label albumLabel = new Label("Album");
            TextField albumField = new TextField();
            Label artistLabel = new Label("Artist");
            TextField artistField = new TextField();
            Label debugLabel = new Label("Debug Area:");
            TextField debugField = new TextField ("Debug Field");
        VBox debug = new VBox(debugLabel, debugField);
        VBox details = new VBox(titleLabel, titleField, albumLabel, albumField, artistLabel, artistField,debug);
    HBox globalHBox = new HBox(sideBar, mainBox, details);


            Button playButton = new Button("", new ImageView(new Image("/mediabuttons/play.png")));//https://www.flaticon.com/authors/vaadin
            Button prevButton = new Button("", new ImageView(new Image("/mediabuttons/prev.png")));
            Button skipButton = new Button("", new ImageView(new Image("/mediabuttons/skip.png")));
    HBox mediaButtons = new HBox(prevButton, playButton, skipButton);



        MenuItem addLibrary = new MenuItem("Add Library...");
    ContextMenu libraryContext = new ContextMenu(addLibrary);
        MenuItem createPlaylist = new MenuItem("create Playlist...");
    ContextMenu playlistContext = new ContextMenu(createPlaylist);
        MenuItem addToPlaylist = new MenuItem("Playlist");
        Menu addToPlaylists = new Menu("Add to Playlist",null, addToPlaylist);
        MenuItem removeFromPlaylist = new MenuItem("Remove from this Playlist");
    ContextMenu mainListContext = new ContextMenu(addToPlaylists, removeFromPlaylist);




    public HomeView() {

        this.getChildren().addAll(globalHBox, mediaButtons);

        VBox.setVgrow(globalHBox, Priority.ALWAYS);
        VBox.setVgrow(mediaButtons, Priority.ALWAYS);

        HBox.setHgrow(mainBox, Priority.SOMETIMES);
        VBox.setVgrow(mainList, Priority.SOMETIMES);
        VBox.setVgrow(emptyList, Priority.ALWAYS);


        sideBar.setMinWidth(220);
        sideBar.setMaxWidth(220);
        sideBar.setPadding(new Insets(10, 0, 0, 15));
        sideBar.setSpacing(0);
        sideBar.getStyleClass().add("menu-list");
        libraryButton.getStyleClass().add("sidebar-button");
        libraryButton.setPrefSize(1000, 35);
        libraryButton.setContextMenu(libraryContext);

        playlistButton.getStyleClass().add("sidebar-button");
        playlistButton.setPrefSize(1000, 35);

        mainBox.setPadding(new Insets(-1, 0, -1, 0));

        mainList.getStyleClass().add("mainServer-list");
        mainList.setPadding(new Insets(0, 1, -1, 1));
        mainList.setContextMenu(mainListContext);
        mainList.setFixedCellSize(40);
        mainList.setMinHeight(mainList.getFixedCellSize()*mainList.getItems().size());
        mainList.setMaxHeight(mainList.getFixedCellSize()*mainList.getItems().size());
        emptyList.getStyleClass().add("empty-list");




        details.setMinWidth(220);
        details.setMaxWidth(220);
        details.getStyleClass().add("menu-list");
        titleField.getStyleClass().add("detail-field");
        albumField.getStyleClass().add("detail-field");
        artistField.getStyleClass().add("detail-field");
        debug.setPadding(new Insets(50, 0, 0, 0));
        debugField.getStyleClass().add("detail-field");

        playButton.getStyleClass().add("media-button");
        prevButton.getStyleClass().add("media-button");
        skipButton.getStyleClass().add("media-button");
        mediaButtons.setAlignment(Pos.CENTER);
        mediaButtons.setMinHeight(70);
        mediaButtons.setMaxHeight(70);
        mediaButtons.setSpacing(12);


    }

    public void setTitleDetail(String title){
        titleField.setText(title);
    }

    public void setAlbumDetail(String album){
        albumField.setText(album);
    }

    public void setArtistDetail(String artist){
        artistField.setText(artist);
    }

    public interfaces.Song getSelectedSong(){
        return mainList.getSelectionModel().getSelectedItem();
    }

    public Playlist getSelectedPlaylist() { return selectedPlaylist; }

    public void setSelectedPlaylist(Playlist newPlaylist) { selectedPlaylist = newPlaylist; }
}
