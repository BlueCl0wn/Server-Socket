/**
 * @author Darek Petersen
 * @version 1.0
 */
public class HandlerServer {
    public static rooms.Rooms Rooms;
    public static server.GreetServer server;

    public int serverPort;

    public static void main(String[] args) {
        System.out.println("started Handler");

        Rooms = new rooms.Rooms();
        server = new server.GreetServer(Rooms);

        server.start();

        System.out.println("ended Handler");

    }
}