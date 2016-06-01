package controller.model;

import com.sun.corba.se.spi.activation.Server;
import controller.view.ServerOverviewController;
import javafx.application.Platform;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luigi on 5/17/16.
 */
public class ClientSetup
{
    public List<SocketClient> list = new ArrayList<SocketClient>();
    public ServerOverviewController controller;

    public ClientSetup(ServerOverviewController controller)
    {
        this.controller = controller;
    }

    public class SocketClient {
        public OutputStream os;
        public InputStream in;
        public Socket socket;

        public ObjectOutputStream oos;

        public SocketClient(Socket socket)
        {
            try {
                this.socket = socket;
                os = this.socket.getOutputStream();
                in = this.socket.getInputStream();
                oos = new ObjectOutputStream(os);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void add(Socket var)
    {
        SocketClient socket = new SocketClient(var);
        list.add(socket);
        ThreadClientSetup thread = new ThreadClientSetup(list.size() -1);
        thread.start();
    }

    public class ThreadClientSetup extends Thread {

        private SocketClient socket;
        private int index;
        private ObjectInputStream objectos;

        public ThreadClientSetup (int index){
            try {
                this.index = index;
                this.socket = list.get(this.index);
                this.objectos = new ObjectInputStream(this.socket.in);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public void run()
        {

            Platform.runLater(() -> controller.addUser());
            try {
                Message obj;
                while((obj =(Message) objectos.readObject()) != null){
                        sendObjects(obj);
                    }
            } catch(Exception e){
                e.printStackTrace();
            }

            Platform.runLater(() -> controller.delUser());
        }
    }

    public void sendObjects(Message o)
    {
        for (SocketClient socket: list) {
            try {
                socket.oos.writeObject(o);
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }

        }
    }

}
