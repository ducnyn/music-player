package home;

import javafx.beans.value.ChangeListener;
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
    private SimpleStringProperty artist;
    private long id;
    private Media media;
    private MediaPlayer mediaPlayer;


    public Song(File file) {
        media = new Media(file.toURI().toString());
        setPath(file.getPath());
        setTitle("");
        setAlbum("");
        setArtist("");


        // The meta data will be null until the action in which this
                                            // constructor was called has finished.
    }

    public Media getMedia() {
        return media;
    }
    @Override
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    
    public void setMediaPlayer(MediaPlayer mediaplayer){
        this.mediaPlayer = mediaplayer;
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
    public String getArtist() {
        return artistProperty().getValue();
    }

    @Override
    public void setArtist(String artist) {

        this.artist = new SimpleStringProperty(artist);

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
    public ObservableValue<String> artistProperty() {

        return this.artist;
    }

    @Override
    public ObservableValue<String> titleProperty() {

        return this.title;
    }


}
