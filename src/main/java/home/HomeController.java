package home;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FilenameFilter;
import java.rmi.RemoteException;

public class HomeController {

    public void link(HomeModel model, HomeView view) {


        /********************************************************************************
         *                                ACTION HANDLERS                               *
         ********************************************************************************/

        view.addLibrary.setOnAction(a->{
            File[] mp3list = directoryFilter(".mp3");
            if (mp3list!=null) {
                for (int i = 0; i < mp3list.length; i++) {
                    Song song = new Song(mp3list[i]);
                    song.setPath(mp3list[i].getPath());
                    if (!model.getLibrary().contains(song)) {
                        try {
                            model.getLibrary().addSong(song);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("song already in list");
                    }
                }
                view.mainList.setItems(model.getLibrary());
            }

        });

        view.libraryLabel.setOnMouseReleased(a->{
            if(model.getLibrary().isEmpty()){
                view.addLibrary.fire();
            } else {
                view.mainList.setItems(model.getLibrary());
            } // TODO: Listener currentSong Selection
        });

        view.playlistLabel.setOnMouseReleased(a->{
            view.mainList.setItems(model.getPlaylist());
        });

        view.playButton.setOnAction(a->{
            interfaces.Song selectedSong = view.mainList.getSelectionModel().getSelectedItem();
            if(model.getCurrentSong()!=null) {
                if(model.getCurrentSong().equals(selectedSong)) {
                    if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        model.getCurrentMediaPlayer().pause();
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));

                        System.out.println("pause");
                    } else {
                        model.getCurrentMediaPlayer().play();
                        System.out.println("play");
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));

                    }
                } else {
                    model.getCurrentMediaPlayer().stop();
                    model.setCurrentSong(selectedSong);
                    model.getCurrentMediaPlayer().play();
                    System.out.println("stop song, play new song");
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));

                }
            } else if (selectedSong!=null){
                model.setCurrentSong(selectedSong);
                model.getCurrentMediaPlayer().play();
                System.out.println("play first time");
                view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));

            }
        });
        /********************************************************************************
         *                            BUTTON EFFECT HANDLERS                            *
         ********************************************************************************/
        view.mainList.setOnMouseClicked(e->{
            if (model.getCurrentSong()!=null) {
                if (view.mainList.getSelectionModel().getSelectedItem().equals(model.getCurrentSong())) {
                    if(model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                        view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
                    }
                } else {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
                }
            }
        });
        view.prevButton.setOnMouseEntered(e-> {
            if (view.mainList.getSelectionModel().getSelectedItem()!=null) {
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
            if (view.mainList.getSelectionModel().getSelectedItem() != null){
                view.prevButton.setGraphic(new ImageView(new Image("/mediabuttons/prevHover.png")));
        }
            });

        view.skipButton.setOnMouseEntered(e-> {
            if(view.mainList.getSelectionModel().getSelectedItem()!=null) {
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
            if(view.mainList.getSelectionModel().getSelectedItem()!=null) {
                view.skipButton.setGraphic(new ImageView(new Image("/mediabuttons/skipHover.png")));
            }
        });




        view.playButton.setOnMousePressed(e->{
            interfaces.Song selectedSong = view.mainList.getSelectionModel().getSelectedItem();

            if (model.getCurrentSong()!=null){
                if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
                }else if(selectedSong!=null){
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
                }
            }
        });
//        view.playButton.setOnMouseReleased(e -> {
//            if (model.getCurrentSong()!=null){
//                if (model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
//                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
//                }
//            } else {
//                view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
//            }
//        });

        view.playButton.setOnMouseEntered(e -> {
            interfaces.Song selectedSong = view.mainList.getSelectionModel().getSelectedItem();

            if (model.getCurrentSong()!=null){
                if (!model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
            }

            } else if(selectedSong!=null) {
                view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
            }
        });
        view.playButton.setOnMouseExited(e -> {
            if (model.getCurrentSong()!=null){
                if (!model.getCurrentMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));
            }
            } else {
                view.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
            }
        });

    } // end of link(model,view)

    /********************************************************************************
     *                                 METHODS                                      *
     ********************************************************************************/
    //these are private, because they should be only accessed by the controller


    private static File[] directoryFilter(String endswith){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder to import");
        directoryChooser.setInitialDirectory(new File("/"));
        File[] fileList = null;
        try {
            fileList = directoryChooser.showDialog(null).listFiles(new FilenameFilter() {

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

