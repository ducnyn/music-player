package home;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

    private static HomeModel homeModel;
    private static HomeView homeView;

    public void link(HomeModel m, HomeView v) {
        homeModel = m;
        homeView = v;

        buttonEffects(homeView.prevButton, "/mediabuttons/prev.png", "/mediabuttons/prevHover.png");
        buttonEffects(homeView.skipButton,"mediabuttons/skip.png", "/mediabuttons/skipHover.png");

        homeView.playButton.setOnMousePressed(e -> {
            if (homeView.isPlaying) {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));

            } else {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
            }
        });

        homeView.playButton.setOnMouseEntered(e -> {
            if (homeView.isPlaying) {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));

            } else {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
            }
        });
        homeView.playButton.setOnMouseExited(e -> {
            if (homeView.isPlaying) {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pause.png")));

            } else {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/play.png")));
            }
        });

        homeView.playButton.setOnAction(e -> {
            homeView.isPlaying = !v.isPlaying;
            if (homeView.isPlaying) {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/pauseHover.png")));
            } else {
                homeView.playButton.setGraphic(new ImageView(new Image("/mediabuttons/playHover.png")));
            }
        });

    }

    private static void buttonEffects(Button button, String path, String hoverPath) {

        button.setOnMouseEntered(e-> {
            button.setGraphic(new ImageView(new Image(hoverPath)));
        });
        button.setOnMouseExited(e-> {
            button.setGraphic(new ImageView(new Image(path)));
        });
        button.setOnMousePressed(e-> {
            button.setGraphic(new ImageView(new Image(path)));
        });
        button.setOnMouseReleased(e-> {
            button.setGraphic(new ImageView(new Image(hoverPath)));
        });


    }








//    public void initModel(HomeModel HomeModel) {
//        // ensure HomeModel is only set once:
//        if (this.HomeModel != null) {
//            throw new IllegalStateException("HomeModel can only be initialized once");
//        }
//
//        this.HomeModel = HomeModel ;
//        listView.setItems(HomeModel.playlist());
//
//        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
//                HomeModel.setCurrentSong(newSelection));
//
//        HomeModel.currentSongProperty().addListener((obs, oldPerson, newPerson) -> {
//            if (newPerson == null) {
//                listView.getSelectionModel().clearSelection();
//            } else {
//                listView.getSelectionModel().select(newPerson);
//            }
//        });
//    }
}

