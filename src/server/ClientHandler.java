package server;
import model.Purchase;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ServerState state;
    private final List<ClientHandler> allClients;

    private PrintWriter out;
    private BufferedReader in;
    private String username = "Anon";

    public ClientHandler(Socket socket, ServerState state, List<ClientHandler> allClients){
        this.socket = socket;
        this.state = state;
        this.allClients = allClients;
    }

    @Override
    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome! Enter your name:");
            String name = in.readLine();
            if (name != null && name.trim().length()>0) username = name.trim();

            out.println("Commands: /buy Walkup | /buy Advance | /buy StudentAdvance | /history | /exit");
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("/buy ")) {
                    String type = line.substring(5).trim();
                    Purchase p = state.buyTicket(username, type);
                    if (p != null) {
                        broadcast(username + " bought ticket: " + p.getTicket().toString());
                    } else {
                        out.println("No ticket available of type: " + type);
                    }
                } else if (line.equals("/history")) {
                    for (Purchase p : state.recentPurchases()) out.println(p.toString());
                } else if (line.equals("/exit")) {
                    out.println("Bye!");
                    break;
                } else {
                    broadcast(username + ": " + line);
                }
            }
        } catch (IOException e){
            System.err.println("ClientHandler error: "+e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            allClients.remove(this);
            broadcast(username + " disconnected.");
        }
    }

    private synchronized void broadcast(String msg) {
        for (ClientHandler ch : allClients) {
            ch.out.println(msg);
        }
    }
}
