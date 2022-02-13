package FirstTest;

/**
 * 
 * @author Darek Petersen
 *
 */
public class Handler {
  public static void main(String[] args) {

    System.out.println("started Handler");
    
    GreetServer server = new GreetServer(6667);
    server.start();

    
    
    GreetClient client = new GreetClient("localhost", 6667);
    client.start();


    System.out.println("ended Handler");
  }
}