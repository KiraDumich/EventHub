package model;

import java.io.Serializable;

public abstract  class Ticket implements Serializable {
    private final int serialNumber;
    private static int nextSerialNumber = 1;

    public Ticket() {
        this.serialNumber = nextSerialNumber++;
    }

    public abstract double getPrice();
    public abstract String getType();

    @Override
    public String toString() {
        return "Ticket{" + "id=" + serialNumber + ", type=" + getType() + ", price=" + getPrice() + "}";
    }
}
