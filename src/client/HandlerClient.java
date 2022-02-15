package client;

import server.GreetServer;



/**
 * @author Darek Petersen
 * @version 1.0
 */
public class HandlerClient {

    public static void main(String[] args) {

        System.out.println("started HandlerClient1");


        GreetClient client1 = new GreetClient("10.9.0.115", 51128);
        client1.start();


        System.out.println("ended Handler");

    }
}