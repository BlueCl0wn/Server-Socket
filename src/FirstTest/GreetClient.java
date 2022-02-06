package FirstTest;

import java.net.*;
import java.io.*;
import java.lang.Thread;


/**
 * @author Darek Petersen
 * @version 1.0
 *
 */
public class GreetClient extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public String ip;
    public int port;
    public int id;

    /**
     * Constructor 1
     */
    public GreetClient() {
        this.ip = "localhost";
        this.port = 6667;
    }

    /**
     * Constructor 2
     * @param ip    ip to connect to
     * @param port  port on which to connect
     */
    public GreetClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Constructor 3
     * @param ip    ip to connect to
     * @param port  port on which to connect
     * @param id    ClientSockets id. For recognition
     */
    public GreetClient(String ip, int port, int id) {
        this.ip = ip;
        this.port = port;
        this.id = id;
    }

    /**
     * Run method to be called by Thread
     */
    public void run() {
        System.out.println("started client");
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String x = sendMessage("Hello World!");
        System.out.println(x);
        stopConnection();
    }

    /**
     * Send message to the server
     * @param msg   String that is to be sent to ServerSocket
     * @return      Answer returned by server
     */
    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "Try/Catch ERROR in 'sendMessage'";
        }
    }

    /**
     * Closes the connection
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
}