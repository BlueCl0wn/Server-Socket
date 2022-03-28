package client;

import game.SplitPane;

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
    private final String ip;
    private final int port;

    // identifier
    public final int id;

    // input
    private final Scanner scanner;
    private SplitPane SP;


    public String msg;

    /**
     * Main-Constructor
     *
     * @param ip   ip to connect to
     * @param port port on which to connect
     * @param id   ClientSockets id. For recognition
     */
    public GreetClient(String ip, int port, int id, SplitPane SP) {
        this.ip = ip;
        this.port = port;
        this.id = id;
        this.SP = SP;

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
     * Constructor
     *
     * @param ip   ip to connect to
     * @param port port on which to connect
     * @param id   ClientSockets id. For recognition
     */
    public GreetClient(String ip, int port, int id) {
        this.ip = ip;
        this.port = port;
        this.id = id; // TODO add a system that generates unique user-IDs at random

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
        System.out.println("called run()");

        this.startTransmission();
    }

    /**
     * basically a main loop
     */
    public void startOneTransmission(){
        System.out.println("started client");

        String text;
        do {
            text = this.getUserMessage();

        } while (this.sendOneMessage(text));

        stopConnection();
    }

    public void startTransmission() {
        System.out.println("started transmission");

        while (true) {
            receiveMessage();
        }
        //System.out.println("recieved a message");
    }

    /**
     * Receive messages and print them
     */
    private boolean receiveMessage() {
        try {
            String answer;
            while (!((answer = in.readLine()).equals("3.141592653589"))) {
                switch (answer) {
                    case "STOP" -> {
                        System.out.println("Recipient closed connection.");
                        out.println("STOP");
                        this.stopConnection();
                        return false;
                    }
                    case "." -> {
                        return true;
                    }
                    default -> {
                        // TODO comment debug print
                        System.out.print("Answer: ");
                        System.out.println(answer);
                        SP.perfomOnMessageReceive(answer);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Try/Catch ERROR in 'sendMessage'");
            return true;
        }
        return false;
    }

    /**
     * send a message to server.
     */
    public void sendMessage(String msg) {
        out.println(msg);
    }

    /**
     * Send message to the server
     *
     * @param msg String that is to be sent to ServerSocket
     * @return String of servers answer
     */
    public boolean sendOneMessage(String msg){
        try {
            out.println(msg);

            String answer;
            while (!((answer = in.readLine()).equals("3.141592653589"))) {
                switch (answer) {
                    case "STOP" -> {
                        System.out.println("Recipient closed connection.");
                        out.println("STOP");
                        this.stopConnection();
                        return false;
                    }
                    case "." -> {
                        return true;
                    }
                    default -> {
                        System.out.print("Answer: ");
                        System.out.println(answer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Try/Catch ERROR in 'sendMessage'");
            return true;
        }
        return false;
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
            System.out.print("Client disconnected");
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