package FirstTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import static java.lang.Math.round;

public class GreetServerClientHandler extends GreetServer {
    private final Socket client;
    private PrintWriter out;
    private BufferedReader in;

    public int room = 0;

    public GreetServerClientHandler(Socket socket) {
        this.client = socket;
    }


    /**
     * Allows class to be used with Thread.
     */
    public void run() {
        this.communicate();
    }

    /**
     * Basically a main loop
     */
    public void communicate() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                switch (inputLine) {
                    case "STOP":
                        this.stopConnection();
                        break;
                    case "CREATE ROOM":
                        this.createRoom();
                        break;
                    case "LEAVE ROOM":
                        this.leaveRoom();
                        break;
                    case "START GAME":

                    case "Hello World!":
                        System.out.println(inputLine);
                        out.println("Hello World to you as well, my dear friend!");
                        break;
                    default:
                        if (inputLine.startsWith("JOIN ROOM")) {
                            int roomId = Integer.parseInt(inputLine.substring(9));
                            this.joinRoom(roomId);
                        } else {
                            out.println("I cannot understand your gibberish.");
                        }
                }
                System.out.println(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a new room and assigns 'this' to that room.
     */
    public void createRoom() {
        if (this.room == 0) {
            int id = rooms.Rooms.createRoom(this);
            if (this.room == id) {
                out.println("There was a problem"); // TODO bad error message
            } else {
                out.println("You have been moved to room " + String.valueOf(id) + ".");
                this.room = id;
            }
        } else {
            out.println("You are already in room " + this.room + ".");
        }
    }

    /**
     * Leaves current room if in on
     */
    public void leaveRoom() {
        if (this.room == 0) {
            out.println("You aren't assigned to a room and therefore cannot leave one.");
        } else {
            this.room = 0;
            out.println("You successfully left your room.");
        }
    }

    /**
     * Uses parameter 'roomId' to join an existing room.
     *
     * @param roomId Id of the room that is to be joined.
     */
    public void joinRoom(int roomId) {
        if (rooms.Rooms.joinRoom(this, roomId)) {
            out.println("You have been successfuly joined room + " + this.room);
        } else {
            out.println("This room does not currently exist. " +
                    "If you want to create a new room use the command 'CREATE ROOM'.");
        }
    }


    /**
     * Close all connections and client-socket
     */
    public void stopConnection() {
        out.println("bye");
        try {
            this.leaveRoom(); // Leave room if necessary

            // Close communication channels
            in.close();
            out.close();

            System.out.println("Server disconnected");

            this.client.close(); // Close socket

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}