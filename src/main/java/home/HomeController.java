package home;

import interfaces.MediaControl;
import javafx.collections.MapChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;


import java.io.File;
import java.io.FilenameFilter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HomeController extends UnicastRemoteObject implements MediaControl {

    protected HomeController() throws RemoteException {
    }

    public void link(HomeModel model, HomeView view) {


        /********************************************************************************
         *                            PROPERTY LISTENERS                                *
         ********************************************************************************/

        view.mainList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                if (newValue.getAlbum() != null) {
                    view.setAlbumDetail(newValue.getAlbum());
                } else view.setAlbumDetail("");
                if (newValue.getTitle() != null) {
                    view.setTitleDetail(newValue.getTitle());
                } else view.setTitleDetail("");
                if (newValue.getArtist() != null) {
                    view.setArtistDetail(newValue.getArtist());
                } else view.setArtistDetail("");
            }

        });
//    todo    model.getLibrary().addListener((observable, oldValue, newValue)->{
//            newValue.
//        });


        view.mainList.itemsProperty().addListener((observable, oldValue, newValue)->{
            if (newValue.size()==0){
                view.albumField.setDisable(true);
                view.titleField.setDisable(true);
                view.artistField.setDisable(true);
            } else {
                view.albumField.setDisable(false);
                view.titleField.setDisable(false);
                view.artistField.setDisable(false);
            }
            view.mainList.setMinHeight(view.mainList.getFixedCellSize()*view.mainList.getItems().size());
            view.mainList.setMaxHeight(view.mainList.getFixedCellSize()*view.mainList.getItems().size());
        });

        view.titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                //TODO: FIND SONG By ID
                model.getLibrary().findSongByPath(view.mainList.getSelectionModel().getSelectedItem().getPath()).setTitle(newValue);
                view.mainList.refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        view.albumField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                //TODO: FIND SONG By ID
                model.getLibrary().findSongByPath(view.mainList.getSelectionModel().getSelectedItem().getPath()).setAlbum(newValue);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        view.artistField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                //TODO: FIND SONG By ID
                model.getLibrary().findSongByPath(view.mainList.getSelectionModel().getSelectedItem().getPath()).setArtist(newValue);
                view.mainList.refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        /********************************************************************************
         *                           ACTION HANDLERS CONTEXTMENUS                       *
         ********************************************************************************/

        view.addToPlaylist.setOnAction(a->{
           model.getPlaylist().add(view.mainList.getSelectionModel().getSelectedItem());
        });

        view.mainList.setCellFactory(c -> {
            ListCell<interfaces.Song> cell = new ListCell<interfaces.Song>() {
                @Override
                protected void updateItem(interfaces.Song myObject, boolean b) {
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

        view.addLibrary.setOnAction(a->{
            boolean firstTime=false;
            if (model.getLibrary().isEmpty()){
                firstTime=true;
            }
            File[] mp3List = directoryFilter(".mp3", view);
            if (mp3List!=null) {
                for (File mp3File : mp3List) {
                    Song song = new Song(mp3File);
                    if (!model.getLibrary().pathExists(song)) {
                        try {
                            model.getLibrary().addSong(song);

                            song.getMedia().getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> c) -> {
                                if (c.wasAdded()) {
                                    if ("title".equals(c.getKey())) {
                                        song.setTitle(c.getValueAdded().toString());
                                    } else if ("album".equals(c.getKey())) {
                                        song.setAlbum(c.getValueAdded().toString());
                                    } else if ("artist".equals(c.getKey())) {
                                        song.setArtist(c.getValueAdded().toString());
                                    }
                                }
                            });
                            song.setMediaPlayer(new MediaPlayer(song.getMedia()));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("song already in list");
                    }
                }
                view.setSelectedPlaylist(model.getLibrary());
                view.mainList.setItems(view.getSelectedPlaylist());
                if (firstTime){
                    view.mainList.getSelectionModel().selectFirst();
                    model.setCurrentSong(view.getSelectedSong());
                    model.setCurrentPlaylist(view.getSelectedPlaylist()); // or getlibrary
                }

            }
        }); //Metadata will
        // be initialized for the first time after the execution of the whole ActionEvent


        view.libraryButton.setOnMouseClicked(a->{
                if (model.getLibrary().isEmpty()) {
                    if(a.getButton()== MouseButton.PRIMARY){
                        view.addLibrary.fire();
                    }
                } else {

                    view.setSelectedPlaylist(model.getLibrary());
                    view.mainList.setItems(model.getLibrary());
                    view.mainList.getSelectionModel().select(model.getCurrentSong());
                    if (view.mainList.getSelectionModel().getSelectedItem() == null) {
                        view.mainList.getSelectionModel().selectFirst();
                        if(a.getClickCount()>2){
                            view.playButton.fire();
                        }
                    }
                }
        });


        view.playlistButton.setOnMouseReleased(a->{
            view.setSelectedPlaylist(model.getPlaylist());
            view.mainList.setItems(model.getPlaylist());
        });



        view.playButton.setOnAction(a->{
            if(model.getCurrentSong()!=null) {
                if ((model.getCurrentSong().equals(view.getSelectedSong())) ||(view.getSelectedSong()==null) ) { //same song or no song selected
                    if (model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        model.getCurrentSong().getMediaPlayer().pause();
                        System.out.println("pause");
                        if (view.playButton.isHover()) {// only if play() isn't fired by doubleclick on song
                            view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
                        } else {
                            view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                        }
                    } else { //not playing
                            model.getCurrentSong().getMediaPlayer().play();
                        System.out.println("resume");
                        if (view.playButton.isHover()) {
                            view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                        } else {
                            view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                        }
                    }
                } else { //different selected
                    model.getCurrentSong().getMediaPlayer().stop();
                    model.setCurrentSong(view.getSelectedSong());
                    model.setCurrentPlaylist(view.getSelectedPlaylist());
                    model.getCurrentSong().getMediaPlayer().play();
                    System.out.println("play another");
                    if (view.playButton.isHover()) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                    } else {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    }
                }
//            } else if (view.getSelectedSong()!=null){ //currentsong null
//                model.setCurrentSong(view.getSelectedSong());
//                model.setCurrentPlaylist(view.getSelectedPlaylist());
//                model.getCurrentSong().getMediaPlayer().play();
//                System.out.println("play first time");
//                if (view.playButton.isHover()) {
//                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
//                } else {
//                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
//                } not needed anymore? because currentsong initialized at import.
            }
        });

        view.mainList.setOnMouseClicked(e->{
            if (model.getCurrentSong()!=null) {
                if (view.getSelectedSong().equals(model.getCurrentSong())) {
                    if(model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    }
                } else {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                }

            }
            if (e.getClickCount()==2){
                view.playButton.fire();

            }
        });

        view.skipButton.setOnAction(a->{
            boolean songIsPlaying= model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING);

            if (model.getCurrentSong()!=null) {
                model.getCurrentSong().getMediaPlayer().stop();
                if(model.getCurrentSongIndex()==model.getCurrentPlaylist().size()-1){ //last song in playlist
                    model.setCurrentSong(model.getCurrentPlaylist().get(0));
                    if (view.getSelectedPlaylist()==model.getCurrentPlaylist()) {
                        view.mainList.getSelectionModel().selectFirst();
                    }
                } else {
                    model.setCurrentSong(model.getCurrentPlaylist().get(model.getCurrentSongIndex() + 1));
                    if (view.getSelectedPlaylist()==model.getCurrentPlaylist()){
                        view.mainList.getSelectionModel().selectNext();
                    }
                }
                if(songIsPlaying) {
                    model.getCurrentSong().getMediaPlayer().play();
                }

            }//TODO when song added to playlist twice, problems with skipping to next song.
        });

        /********************************************************************************
         *                           BUTTON EFFECT-ONLY HANDLERS                        *
         ********************************************************************************/

        view.prevButton.setOnMouseEntered(e-> {
            if (model.getCurrentSong()!=null) {
                view.prevButton.setGraphic(new ImageView(new Image("/mediabuttons/prevHover.png")));
            }
            });
        view.prevButton.setOnMouseExited(e-> {
            view.prevButton.setGraphic(new ImageView(new Image("/mediabuttons/prev.png")));
            });
        view.prevButton.setOnMousePressed(e-> {
            view.prevButton.setGraphic(new ImageView(new Image("/mediabuttons/prev.png")));
            });
        view.prevButton.setOnMouseReleased(e-> {
            if (model.getCurrentSong()!=null){
                view.prevButton.setGraphic(new ImageView(new Image("/mediabuttons/prevHover.png")));
        }
            });

        view.skipButton.setOnMouseEntered(e-> {
            if(model.getCurrentSong()!=null) {
                view.skipButton.setGraphic(new ImageView(new Image("/mediabuttons/skipHover.png")));
            }
        });
        view.skipButton.setOnMouseExited(e-> {
            view.skipButton.setGraphic(new ImageView(new Image("mediabuttons/skip.png")));
        });
        view.skipButton.setOnMousePressed(e-> {
            view.skipButton.setGraphic(new ImageView(new Image("mediabuttons/skip.png")));
        });
        view.skipButton.setOnMouseReleased(e-> {
            if(model.getCurrentSong()!=null) {
                view.skipButton.setGraphic(new ImageView(new Image("/mediabuttons/skipHover.png")));
            }
        });




        view.playButton.setOnMousePressed(e->{

            if(model.getCurrentSong()!=null) {
                if (model.getCurrentSong().equals(view.getSelectedSong())) {
                    if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    } else if (view.getSelectedSong() != null) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                    }
                } else {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                }
            }

        });


        view.playButton.setOnMouseEntered(e -> {
            if (model.getCurrentSong()!=null){
                if (model.getCurrentSong().equals(view.getSelectedSong())){
                    if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                    } else {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
                    }
                } else if (view.getSelectedSong()!=null){
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));

                } else {//currentsong not null, but selected is null
                    if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                    } else {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
                    }
                }
            }
        });

        view.playButton.setOnMouseExited(e -> {
            if (model.getCurrentSong()!=null){
                if (model.getCurrentSong().equals(view.getSelectedSong()) || view.getSelectedSong()==null){
                    if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    } else {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                    }
                } else {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));

                }
            }
        });

    } // end of link(model,view)

    /********************************************************************************
     *                                 METHODS                                      *
     ********************************************************************************/
    //these are private, because they should be only accessed by the controller

    @Override
    public File[] directoryFilter(String endswith, Node parent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder to import");
        directoryChooser.setInitialDirectory(new File("/"));
        File[] fileList = null;
        try {
            fileList = directoryChooser.showDialog(parent.getScene().getWindow()).listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {

                    return name.toLowerCase().endsWith(endswith);
                }//listFiles can be called with class that implements FilenameFilter. Here we create such a class inside the call.

            });
        } catch (NullPointerException e) {
            System.out.println("");
        } finally {
            return fileList;
        }
    }



}

