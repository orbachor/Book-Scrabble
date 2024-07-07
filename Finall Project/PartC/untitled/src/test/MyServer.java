package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    int port;
    boolean stop;
    ClientHandler ch;
    public MyServer(int port ,ClientHandler ch)
    {
        this.ch=ch;
        this.port=port;
    }

    public void start()
    {
        stop=false;
        new Thread(()->startServer()).start();
    }

    private void startServer() {
        ServerSocket server=null;
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop)
            {Socket client=null;
                try {
                    client = server.accept();
                    ch.handleClient(client.getInputStream(), client.getOutputStream());
                    ch.close();
                    client.close();
                }
                catch (SocketTimeoutException e) {}
                finally {
                    ch.close();
                    if (client != null)
                        client.close();}
            }
            server.close();
        }
        catch(IOException e){e.printStackTrace();}
        finally
        {
            if (server != null)
            {
                try
                {server.close();}
                catch (IOException e)
                {System.out.println("The IOException in Server.close is"+e);}
            }
        }
    }

    public void close()
    {
        stop=true;
    }
}
