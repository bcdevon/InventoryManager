package devon.inventorymanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** This is the Inventory class.
 * This class creates and manages an inventory of parts and products.*/
public class Inventory {
    //ObservableList to store all the Parts
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();

    //Observable list to store all the Products
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This is the addPart method.
     * this method adds a new part to the allParts list.
     * @param part The part to be added.*/
    public static void addPart(Part part){
        allParts.add(part);
    }

    /** This is the addProduct method.
     * this method add a new product to the allProducts list.
     * @param product The product to be added.*/
    public static void addProduct(Product product){ allProducts.add(product); }

    /** This is the deletePart Method.
     * this method deletes a part from the inventory.
     * @param part part to be deleted*/
    public static void deletePart(Part part){
        getAllParts().remove(part);
    }

    /** This is the deleteProduct method.
     * this method deletes a product from the inventory.
     * @param product product to be deleted*/
    public static void deleteProduct(Product product) {getAllProducts().remove(product);}

    /** This is the getAllParts method.
     * this method gets a list of all the parts in the inventory.
     * @return ObservableList of all parts*/
    public static ObservableList<Part> getAllParts() {return allParts;}

    /** This is the getAllProducts method.
     * This method gets a list of all the products in the inventory.
     * @return ObservableList of all products*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** This is the addTestData method.
     * This method creates parts and products and is used
     * to populate the inventory with sample data.*/
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
