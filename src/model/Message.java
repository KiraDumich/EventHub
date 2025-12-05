package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    private String sender;
    private String text;
    private LocalDateTime time;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.time = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }
    public String getText() {
        return text;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public String format() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + time.format(fmt) + "] " + sender + ": " + text;
    }

    @Override
    public String toString() {
        return format();
    }
}
