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

    private String room = "0000";

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
                            this.joinRoom(inputLine.substring(9));
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
     * Generates a random unused int to be used as a roomId
     * @return roomId
     */
    private int generateRoomId() {
        Random rand = new Random();
        int roomId;
        do {
            roomId = round(1000 + 8999 * rand.nextFloat());
        } while (openRooms.contains(roomId));

        return roomId;
    }

    /**
     * Creates a new room and assigns 'this' to that room.
     */
    public void createRoom() {
        if(this.room.equals("0000")) {
            String id = String.valueOf(this.generateRoomId());
            out.println("You have been moved to room " + id + ".");
            this.room = id;
        } else {
            out.println("You are already in room " + this.room + ".");
        }
    }

    /**
     * Leaves current room if in on
     */
    public void leaveRoom() {
        if(this.room.equals("0000")) {
            out.println("You aren't assigned to a room and therefore cannot leave one.");
        } else {
            this.room = "0000";
            out.println("You successfully left your room.");
        }
    }

    /**
     * Uses parameter 'roomId' to join an existing room.
     * @param roomId Id of the room that is to be joined.
     */
    public void joinRoom(String roomId) {
        if(openRooms.contains(roomId)) {
            this.room = roomId;
        } else {
            out.println("This room does not currently exist. " +
                        "If you want to create a new room use the command 'CREATE ROOM'.");
        }
    }
    public void stopConnection() {
        out.println("bye");
        try {
            in.close();
            out.close();

            System.out.println("Server disconnected");
            this.client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}