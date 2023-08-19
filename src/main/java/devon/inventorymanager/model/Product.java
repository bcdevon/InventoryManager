package devon.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
/**Represents a product in the inventory.*/
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private static int lastAssignedProductId = 0;
    private static List<Integer> usedProductIds = new ArrayList<>();

    /**creates a new product with a generated unique ID.*/
    public Product() {
        this.id = generateNewProductId();

    /**Generates a new unique product ID.
     *@return The generated product ID*/
    }
    public static int generateNewProductId() {
        //find the next available ID
        lastAssignedProductId = findNextProductId();
        //add new ID to list of used ID's
        usedProductIds.add(lastAssignedProductId);
        //return the new ID
        return lastAssignedProductId;
    }
    /**Finds the next available product ID that is not already used.
     * @return the next available product ID.*/
    private static int findNextProductId(){
        //increment the last assigned ID by 1
        int nextProductId = lastAssignedProductId +1;
        //check if the incremented ID has already been used
        while (isDuplicateId(nextProductId)){
            //if ID has already been used increment by 1 and check again
            nextProductId++;
        }
        //return the unique ID
        return nextProductId;
    }
    /**Checks if the given ID is a duplicate.
     * @param id  The ID to check for being a duplicate.
     * @return True if the ID is a duplicate, or else return false.*/
    private static boolean isDuplicateId(int id) {
        //loop through all the product ID's that have been used
        for (int i = 0; i < usedProductIds.size(); i++) {
            int usedProductId = usedProductIds.get(i);
            //check if ID being assigned has already been used.
            if (usedProductId == id) {
                //if ID already used return true
                return true;
            }
        }
        // check if the id is a duplicate of the test data
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        //loop through all the products
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            //check if the products ID match the given ID
            if (product.getId() == id) {
                // return true if ID is a duplicate
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

    /**constructor to create a new product
     * @param id unique ID of the product
     * @param max maximum inventory stock level allowed for the product
     * @param min minimum inventory stock level allowed for the product
     * @param name name of the product
     * @param price price of the product
     * @param stock current inventory stock level of the product*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        //set the values of the product using the provided values
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        //initialize the associatedParts list as and ObservableList
        associatedParts = FXCollections.observableArrayList();
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

    public int getMax() {return max; }

    public void setMax(int max) {

        this.max = max;
    }

    /**Adds an associated part to the product.
     * @param part The part to be added to the products associated parts list.*/
     public void addAssociatedPart(Part part) {
        associatedParts.add(part);
     }
     /**Removes an associated part from the product.
      * @param part The part to be removed from the products associated parts list.*/
     public void removeAssociatedPart(Part part) {
        associatedParts.remove(part);
     }
     /**Retrieves the list of associated parts for the product.
      *@return The list of associated parts.*/
     public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
     }
}
