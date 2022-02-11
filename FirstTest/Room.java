package FirstTest;

/**
 * @author Darek Petersen
 */
public class Room {
    public String id;

    public GreetServerClientHandler client1;
    public GreetServerClientHandler client2;

    /**
     * Main Constructor
     * @param id roomID of this room
     */
    public Room(String id, GreetServerClientHandler client1,GreetServerClientHandler client2) {
        this.id = id;

        //
        this.client1 = client1;
        this.client2 = client2;
    }

    /**
     * Checks for equality with parsed ID.
     * @param checkingId ID that is being compared
     * @return boolean
     */
    public boolean equals(String checkingId) {
        return this.id.equals(checkingId);
    }
}
