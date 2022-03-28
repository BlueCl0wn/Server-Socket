package client;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class HandlerClient2 {
    public static void main(String[] args) {
        System.out.println("started HandlerClient2");

        GreetClient client2 = new GreetClient("localhost", 61438);
        client2.start();


        System.out.println("ended HandlerClient2");

    }
}