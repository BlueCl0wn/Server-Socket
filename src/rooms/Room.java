package rooms;

import server.GreetServerClientHandler;

import java.util.Observable;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class Room extends Observable {
    public final int id;

    public final GreetServerClientHandler[] clients;

    public String message;

    /**
     * Main Constructor
     *
     * @param id roomID of this room
     */
    public Room(int id, GreetServerClientHandler client1, GreetServerClientHandler client2) {
        this.id = id;

        // initiate client array
        clients = new GreetServerClientHandler[2];
        clients[0] = client1;
        clients[1] = client2;


        if (client1 != null) {
            this.addObserver(client1);
        }
        if (client2 != null) {
            this.addObserver(client2);
        }
    }

    /**
     * Main Constructor
     *
     * @param id roomID of this room
     */
    public Room(int id, GreetServerClientHandler client1) {
        this(id, client1, null);
    }

    /**
     * Main Constructor
     * @param id roomID of this room
     */
    public Room(int id) {
        this(id, null, null);
    }


    /**
     * Check if a client is in an array.
     *
     * @param array Array that is to be searched through
     * @param c     Client
     * @return boolean
     */
    public static int posOfElementInArray(final GreetServerClientHandler[] array, final GreetServerClientHandler c) throws Exception {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        throw new Exception("Element not found in array.");
    }

    public boolean addClient(GreetServerClientHandler client) {
        for (GreetServerClientHandler c : clients) {
            if (c == null) {
                clients[1] = client;
                this.addObserver(client);
                return true;
            } else if (c == client) {
                return false;
            }
        }
        return false;
    }

    public void removeClient(GreetServerClientHandler client) throws Exception {
        int pos = posOfElementInArray(clients, client);
        if (pos >= 0 & pos < 3) {
            this.clients[pos] = null;
            this.deleteObserver(client);
        }
    }

    /**
     * Checks for equality with parsed ID.
     *
     * @param checkingId ID that is being compared
     * @return boolean
     */
    public boolean equal(final int checkingId) {
        return this.id == checkingId;
    }

    /**
     * Overrides hashcode method
     * I know that it doesn't even use a hash method, but whatever.
     *
     * @return Hashcode
     */
    public int hashCode() {
        final int prime = 31;
        return prime + this.id;
    }

    /**
     * Override .equals() method
     * Allows comparison of two instances of Room
     *
     * @param obj Other Room instance
     * @return boolean
     */
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

    /**
     *
     */
    public boolean isEmpty() {
        for (GreetServerClientHandler c : clients) {
            if (c == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return message variable to asking client.
     * @return String
     */
    public String getMessage() {
        if (!(this.message.equals(""))) {
            return this.message;
        } else {
            return "No Message found";
        }
    }

    /**
     * Listener
     * @param msg
     */
    public void receiveMessage(String msg) throws Exception {
        synchronized (this) {
            this.message = msg;
        }
        setChanged();
        notifyObservers(); // TODO get done
    }

    public GreetServerClientHandler[] getMembers() {
        return clients;
    }

    public int countMembers() {
        int counter = 0;
        for (GreetServerClientHandler c : clients) {
            if (c != null) {
                counter++;
            }
        }
        return counter;
    }
}

