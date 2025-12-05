package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Purchase implements Serializable {
    private final String username;
    private final Ticket ticket;
    private final LocalDateTime time;

    public Purchase(String username, Ticket ticket) {
        this.username = username;
        this.ticket = ticket;
        this.time = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }
    public Ticket getTicket() {
        return ticket;
    }
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "[" + time + "] " + username + " bought " + ticket.toString(); 
    }
}
