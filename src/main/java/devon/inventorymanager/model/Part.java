package devon.inventorymanager.model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public abstract class Part {
    private int id;
    private static int lastAssignedId = 0;
    private static List<Integer> usedIds = new ArrayList<>();
    //generate an id for each part added and increment by 1 create a list of ids that have been used
    public Part() {
        this.id = generateNewId();
    }
    public static int generateNewId() {
        lastAssignedId = findNextId();
        usedIds.add(lastAssignedId);
        return lastAssignedId;
        }
      // increment 1 from the last assigned id if the id is a duplicate increment by 1 until it is unique.
    private static int findNextId() {
        int nextId = lastAssignedId +1;
        while (isDuplicateId(nextId)) {
            nextId++;
        }
        return nextId;
    }
    //if the id is a duplicate return true
    private static boolean isDuplicateId(int id) {
        for (int i = 0; i < usedIds.size(); i++) {
            int usedId = usedIds.get(i);
            if (usedId == id) {
                return true;
            }
        }
        //if the id is a duplicate of the test data return true
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == id) {
                return true;
            }
        }

        return false;
    }

    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
