package devon.inventorymanager.model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/** This is the Part Class.
 * This class represents a Part in the inventory.*/
public abstract class Part {
    //the unique id of the part
    private int id;
    //the last assigned part id
    private static int lastAssignedId = 0;
    //list of part ID's already used
    private static List<Integer> usedIds = new ArrayList<>();


    /** This is the Part constructor method.
     * This constructor is for creating a new part with a generated unique ID.*/
    public Part() {
        this.id = generateNewId();
    }

    /** This is the generateNewId method.
     * This method generates a unique part ID
     * @return  the generated Part ID*/
    public static int generateNewId() {
        //find the next available ID
        lastAssignedId = findNextId();
        //add new ID to the list of used ID's
        usedIds.add(lastAssignedId);
        //return the new ID
        return lastAssignedId;
        }

        /** This is the findNextId method.
         * This method finds the next available part ID that is not already used.
         * @return the next available part ID*/
    private static int findNextId() {
        //increment the last assigned ID by 1
        int nextId = lastAssignedId +1;
        //check if the incremented ID has already been used
        while (isDuplicateId(nextId)) {
            //if ID has already been used increment by 1 and check again
            nextId++;
        }
        //return the unique ID
        return nextId;
    }

    /** This is the isDuplicateID method.
     * This method checks if the given part ID has already been used.
     * @param id The ID to check for being a duplicate
     * @return True if the ID is a duplicate, or else return false*/
    private static boolean isDuplicateId(int id) {
        //Loop through all the part ID's
        for (int i = 0; i < usedIds.size(); i++) {
            int usedId = usedIds.get(i);
            //check if the ID being assigned has already been used
            if (usedId == id) {
                //if ID already used return to True
                return true;
            }
        }
        //Check if the id is already used in the test data
        ObservableList<Part> allParts = Inventory.getAllParts();
        //loop through all the parts
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            //check if the parts ID matches the given ID
            if (part.getId() == id) {
                //return true if the ID is already used
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

    /** constructor to create a new part
     * @param stock current inventory stock level of the part
     * @param price price of the part
     * @param name name of the part
     * @param min minimum inventory stock level allowed for the part
     * @param max maximum inventory stock level allowed for the part
     * @param id unique ID of the new part*/
    public Part(int id, String name, double price, int stock, int min, int max) {
        //set the attributes of the part using the provided values
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
