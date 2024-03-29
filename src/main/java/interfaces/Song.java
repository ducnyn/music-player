package interfaces;

import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public interface Song {

    Media getMedia();

    MediaPlayer getMediaPlayer();

    String getAlbum() ;

    void setAlbum(String album) ;

    String getArtist() ;

    void setArtist(String artist) ;

    String getPath() ;

    void setPath(String path) ;

    String getTitle() ;

    void setTitle(String title) ;

    long getId();

    void setId(long id);

    ObservableValue<String> pathProperty();
    ObservableValue<String> albumProperty();
    ObservableValue<String> artistProperty();
    ObservableValue<String> titleProperty();

    String toString();
}
