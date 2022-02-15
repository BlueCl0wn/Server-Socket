package server;

// Communication with client device
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// Socket
import java.net.Socket;

// Event Handling
import java.util.Observable;
import java.util.Observer;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class GreetServerClientHandler extends Thread implements Observer {
    // Own socket
    private final Socket clientSocket;

    // communication channels
    private PrintWriter out;
    private BufferedReader in;

    // Rooms instance of main
    public final rooms.Rooms Rooms;

    // If of current room. 0 corresponds to no room.
    public int roomId = 0;
    public rooms.Room room;


    /**
     * Main Constructor
     * @param socket
     * @param Rooms
     */
    public GreetServerClientHandler(Socket socket, rooms.Rooms Rooms) {
        this.clientSocket = socket;
        this.Rooms = Rooms;

        // Initiate in and out channels to client
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


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
    public boolean communicate() {
        try {

            // Main-Loop for this client on server-side
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                switch (inputLine) {
                    case "STOP" -> {
                        this.stopConnection();
                        return false;
                    }
                    case "CREATE ROOM" ->
                            this.createRoom();
                    case "LEAVE ROOM" ->
                            this.leaveRoom(this);
                    case "START GAME" ->
                            // TODO start game method
                            out.println("coming soon");
                    case "CURRENT ROOM" ->
                            out.println(this.getCurrentRoomId());
                    case "Hello World!" -> {
                        System.out.println(inputLine);
                        out.println("Hello World to you as well, my dear friend!");
                    }
                    case "GET MESSAGE" ->
                            out.println(this.getFromRoom());
                    default -> {
                        if (inputLine.startsWith("JOIN ROOM")) {
                            // Join room with id from string
                            // Outside switch-statement because it does no support '.startWith()'.
                            int roomId = Integer.parseInt(inputLine.substring(10)); // TODO Add method to check for valid roomId.
                            this.joinRoom(roomId);

                        } else if (this.roomId != 0) {
                            // If protocol doesn't recognise command and 'this' is connected to a room,
                            // then send message to room.
                            this.sendToRoom(inputLine);

                        } else {
                            // Unknown Command
                            out.println("Error: unknown command");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Creates a new room and assigns 'this' to that room.
     */
    public void createRoom() {
        if (this.roomId == 0) {
            int id = this.Rooms.createRoom(this);
            if (this.roomId == id) {
                out.println("There was a problem. " +
                        "For some reason you are already in the just created room. " +
                        "Don't ask me how that happened");
            } else {
                out.println("You have been moved to room " + id + ".");
                this.roomId = id;
                this.room = this.Rooms.getRoomFromList(roomId);
                this.room.addObserver(this);
            }
        } else {
            out.println("You are already in room " + this.roomId + ".");
        }
    }

    /**
     * Leaves current room if in on
     */
    public void leaveRoom(GreetServerClientHandler client) {
        if (this.Rooms.leaveRoom(client)) {
            this.roomId = 0;
            out.println("You successfully left your room.");
        } else {
            out.println("There was an error while trying to leave the room. " +
                    "You are probably not assigned to a room and therefore cannot leave one.");
        }
    }

    /**
     * Uses parameter 'roomId' to join an existing room.
     *
     * @param roomId ID of the room that is to be joined.
     */
    public void joinRoom(int roomId) {
        if (this.Rooms.joinRoom(this, roomId)) {
            this.roomId = roomId;
            this.room = this.Rooms.getRoomFromList(roomId);
            this.room.addObserver(this);
            out.println("You have successfully joined room " + this.roomId);

        } else {
            out.println("There was an error while connecting to room " + roomId + ". " +
                    "This room does either not currently exist or you have already join it. ");
        }
    }

    /**
     * Close all connections and client-socket
     */
    public void stopConnection() {
        out.println("bye");
        try {
            this.leaveRoom(this); // Leave room if necessary

            // Close communication channels
            in.close();
            out.close();

            System.out.println("Server disconnected from client");

            this.clientSocket.close(); // Close socket

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns room id.
     *
     * @return int roomId
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Returns String of containing information about current room.
     * If 'Not connected to a room' ('roomId' == 0) it returns exactly that.
     *
     * @return String
     */
    public String getCurrentRoomId() {
        if (this.roomId== 0) {
            return "You are not connected to a room";
        } else {
            return "You are in room " + this.roomId;
        }
    }

    /**
     * Send message to room
     * @param msg
     */
    public void sendToRoom(String msg) {
        this.room.receiveMessage(msg);
    }

    /**
     * Gets a message form current room
     * @return String
     */
    public String getFromRoom() {
        return this.room.getMessage();
    }

    @Override
    public void update(Observable o, Object arg) {
        String msg = ((rooms.Room) o).getMessage();
        out.println(msg);
    }
}