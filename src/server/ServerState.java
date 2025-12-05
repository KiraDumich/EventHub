package server;
import model.*;
import util.DataContainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerState {
    private final DataContainer<Ticket> tickets;
    private final List<Purchase> purchases = new ArrayList<>();
    private final DatabaseHelper db;
    private final File backupFile = new File("purchases.ser");

    public ServerState(DatabaseHelper db) {
        this.db = db;
        this.tickets = new DataContainer<>(100);
        // наполним несколькими билетами
        try {
            for (int i=0;i<10;i++) tickets.add(new Walkup());
            for (int i=0;i<5;i++) tickets.add(new Advance(12));
            for (int i=0;i<5;i++) tickets.add(new StudentAdvance(12));
        } catch (Exception ignored){}
        loadFromFile();
    }

    public synchronized Purchase buyTicket(String username, String type) {
        // простой поиск по типу
        List<Ticket> list = tickets.snapshot();
        for (Ticket t : list) {
            if (t.getType().startsWith(type)) {
                boolean removed = tickets.remove(t);
                if (!removed) return null;
                Purchase p = new Purchase(username, t);
                purchases.add(p);
                db.savePurchase(p);
                saveToFile();
                return p;
            }
        }
        return null;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(backupFile))){
            oos.writeObject(purchases);
        } catch (IOException e){
            System.err.println("Save file error: "+e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile(){
        if (!backupFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(backupFile))){
            Object obj = ois.readObject();
            if (obj instanceof List) {
                purchases.clear();
                purchases.addAll((List<Purchase>) obj);
            }
        } catch (Exception e){
            System.err.println("Load file error: "+e.getMessage());
        }
    }

    public synchronized List<Purchase> recentPurchases() {
        return new ArrayList<>(purchases);
    }
}
