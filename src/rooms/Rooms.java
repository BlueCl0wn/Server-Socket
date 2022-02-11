package rooms;

import FirstTest.GreetServerClientHandler;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;


/**
 * @author Darek Petersem
 */
public class Rooms {
    private static ArrayList<Room> openRooms;

    public Rooms() {
        openRooms = new ArrayList<>();
    }

    /**
     * Generates a random unused int to be used as a roomId
     * @return roomId
     */
    private static int generateRoomId() {
        Random rand = new Random();
        int roomId;
        do {
            roomId = round(1000 + 8999 * rand.nextFloat());
        } while (openRooms.contains(roomId) & roomId != 0);

        return roomId;
    }


    /**
     * Creates a new room and assigns 'this' to that room.
     */
    public static int createRoom(GreetServerClientHandler client) {
        if(client.room == 0) {
            int id = generateRoomId();
            openRooms.add(new Room(id, client));
            return id;
        } else {
            return client.room;
        }
    }

    /**
     * Leaves current room if in on
     * @return
     */
    public static boolean leaveRoom(GreetServerClientHandler client) {
        if(client.room == 0) {
            return false;
        } else {
            client.room = 0;
            return true;
        }
    }

    /**
     * Uses parameter 'roomId' to join an existing room.
     * @param client Client who wants to join a room.
     * @param roomId Id of the room that is to be joined.
     */
    public static boolean joinRoom(GreetServerClientHandler client, int roomId) {
        for (int i = 0; i < openRooms.size(); i++) {

        }
        for(Room r : openRooms) {
            Room.equal(roomId);
        }
        if(openRooms.contains(new Room(roomId))) {
            return false;
        } else {
            openRooms.add()
            return true;
        }
    }
}
