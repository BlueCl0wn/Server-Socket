package firstTest;

/**
 * @author Darek Petersen
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


        //GreetClient client2 = new GreetClient("localhost", 6667);
        //client2.start();


        System.out.println("ended Handler");
    }
}