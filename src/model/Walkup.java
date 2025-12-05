package model;

public class Walkup extends Ticket {
    @Override
    public double getPrice() {
        return 50.0;
    }

    @Override
    public String getType() {
        return "Walkup";
    }
}
