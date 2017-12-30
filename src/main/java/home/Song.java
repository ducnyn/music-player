package home;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class Song implements interfaces.Song {

    //all private, use getters and setters
    private SimpleStringProperty path;
    private SimpleStringProperty title;
    private SimpleStringProperty album;
    private SimpleStringProperty interpret;
    private long id;
    private Media song;
    private MediaPlayer mdplayer;


    public Song(File file) {
        song = new Media(file.toURI().toString());

        song.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> c) -> {
            if (c.wasAdded()) {
                if ("title".equals(c.getKey())) {
                    setTitle(c.getValueAdded().toString());
                    System.out.println(getTitle());
                } else if ("album".equals(c.getKey())) {
                    setAlbum(c.getValueAdded().toString());
                } else if ("artist".equals(c.getKey())) {
                    setInterpret(c.getValueAdded().toString());
                }
            }
        });

        mdplayer = new MediaPlayer(song); // The meta data will be null until the action in which this
                                            // constructor was called has finished.
    }

//    public Media getMedia() {
//        return song;
//    }
    @Override
    public MediaPlayer getMediaPlayer() {
        return mdplayer;
    }

    @Override
    public String getAlbum() {

        return albumProperty().getValue();
    }

    @Override
    public void setAlbum(String album) {
        this.album = new SimpleStringProperty(album);
    }

    @Override
    public String getInterpret() {
        return interpretProperty().getValue();
    }

    @Override
    public void setInterpret(String interpret) {

        this.interpret = new SimpleStringProperty(interpret);

    }

    @Override
    public String getPath() {
        return pathProperty().getValue();
    }

    @Override
    public void setPath(String path) {
        this.path = new SimpleStringProperty(path);
    }

    @Override
    public String getTitle() {
        return titleProperty().getValue();
    }

    @Override
    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public ObservableValue<String> pathProperty() {

        return this.path;
    }

    @Override
    public ObservableValue<String> albumProperty() {

        return this.album;
    }

    @Override
    public ObservableValue<String> interpretProperty() {

        return this.interpret;
    }

    @Override
    public ObservableValue<String> titleProperty() {

        return this.title;
    }
}
