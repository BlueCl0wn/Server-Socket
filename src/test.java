import server.*;

public class test {
    static GreetServerClientHandler[] clients;

    public static void main(String[] args) {
        clients  = new GreetServerClientHandler[2];

        System.out.println(clients[0]);
    }
}
