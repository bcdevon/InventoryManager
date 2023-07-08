package devon.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private static int lastAssignedProductId = 0;
    private static List<Integer> usedProductIds = new ArrayList<>();
    //generate an id for each product added ids should be incremented by 1 each time and be unique
    public Product() {
        this.id = generateNewProductId();
    }
    public static int generateNewProductId() {
        lastAssignedProductId = findNextProductId();
        usedProductIds.add(lastAssignedProductId);
        return lastAssignedProductId;
    }
    //increment 1 from the last assigned product id if the id is a duplicate increment by 1 until it is unique.
    private static int findNextProductId(){
        int nextProductId = lastAssignedProductId +1;
        while (isDuplicateId(nextProductId)){
            nextProductId++;
        }
        return nextProductId;
    }
    //If the id is a duplicate return true
    private static boolean isDuplicateId(int id) {
        for (int i = 0; i < usedProductIds.size(); i++) {
            int usedProductId = usedProductIds.get(i);
            if (usedProductId == id) {
                return true;
            }
        }
        // if the id is a duplicate of the test data return true
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getId() == id) {
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

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
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
     public void addAssociatedPart(Part part) {
        associatedParts.add(part);
     }
     public void removeAssociatedPart(Part part) {
        associatedParts.remove(part);
     }
     public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
     }
}
