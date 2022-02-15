package client;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class HandlerClient2 {
    //public static rooms.Rooms Rooms;
    //public static GreetServer server;

    public static void main(String[] args) {

        //Rooms = new rooms.Rooms();
        // server = new GreetServer(Rooms);

        System.out.println("started Handler");

        //server.start();

        GreetClient client1 = new GreetClient("10.9.0.115", 51128);
        client1.start();

        //GreetClient client2 = new GreetClient("localhost", 6667);
        //client2.start();

        System.out.println("ended Handler");

    }
}