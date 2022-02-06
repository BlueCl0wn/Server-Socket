package FirstTest;

import java.net.*;
import java.io.*;
import java.lang.Thread;

/**
 * @author Darek Petersen
 */
public class GreetServer extends Thread{
    private ServerSocket server;

    public int port;

    public GreetServer(int port) {
        System.out.println("started Server");

        this.port = port;
    }

    public void run() {
        startConnection();
    }

    /**
     * Connect to server and host loop for multiple clients.
     * Close Connection when done.
     */
    public void startConnection() {
        try {
                server = new ServerSocket(this.port);
            for (int i = 0; i < 5; i++) {
                new GreetServerHandler(server.accept()).start();
            }

            server.close();
            System.out.println("ended Server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class GreetServerHandler extends Thread {
        private final Socket client;
        private PrintWriter out;
        private BufferedReader in;

        public GreetServerHandler(Socket socket) {
            this.client = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {

                    if(".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    } else if("".equals(inputLine)) {
                        break;
                    } else if("Hello World!".equals(inputLine)) {
                        System.out.println(inputLine);
                        out.println("Hello World to you as well, my dear friend!");
                    } else {
                        out.println("I cannot understand your gibberish.");
                        break;
                    }
                    System.out.println(inputLine);
                }
                stopConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stopConnection() {
            try {
                in.close();
                out.close();

                this.client.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}