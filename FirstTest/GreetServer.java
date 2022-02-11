package FirstTest;

import java.net.*;
import java.io.*;
import java.lang.Thread;

/**
 * @author Darek Petersen
 * @version 1.0
 */
public class GreetServer extends Thread{
    private ServerSocket server;

    public final int port;


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
    private void startConnection() {
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
                label:
                while ((inputLine = in.readLine()) != null) {
                    switch (inputLine) {
                        case ".":
                            out.println("bye");
                            break label;
                        case "":
                            break label;
                        case "Hello World!":
                            System.out.println(inputLine);
                            out.println("Hello World to you as well, my dear friend!");
                            break;
                        default:
                            out.println("I cannot understand your gibberish.");
                            break label;
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