package devon.inventorymanager.model;

/** This is the InHouse class.
 * The InHouse class is a class that extends from the Part class
 * It represents an InHouse part in the inventory.*/
public class InHouse extends Part {
    //The machine ID of the part
    private int machineID;

    /** This is the constructor method for the InHouse part.
     * This constructor creates a new InHouse part.
     * @param stock Current inventory stock level of the InHouse part
     * @param price Price of the part
     * @param name Name of the part
     * @param min Minimum inventory stock level allowed for the part
     * @param max Maximum inventory stock level allowed for the part
     * @param id Unique ID of the part
     * @param machineID machine id of the part*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This is the getMachineID method.
     * This gets the machine ID of an InHouse Part.
     * @return machineID*/
    public int getMachineID() {
        return machineID;
    }

    /** This is the setMachineID method.
     * This sets the machineID of the InHouse part.
     * @param machineID */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
