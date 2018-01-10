package home;

import javafx.collections.ModifiableObservableListBase;

import interfaces.Song;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Playlist extends ModifiableObservableListBase<Song> implements interfaces.Playlist{

    private ArrayList<Song> listOfSongs = new ArrayList<>();

    @Override
    public boolean addSong(Song s) throws RemoteException {
       return listOfSongs.add(s);

    }

    @Override
    public boolean deleteSong(Song s) throws RemoteException {
       return  listOfSongs.remove(s);
    }

    @Override
    public boolean deleteSongByID(long id) throws RemoteException {
        return listOfSongs.removeIf(song -> song.getId() == id);
    }

    @Override
    public void setList(ArrayList<Song> s) throws RemoteException {
        this.listOfSongs = s;
    }

    @Override
    public ArrayList<Song> getList() throws RemoteException {
        return listOfSongs;
    }

    @Override
    public void clearPlaylist() throws RemoteException {
        listOfSongs.clear();
    }

    @Override
    public int sizeOfPlaylist() throws RemoteException {
        return listOfSongs.size();
    }

    @Override
    public Song findSongByPath(String name) throws RemoteException {
        return listOfSongs.stream().filter(song -> song.getPath()==name).findFirst().get();
    }

    @Override
    public Song findSongByID(long id) throws RemoteException {
        return listOfSongs.stream().filter(song -> song.getId()==id).findFirst().get();
    }

    @Override
    public Song get(int i) {
        return listOfSongs.get(i);
    }

    @Override
    public int size() {
        return listOfSongs.size();
    }

    @Override
    protected void doAdd(int i, Song song) {
        listOfSongs.add(i, song);
    }

    @Override
    protected Song doSet(int i, Song song) {
        return listOfSongs.set(i, song);
    }

    @Override
    protected Song doRemove(int i) {
        return listOfSongs.remove(i);
    }

    public boolean pathExists(Song songToCheck){
        for(Song libSong : this){
            if ( libSong.getPath().equals(songToCheck.getPath()) ){
                return true;
            }
        }
        return false;
    }
}
