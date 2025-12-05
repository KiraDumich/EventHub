package model;

public class Advance extends Ticket {
    private final int daysInAdvance;

    public Advance(int daysInAdvance) {
        this.daysInAdvance = daysInAdvance;
    }

    @Override
    public double getPrice() {
        return daysInAdvance >= 10 ? 30 : 40;
    }

    @Override
    public String getType() {
        return "Advance(" + daysInAdvance + ")";
    }
}
