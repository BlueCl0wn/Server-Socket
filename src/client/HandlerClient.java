package client;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class HandlerClient {

    public static void main(String[] args) {

        System.out.println("started HandlerClient1");


        GreetClient client1 = new GreetClient("localhost", 61438);
        client1.start();


        System.out.println("ended Handler");

    }
}