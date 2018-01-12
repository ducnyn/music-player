package mainServer;

import home.HomeController;
import mainClient.MediaControlClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MediaControlServer extends HomeController{
    private ArrayList<MediaControlClient> clients;
    String name = "MediaControl";

    protected MediaControlServer() throws RemoteException {
        this.clients = new ArrayList();
    }
}
