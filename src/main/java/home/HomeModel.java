package home;


public class HomeModel {

    private final Playlist library = new Playlist();
    private final Playlist playlist = new Playlist();
    public Playlist getLibrary() {
        return library ;
    }
    public Playlist getplaylist() {
        return playlist ;
    }
}