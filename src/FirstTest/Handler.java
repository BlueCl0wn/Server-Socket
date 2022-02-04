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

    System.out.println("1");
    
    
    GreetClient client = new GreetClient("localhost", 6667);
    System.out.println("2");
    client.start();

    System.out.println("3");

    System.out.println("ended Handler");
    System.out.println("--------------");
  }
}