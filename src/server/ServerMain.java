package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        int port = 6000;
        DatabaseHelper db = new DatabaseHelper("jdbc:postgresql://localhost:5432/EventHub", "postgres", "KiDu2005");
        ServerState state = new ServerState(db);


        List<ClientHandler> clients = new ArrayList<>();
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server listening on " + port);
            while (true) {
                Socket s = ss.accept();
                System.out.println("New client connected: " + s.getRemoteSocketAddress());
                ClientHandler handler = new ClientHandler(s, state, clients);
                clients.add(handler);
                new Thread(handler).start();
            }
        }
    }
}
