package FirstTest;

import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;

/**
 * @author Darek Petersen
 */
public class GreetServer extends Thread{
    // ServerSocket instance
    private ServerSocket server;

    private static boolean isRunning;

    static ArrayList<Integer> rooms;

    public int port;

    // ArrayList to save rooms
    //ArrayList<>


    /**
     * Main Constructor
     * @param port Port number on which to open server
     */
    public GreetServer(int port) {
        System.out.println("started Server");

        // Indicates status of the server
        isRunning = true;

        // List to store room numbers
        rooms = new ArrayList<>();

        // Stores port on which the server is opened
        this.port = port;

        // Open server
        try {
            server = new ServerSocket(this.port);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Generator 2
     */
    public GreetServer() {
        // Use 0 as port number to automatically choose free port
        this(0);

        // Save assigned port number for later usage
        this.port = server.getLocalPort();

        // Print out server port for clients to connect to.
        System.out.print("Started server on port: ");
        System.out.println(this.port);
    }

    public void run() {
        startConnection();
    }

    /**
     * Connect to server and host loop for multiple clients.
     * Close Connection when done.
     */
    private void startConnection() {
        try {
            while (isRunning) {
                new GreetServerClientHandler(server.accept()).start();
            }

            server.close();
            System.out.println("ended Server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a random unused int to be used as a roomId
     * @return roomId
     */
    protected int generateRoomId() {
        Random rand = new Random();
        int roomId;
        do {
            roomId = round(1000 + 8999 * rand.nextFloat());
        } while (rooms.contains(roomId));

        return roomId;
    }

    private boolean closeRoom(String roomId) {
        return rooms.remove(roomId);
    }



}