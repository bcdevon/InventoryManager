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
    public static void deletePart(Part part){
        getAllParts().remove(part);
    }
    public static void deleteProduct(Product product) {getAllProducts().remove(product);}
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    public static void addTestData() {
        InHouse wheel = new InHouse(1, "Wheel", 25.00, 5, 1, 10, 42);
        Inventory.addPart(wheel);
        OutSourced Rim = new OutSourced(2, "Rim", 50.00, 8, 1, 10, "Wheel company" );
        Inventory.addPart(Rim);
        InHouse powerplant = new InHouse(3,"Turbine Engine",65000.0,5,1,10,33);
        Inventory.addPart(powerplant);
        OutSourced Piston_engine = new OutSourced(4,"Rotax", 33000, 2,1,15,"Rotax");
        Inventory.addPart(Piston_engine);
        Product motorcycle = new Product(1, "Airbike", 15000.75, 1, 1, 5);
        Inventory.addProduct(motorcycle);
        Product airplane = new Product(2,"kitfox", 35000.76, 2, 1, 5);
        Inventory.addProduct(airplane);
        Product gokart = new Product(3,"ranger", 150.23, 6,1,10);
        Inventory.addProduct(gokart);
    }
}
