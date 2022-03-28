package server;

// Communication with client device

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// Socket
import java.net.Socket;

// Event Handling
import java.util.Arrays;
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
     *
     * @param socket clients socket
     * @param Rooms instance if rooms.Rooms for room management
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
    public void communicate() {
        try {

            // Main-Loop for this client on server-side
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                switch (inputLine) {
                    case "STOP" -> {
                        this.stopConnection();
                        return;
                    }
                    case "CREATE ROOM" -> {
                        this.createRoom();
                        out.println(".");
                    }
                    case "LEAVE ROOM" -> {
                        this.leaveRoom(this);
                        out.println(".");
                    }
                    case "START GAME" -> {
                        // TODO start game method
                        out.println("coming soon");
                        out.println(".");
                    }
                    case "CURRENT ROOM" -> {
                        out.println(this.getCurrentRoomId());
                        out.println(".");
                    }
                    case "Hello World!" -> {
                        System.out.println(inputLine);
                        out.println("Hello World to you as well, my dear friend!");
                        out.println(".");
                    }
                    case "GET MESSAGE" -> {
                        out.println(this.getFromRoom());
                        out.println(".");
                    }
                    case "GET MEMBERS" -> {
                        out.println(Arrays.toString(this.getMembers()));
                        out.println(".");
                    }
                    case "COUNT MEMBERS" -> {
                        out.println(this.countMembers());
                        out.println(".");
                    }
                    case "." -> {
                        out.println("This message is not allowed");
                        out.println(".");
                    }
                    default -> {
                        if (inputLine.startsWith("JOIN ROOM")) {
                            // Join room with id from string
                            // Outside switch-statement because it does no support '.startWith()'.
                            String roomIdString = inputLine.substring(10);
                            if (isValidRoomIdString(roomIdString)) {
                                this.joinRoom(Integer.parseInt(roomIdString));
                            } else {
                                out.println("This is not a valid room ID or wrong command syntax." +
                                        "Correct: JOIN ROOM <roomId> ");
                                out.println(".");
                            }
                        } else if (this.roomId != 0) {
                            // If protocol doesn't recognise command and 'this' is connected to a room,
                            // then send message to room.
                            this.sendToRoom(inputLine);
                            System.out.println(inputLine);

                        } else {
                            // Unknown Command
                            System.out.println(inputLine);
                            out.println("Error: unknown command and/or not connected to a room.");
                            out.println(".");
                        }
                    }
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if string only contains ints and length is maximum 4 long.
     * Requirements for valid roomId.
     * @param roomId String of roomId
     * @return boolean
     */
    private boolean isValidRoomIdString(String roomId) {
        return roomId.matches("[0-9]+") && roomId.length() <= 4;
    }

    /**
     * Return array of members if current room
     * If not in room, returns null array
     * @return GreetServerClientHandler[]
     */
    private GreetServerClientHandler[] getMembers() {
        if (this.roomId != 0) {
            return this.room.getMembers();
        } else {
            return new GreetServerClientHandler[2];
        }

    }

    /**
     * Gives number of clients in current room
     * @return int
     */
    private int countMembers() {
        if (this.roomId != 0) {
            return this.room.countMembers();
        } else {
            return -1;
        }
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
            }
        } else {
            out.println("You are already in room " + this.roomId + ".");
        }

        // Over
        out.println(".");
    }

    /**
     * Leaves current room if in on
     */
    public void leaveRoom(GreetServerClientHandler client) throws Exception {
        if (this.Rooms.leaveRoom(client)) {
            this.roomId = 0;
            out.println("You successfully left your room.");
        } else {
            out.println("There was an error while trying to leave the room. " +
                    "You are probably not assigned to a room and therefore cannot leave one.");
        }
        // Over
        out.println(".");
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
            out.println("You have successfully joined room " + this.roomId);

        } else {
            out.println("There was an error while connecting to room " + roomId + ". " +
                    "This room does either not currently exist or you have already join it. ");
        }
        // Over
        out.println(".");
    }

    /**
     * Close all connections and client-socket
     */
    public void stopConnection() {
        out.println("bye");
        try {
            this.leaveRoom(this); // Leave room if necessary

            // send STOP
            out.println("STOP");
            out.println(".");

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
        if (this.roomId == 0) {
            return "You are not connected to a room";
        } else {
            return "You are in room " + this.roomId;
        }
    }

    /**
     * Send message to room
     *
     * @param msg String send to room
     */
    public void sendToRoom(String msg){
        this.room.receiveMessage(msg);
        this.room.receiveMessage(".");
    }

    /**
     * Gets a message form current room
     *
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