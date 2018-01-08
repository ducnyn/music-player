package home;


import javafx.scene.media.MediaPlayer;

import java.rmi.RemoteException;

public class HomeModel {

    private final Playlist library = new Playlist();
    private final Playlist playlist = new Playlist();
    private static Song currentSong;
    private static int currentSongIndex;
    private static Playlist currentPlaylist;

    public int getCurrentSongIndex(){
        try {
            return getCurrentPlaylist().getList().indexOf(getCurrentSong());
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }//TODO dont return 0

    }

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

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song newSong) {
        currentSong = newSong;
    }



}


