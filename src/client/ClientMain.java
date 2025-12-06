package client;

import java.io.*;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 6000;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            
            Thread reader = new Thread(() -> {
                try {
                    String s;
                    while ((s = in.readLine()) != null) {
                        System.out.println(s);
                    }
                }
                catch (IOException ignored) {}
            });

            reader.setDaemon(true);
            reader.start();

            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                if (line.equals("/exit")) {
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
