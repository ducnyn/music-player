package interfaces;

import home.HomeModel;
import home.HomeView;
import javafx.scene.Node;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface MediaControl extends Remote {

    void link(HomeModel model, HomeView view) throws RemoteException;

    File[] directoryFilter(String endswith, Node parent) throws RemoteException;
}
