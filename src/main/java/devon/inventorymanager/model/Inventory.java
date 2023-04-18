package devon.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    public static void addTestData() {
        InHouse wheel = new InHouse(3, "Wheel", 25.00, 5, 1, 10, 42);
        Inventory.addPart(wheel);
        OutSourced Rim = new OutSourced(5, "Rim", 50.00, 8, 1, 10, "Wheel company" );
        Inventory.addPart(Rim);
        Product motorcycle = new Product(6, "Electric motorcycle", 15000.75, 1, 1, 5);
        Inventory.addProduct(motorcycle);
    }
}
