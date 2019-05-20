package home;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;


import java.io.File;
import java.io.FilenameFilter;
import java.rmi.RemoteException;

public class HomeController {



    public void link(HomeModel model, HomeView view) {


        /********************************************************************************
         *                            PROPERTY LISTENERS                                *
         ********************************************************************************/

        view.mainList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateDetails(observable, oldValue, newValue, view);
        });

//         view.mainList.getSelectionModel().getSelectedItem().getMediaPlayer().statusProperty().addListener((observable, oldValue, newValue) ->{
//             if(newValue == MediaPlayer.Status.PLAYING){
//
//             }
//         });

        view.mainList.itemsProperty().addListener((observable, oldValue, newValue)->{
            disableDetails(newValue, view);
        });

        view.titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            changeTitle(model, view, newValue);
        });
        view.albumField.textProperty().addListener((observable, oldValue, newValue) -> {
            changeAlbum(model, view, newValue);
        });
        view.artistField.textProperty().addListener((observable, oldValue, newValue) -> {
            changeArtist(model, view, newValue);
        });

//        view.mainList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
//           if ((newValue.equals(model.getCurrentSong()))&&(model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING))){
//               view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
//               System.out.println("selected is playing");
//           } else {
//               view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
//               System.out.println("selected is not playing");
//           }
//
//
//           if (model.getCurrentSong() == null){
//               view.debugField.setText("no Current Song");
//           } else {
//               view.debugField.setText(model.getCurrentSong().getTitle());
//           }
//        });

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
        /********************************************************************************
         *                           ACTION HANDLERS LEFTCLICK                          *
         ********************************************************************************/

        view.mainList.setOnMouseClicked(a->{
            mainListClick(model, view, a);
        });
        /********************************************************************************
         *                          ACTION HANDLERS CONTEXTMENUS                        *
         ********************************************************************************/

        view.addToPlaylist.setOnAction(a->{
            addToPlaylist(model, view);
        });

        view.addLibrary.setOnAction(a->{
            addLibrary(model, view);
        });

        /********************************************************************************
         *                             ACTION HANDLERS BUTTONS                          *
         ********************************************************************************/

        view.libraryButton.setOnMouseClicked(a->{
            libraryButton(model, view, a);
        });

        view.playlistButton.setOnMouseClicked(a->{
            playlistButton(model, view, a);
        });

        view.playButton.setOnAction(a->{
            playButtonAction(model, view);
        });

        view.skipButton.setOnAction(a->{
            skipButtonAction(model, view);
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

            if (model.getCurrentSong()!=null) {
                if (model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                } else { //not playing
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                }
            }
        });

        view.playButton.setOnMouseReleased(e->{
            if (model.getCurrentSong()!=null) {
                if (model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
                } else { //not playing
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                }
            }
        });


        view.playButton.setOnMouseEntered(e -> {
            if (model.getCurrentSong()!=null) {
                if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                } else {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
                }
            }
        });

        view.playButton.setOnMouseExited(e -> {
            if (model.getCurrentSong() != null) {
                if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                } else {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                }
            }
        });

    } // end of link(model,view) method

    /********************************************************************************
     *                                 METHODS                                      *
     ********************************************************************************/
    //these are private, because they should be only accessed by the controller
    public void updateDetails(ObservableValue observable, interfaces.Song oldValue, interfaces.Song newValue, HomeView view){
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
    }
    public void disableDetails(ObservableList newValue, HomeView view){
        if (newValue.size()==0){
            view.albumField.setDisable(true);
            view.titleField.setDisable(true);
            view.artistField.setDisable(true);
        } else {
            view.albumField.setDisable(false);
            view.titleField.setDisable(false);
            view.artistField.setDisable(false);
        } //TODO disable at start.
        view.mainList.setMinHeight(view.mainList.getFixedCellSize()*view.mainList.getItems().size());
        view.mainList.setMaxHeight(view.mainList.getFixedCellSize()*view.mainList.getItems().size());
    }
    public void changeTitle(HomeModel model,HomeView view, String newValue){
        try {
            //TODO: FIND SONG By ID
            model.getLibrary().findSongByPath(view.mainList.getSelectionModel().getSelectedItem().getPath()).setTitle(newValue);
            view.mainList.refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void changeAlbum(HomeModel model,HomeView view, String newValue){
        try {
            //TODO: FIND SONG By ID
            model.getLibrary().findSongByPath(view.mainList.getSelectionModel().getSelectedItem().getPath()).setAlbum(newValue);
            view.mainList.refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void changeArtist(HomeModel model,HomeView view, String newValue){
        try {
            //TODO: FIND SONG By ID
            model.getLibrary().findSongByPath(view.mainList.getSelectionModel().getSelectedItem().getPath()).setArtist(newValue);
            view.mainList.refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void addToPlaylist(HomeModel model, HomeView view){
        try {
            model.getPlaylist().addSong(view.mainList.getSelectionModel().getSelectedItem());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
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

    public void addLibrary(HomeModel model, HomeView view){
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
                        song.getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue)->{
                            if (newValue == song.getMedia().getDuration()){
                                view.skipButton.fire();
                            }
                        });
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
    } //Metadata will
    // be initialized for the first time after the execution of the whole ActionEvent

    public void libraryButton(HomeModel model, HomeView view, MouseEvent a){
        if (model.getLibrary().isEmpty()) {
            if(a.getButton() == MouseButton.PRIMARY){
                addLibrary(model, view);
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
    }

    public void playlistButton(HomeModel model, HomeView view, MouseEvent a){
        if (model.getPlaylist().isEmpty()){
            if(a.getButton()==MouseButton.PRIMARY){
                System.out.println("Playlist is empty");
            }
        } else {
            view.setSelectedPlaylist(model.getPlaylist());
            view.mainList.setItems(model.getPlaylist());
            view.mainList.getSelectionModel().select(model.getCurrentSong());
        }

    }

    public void playButtonAction(HomeModel model, HomeView view){
        if(model.getCurrentSong()!=null) {
                if (model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    model.getCurrentSong().getMediaPlayer().pause();
                    System.out.println("pause");
                } else { //not playing
                    model.getCurrentSong().getMediaPlayer().play();
                    System.out.println("resume");
                }

        }
    }

    public void mainListClick(HomeModel model, HomeView view, MouseEvent a){
        if (model.getCurrentSong()!=null) {
            if (view.getSelectedSong().equals(model.getCurrentSong())) {

                System.out.print("select same song:");
                if(model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {

                    System.out.println(" playing");
                    if (a.getClickCount()==2) {
                        view.playButton.fire();

                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                    }
                } else {
                    System.out.println(" paused");
                    if (a.getClickCount()==2){
                        view.playButton.fire();

                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    }
                }


            } else {
                System.out.println("select new song");
                    if (a.getClickCount()==2){
                        model.getCurrentSong().getMediaPlayer().stop();
                        model.setCurrentSong(view.getSelectedSong());
                        playButtonAction(model, view);
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    }
            }

        }

    }

    public void skipButtonAction(HomeModel model, HomeView view){
        interfaces.Song oldSong = model.getCurrentSong();
        if (model.getCurrentSong()!=null) {
            boolean oldSongWasPlaying= model.getCurrentSong().getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING);
            System.out.println("let's stop: " + oldSong.getTitle());
            oldSong.getMediaPlayer().stop();
            System.out.println("Old song ("+oldSong.getTitle()+")status: " + oldSong.getMediaPlayer().getStatus().toString());

            if(model.getCurrentSongIndex()==model.getCurrentPlaylist().size()-1){ //last song in playlist
                model.setCurrentSong(model.getCurrentPlaylist().get(0));
                if(oldSongWasPlaying) {
                    model.getCurrentSong().getMediaPlayer().play();
                    System.out.println("play after skip playing song");
                }
                if (view.getSelectedPlaylist()==model.getCurrentPlaylist()) {
                    view.mainList.getSelectionModel().selectFirst();
                }

            } else {                                                              //any song in playlist
                model.setCurrentSong(model.getCurrentPlaylist().get(model.getCurrentSongIndex() + 1));
                if(oldSongWasPlaying) {
                    model.getCurrentSong().getMediaPlayer().play(); //TODO: Hier ganz klar play, aber Status von currentSong ist nicht PLAYING
                    System.out.println("play current song: "+model.getCurrentSong().getTitle()+" after skip old song: " + oldSong.getTitle());
                }
                if (view.getSelectedPlaylist()==model.getCurrentPlaylist()){
                    view.mainList.getSelectionModel().select(oldSong);
                    view.mainList.getSelectionModel().selectNext();
                    System.out.println("selected next:" +view.getSelectedSong().getTitle());

                }
            }
            System.out.println("skip song");


        }//TODO when song added to playlist twice, problems with skipping to next song.
    }

}

