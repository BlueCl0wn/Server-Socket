package FirstTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GreetServerClientHandler extends GreetServer {
    private final Socket client;
    private PrintWriter out;
    private BufferedReader in;

    public GreetServerClientHandler(Socket socket) {
        this.client = socket;
    }


    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                switch (inputLine) {
                    case "STOP":
                        out.println("bye");
                        // isRunning = false;
                        stopConnection();
                        break;
                    case "CREATE ROOM":
                        int id = this.generateRoomId();
                        out.println("You have been moved to room " + toString(id)); //TODO
                    case "Hello World!":
                        System.out.println(inputLine);
                        out.println("Hello World to you as well, my dear friend!");
                    default:
                        out.println("I cannot understand your gibberish.");
                }
                System.out.println(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stopConnection() {
        try {
            in.close();
            out.close();

            System.out.println("Server disconnected");
            this.client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}