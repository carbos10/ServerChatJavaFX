package controller.model;

import com.sun.corba.se.spi.activation.Server;
import com.sun.deploy.util.SessionState;
import controller.view.ServerOverviewController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by luigi on 5/16/16.
 */
public class ThreadServer {

    private ThreadMain thread = new ThreadMain();
    private ClientSetup clientSetup;

    protected int port;

    private ServerSocket server;

    public ThreadServer(int port, ServerOverviewController controller)
    {
        try {
            clientSetup = new ClientSetup(controller);
            this.port = port;
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer()
    {
        thread.start();
    }

    public int getPort()
    {
        return this.port;
    }

    class ThreadMain extends Thread {

        public void run()
        {
            while(true){
                try {
                    Socket client = server.accept();
                    clientSetup.add(client);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }





}
