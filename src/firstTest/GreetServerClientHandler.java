package firstTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class GreetServerClientHandler extends Thread{
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public final rooms.Rooms Rooms;

    public int room = 0;

    public GreetServerClientHandler(Socket socket, rooms.Rooms Rooms) {
        this.clientSocket = socket;
        this.Rooms = Rooms;
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
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
                    default -> {
                        if (inputLine.startsWith("JOIN ROOM")) {
                            int roomId = Integer.parseInt(inputLine.substring(10)); // TODO Add method to check for valid roomId.
                            this.joinRoom(roomId);
                        } else {
                            out.println("I cannot understand your gibberish.");
                        }
                    }
                }
                System.out.println(inputLine);
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
        if (this.room == 0) {
            int id = this.Rooms.createRoom(this);
            if (this.room == id) {
                out.println("There was a problem. " +
                        "For some reason you are already in the just created room. " +
                        "Don't ask me how that happened");
            } else {
                out.println("You have been moved to room " + id + ".");
                this.room = id;
            }
        } else {
            out.println("You are already in room " + this.room + ".");
        }
    }

    /**
     * Leaves current room if in on
     */
    public void leaveRoom(GreetServerClientHandler client) {
        if (this.Rooms.leaveRoom(client)) {
            this.room = 0;
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
            out.println("You have successfully joined room " + this.room);
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
     * @return int roomId
     */
    public int getRoomId() {
        return this.room;
    }

    /**
     * Returns String of containing information about current room.
     * If 'Not connected to a room' ('roomId' == 0) it returns exactly that.
     * @return String
     */
    public String getCurrentRoomId() {
        if(this.room == 0) {
            return "You are not connected to a room";
        } else {
            return "You are in room " + this.room;
        }
    }

}