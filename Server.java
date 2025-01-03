package socketJavaTp2Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.plaf.basic.BasicTreeUI.CellEditorHandler;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        int port = 12345;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server is running!");
            System.out.println("server waiting for a connection...");
            while (true)
            {
                Socket socket  = null;
                socket = server.accept();
                System.out.println("Client accepted" + socket.getRemoteSocketAddress());
                ClientHandler client = new ClientHandler(socket);
                client.start();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            server.close();
        }
    }
}


class ClientHandler extends Thread{
    static int counter = 0;
    private Socket socket;
    public ClientHandler(Socket s){
        socket = s;
        counter++;
        System.out.println("A client has connected. Current number of clients: " + counter);
    }
    @Override
    public void run()
    {
        OutputStream socketOut = null;
        ObjectInputStream objInput = null;
        ObjectOutputStream objOutput = null;
        try {
            socketOut = socket.getOutputStream();
            objOutput = new ObjectOutputStream(socketOut);
            socketOut.flush();
            objInput = new ObjectInputStream(socket.getInputStream());
            socketOut.write(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true)
        {
            Object obj;
            try {
                obj = objInput.readObject();
                if (obj instanceof Operation)
                {
                    Operation operation = (Operation)obj; // casting
                    if (operation.getStatus())
                    {
                        counter--;
                        System.out.println("Socket client has ended. Number of current clients: " + counter);
                        socket.close();
                        break;
                    }
                    System.out.println("Object has been deserialized: " + operation.toString());
                    operation.calculer();
                    objOutput.writeObject(operation);
                    System.out.println("Object has been serialized: " + operation.toString());
                }
                else
                {
                    System.out.println("Not instance of Operation");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}