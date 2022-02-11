package rooms;

import FirstTest.GreetServerClientHandler;

/**
 * @author Darek Petersen
 */
public class Room {
    public int id;

    GreetServerClientHandler[] clients = new GreetServerClientHandler[2];

    /**
     * Main Constructor
     * @param id roomID of this room
     */
    public Room(int id, GreetServerClientHandler client1,GreetServerClientHandler client2) {
        this.id = id;

        //
        clients[0] = client1;
        clients[1] = client2;
    }

    /**
     * Main Constructor
     * @param id roomID of this room
     */
    public Room(int id, GreetServerClientHandler client1) {
        this.id = id;

        //
        clients[0] = client1;
    }

    /**
     * Main Constructor
     * @param id roomID of this room
     */
    public Room(int id) {
        this.id = id;
    }


    /**
     * Check if a client is in an array.
     * @param array Array that is to be searched through
     * @param c Client
     * @return boolean
     */
    public static int contains(final GreetServerClientHandler[] array, final GreetServerClientHandler c) {
        for(int i = 0; i < array.length; i++){
            if(array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public boolean addClient(GreetServerClientHandler client) {
        if (clients[1] == null) {
            clients[1] = client;
            return true;
            } else {
            return false;
        }
    }

    public boolean removeClient(GreetServerClientHandler client) {
        int pos = contains(clients, client);
        if (pos >= 0) {
            clients[pos] = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks for equality with parsed ID.
     * @param checkingId ID that is being compared
     * @return boolean
     */
    public boolean equal(final int checkingId) {
        return this.id == checkingId;
    }

    /**
     * Overrides hashcode method
     * I know that it doesn't even use a hash method, but whatever.
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + this.id;
    }

    /** Override .equals() method
     * Allows comparison of two instances of Room
     * @param obj Other Room instance
     * @return boolean
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Room other = (Room) obj;
        if (this.id == 0) {
            return other.id == 0;
        } else {
            return this.id == other.id;
        }
    }
}
