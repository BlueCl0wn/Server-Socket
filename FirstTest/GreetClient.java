package FirstTest;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.Thread;


/**
 * @author Darek Petersen
 * @version 1.0
 */
public class GreetClient extends Thread {
    // socket and communication
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    // address and port
    public String ip;
    public int port;

    // identifier
    public int id;

    // input
    Scanner scanner;

    /**
     * Main-Constructor
     *
     * @param ip   ip to connect to
     * @param port port on which to connect
     * @param id   ClientSockets id. For recognition
     */
    public GreetClient(String ip, int port, int id) {
        this.ip = ip;
        this.port = port;
        this.id = id;

        // instantiates scanner
        this.scanner = new Scanner(System.in);

        try {
            // create socket
            clientSocket = new Socket(ip, port);

            // create input and output for communication with recipient
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor 1
     */
    public GreetClient() {
        this("localhost", 6666, 0);
    }

    /**
     * Constructor 2
     *
     * @param ip   ip to connect to
     * @param port port on which to connect
     */
    public GreetClient(String ip, int port) {
        this(ip, port, 0);
    }

    /**
     * Run-method to allow use with 'Thread'
     */
    public void run() {
        this.startTransmission();
    }

    /**
     * basically a main loop
     */
    public void startTransmission() {
        System.out.println("started client");

        String text;
        do {
            text = this.getUserMessage();

        } while (this.sendMessage(text));

        stopConnection();
    }

    /**
     * Send message to the server
     *
     * @param msg String that is to be sent to ServerSocket
     * @return String of servers answer
     */
    public boolean sendMessage(String msg) {
        try {
            out.println(msg);

            String answer = in.readLine();
            if (".".equals(answer)) {
                System.out.println("Recipient closed connection.");
                this.stopConnection();
                return false;
            } else if ("".equals(answer)) {
                System.out.println("Answer loop ended");
                return true;
            } else {
                System.out.print("Answer: ");
                System.out.println(answer);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Try/Catch ERROR in 'sendMessage'");
            return true;
        }
    }

    /**
     * Closes the connection  with recipient
     * and with it 'in' and 'out'
     */
    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ended client");
    }

    /**
     * Uses 'this.scanner' to get input from the user
     *
     * @return String of users input
     */
    private String getUserMessage() {
        System.out.print("Input: ");
        return scanner.nextLine();
    }
}