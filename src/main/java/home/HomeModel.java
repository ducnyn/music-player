package home;


import javafx.scene.media.MediaPlayer;

public class HomeModel {

    private final Playlist library = new Playlist();
    private final Playlist playlist = new Playlist();
    private static interfaces.Song currentSong;
//    private static MediaPlayer currentMediaPlayer;
    private static Playlist currentPlaylist;

    public Playlist getLibrary() {
        return library;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist newCurrent) {
        currentPlaylist = newCurrent;
    }

    public MediaPlayer getCurrentMediaPlayer() {
        return currentSong.getMediaPlayer();
    }

//    public void setCurrentMediaPlayer(MediaPlayer newPlayer) {
//        currentMediaPlayer = newPlayer;
//    }

    public interfaces.Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(interfaces.Song newSong) {
        currentSong = newSong;
    }
}