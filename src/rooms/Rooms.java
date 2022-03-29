package rooms;

import server.GreetServerClientHandler;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;


/**
 * @author Darek Petersem
 * @version 1.0
 */
public class Rooms {
    private final ArrayList<Room> openRooms;

    public Rooms() {
        openRooms = new ArrayList<>();
    }

    /**
     * Generates a random unused int to be used as a roomId
     *
     * @return roomId
     */
    public int generateRoomId() {
        Random rand = new Random();
        int roomId;
        do {
            roomId = round(1000 + 8999 * rand.nextFloat());
        } while (openRooms.contains(new Room(roomId)) & roomId != 0);

        return roomId;
    }


    /**
     * Creates a new room and assigns 'this' to that room.
     */
    public int createRoom(GreetServerClientHandler client) {
        if (client.roomId == 0) {
            int id = this.generateRoomId();
            openRooms.add(new Room(id, client));
            return id;
        } else {
            return client.roomId;
        }
    }

    /**
     * Uses parameter 'roomId' to join an existing room.
     *
     * @param client Client who wants to join a room.
     * @param roomId ID of the room that is to be joined.
     */
    public boolean joinRoom(GreetServerClientHandler client, int roomId) throws Exception {
        for (Room openRoom : openRooms) {
            if (openRoom.id == roomId) {
                return openRoom.addClient(client);
            }
        }
        return false; // room does not exist
    }

    /**
     * Leaves current room if in on
     *
     * @return boolean
     */
    public boolean leaveRoom(GreetServerClientHandler client) throws Exception {
        int clientId = client.getRoomId();
        if (clientId == 0) {
            return false;
        } else {
            Room room = this.getRoomFromList(clientId);
            int pos = getRoomIndex(clientId);
            openRooms.remove(pos).removeClient(client);

            if (room.isEmpty()) {
                openRooms.remove(room);
            }

            client.roomId = 0;
            return true;
        }
    }

    /**
     * Returns room by roomId
     * @param roomId ID of searched room
     * @return Room
     */
    public Room getRoomFromList(int roomId) {
        for (Room openRoom : openRooms) {
            if (openRoom.id == roomId) {
                return openRoom;
            }
        }
        return null;
    }

    public int getRoomIndex(int roomId) {
        for (int i = 0; i < this.openRooms.size(); i++) {
            if (this.openRooms.get(i).id == roomId) {
                return i;
            }
        }
        return -1;
    }
}
