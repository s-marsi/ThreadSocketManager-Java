package ThreadSocketManagerJava;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import ThreadSocketManager-Java.Operation;

public class Client {

    public static Operation userInput(Scanner scan)
    {
        double nb1;
        double nb2;
        String op;
        Operation operation = new Operation();
        System.out.print("Enter a double (or type 'exit' to quit):");
        if (scan.hasNextDouble()){
            nb1 = scan.nextDouble();
        }
        else{
            String input = scan.nextLine();
            if (input.equals("exit") == false)
                System.out.println("Invalid input");
            operation.setStatus();
            scan.close();
            return (operation);
        }
        scan.nextLine();
        System.out.print("Enter an operation (or type 'exit' to quit):");
        op = scan.nextLine();
        if (op.equals("exit"))
        {
            System.out.println("Program has ended.");
            operation.setStatus();
            scan.close();
            return (operation);
        }
        System.out.print("Enter a double (or type 'exit' to quit):");
        if (scan.hasNextDouble()){
            nb2 = scan.nextDouble();
        }
        else{
            String input = scan.nextLine();
            if (input.equals("exit") == false)
                System.out.println("Invalid input");
            operation.setStatus();
            scan.close();
            return (operation);
        }
        Operation returnOperation = new Operation(nb1, nb2, op);
        return (returnOperation);
    }

     public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        int                port = 12345;
        Socket             socket = null;
        InputStream        socketIn = null;
        ObjectOutputStream objOutput = null;
        ObjectInputStream  objInput = null;
        Operation          operation;
        Scanner scan = new Scanner(System.in);
        try {
            socket = new Socket("localhost", port);
            System.out.println("Client ready!");
            socketIn = socket.getInputStream();
            objOutput = new ObjectOutputStream(socket.getOutputStream());
            objInput = new ObjectInputStream(socket.getInputStream());
            int count = socketIn.read();
            System.out.println("Vous êtes le client n° " + count);
            
        } catch (Exception e) {
           System.out.println(e.getMessage());
           System.exit(1);
        }
        while (true)
        {
            operation = userInput(scan); // Get the user input, validate it, create an Operation instance, and return it.
            if (operation.getStatus()) // If status is true, it indicates an error in the user input or that the input is "exit", so the program should terminate.
                break ;
            objOutput.writeObject(operation);
            System.out.println("Object has been serialized: " + operation.toString());
            Operation operation2 = (Operation)objInput.readObject();
            System.out.println("Object has been deserialized: " + operation2.toString());
        }
        objOutput.writeObject(operation);
        socket.close();
        scan.close();
    }
}
