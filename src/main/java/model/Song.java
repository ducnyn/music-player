package model;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;



public class Song implements interfaces.Song {

    private SimpleStringProperty path;
    private SimpleStringProperty title;
    private SimpleStringProperty album;
    private SimpleStringProperty interpret;
    private long id;

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
