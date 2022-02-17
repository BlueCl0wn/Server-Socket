import client.GreetClient;
import server.GreetServer;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class Handler {
    public static rooms.Rooms Rooms;
    public static GreetServer server;

    public static void main(String[] args) {

        Rooms = new rooms.Rooms();
        server = new GreetServer(Rooms);

        System.out.println("started Handler");

        server.start();

        GreetClient client1 = new GreetClient("localhost", server.port);
        client1.start();

        System.out.println("ended Handler");

    }
}