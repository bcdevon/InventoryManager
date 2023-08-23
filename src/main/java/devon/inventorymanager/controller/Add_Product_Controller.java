package devon.inventorymanager.controller;
import devon.inventorymanager.model.Inventory;
import devon.inventorymanager.Main;
import devon.inventorymanager.model.Part;
import devon.inventorymanager.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the Add_Product_Controller class
 * This class handles adding products checking for correct data types.
 * It handles saving or canceling when adding products as well as associating parts with products.*/
public class Add_Product_Controller implements Initializable {
    public Button removeAssociatedPart;
    public Button addProductSave;
    public Button addProductCancel;
    public Button addPartToProduct;
    public TextField productIDTF;
    public TextField productNameTF;
    public TextField productInvTF;
    public TextField productPriceTF;
    public TextField productMaxTF;
    public TextField productMinTF;
    public TableView availablePartsTable;
    public TableColumn availablePartIDCol;
    public TableColumn availablePartNameCol;
    public TableColumn availablePartInventoryCol;
    public TableColumn availablePartPriceCol;
    public TableView<Part> associatedParts;
    public TableColumn associatedPartIDCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedPartInventoryCol;
    public TableColumn associatedPartPriceCol;
    public ObservableList<Part> availableParts;
    public ObservableList<Part> associatedPartsList;
    public TextField searchAddProduct;

    /**This is the initialize method
     * this method is called during initialization and sets up default values of the ID text field
     * and populate the available parts table.
     * @param url The locations of the Add Product.fxml
     * @param resourceBundle The resource bundle used for initialization*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchAddProduct.setPromptText("Search by Name or ID");
        //set default for product id text field
        productIDTF.setText("ID will be auto generated");
        productIDTF.setDisable(true);

        //populate available parts table
        availableParts = Inventory.getAllParts();
        availablePartsTable.setItems(availableParts);

        //Set up columns for available parts table
        availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Initialize the associated parts list and table
        associatedPartsList = FXCollections.observableArrayList();
        associatedParts.setItems(associatedPartsList);

        //Set up columns for associated parts table
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**This is the onRemoveAssociatedPart method.
     * this is and event handler method.
     * this method is called when the user clicks remove associated part button.
     * When a part is removed from a product the user must click ok to confirm.
     * @param actionEvent The event triggered by the action event*/
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        //check the selected part from the associated parts table
        Part selectedPart = associatedParts.getSelectionModel().getSelectedItem();

        //check if a part is selected
        if(selectedPart != null) {

            //confirm if the user wants to remove the part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Associated Part");
            alert.setContentText("Do you want to remove this part from the product");

            //if confirmed remove the associated part
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                associatedPartsList.remove(selectedPart);
            }

        }

    }

    /**left off here 8/22/2023*/
    public void onAddProductCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
    }

    public void onaddProductSave(ActionEvent actionEvent) {
        int productID = Product.generateNewProductId();

        //Get values from textfields
        String productName = productNameTF.getText();
        String productInventoryS = productInvTF.getText();
        String productPriceS = productPriceTF.getText();
        String productMaxS = productMaxTF.getText();
        String productMinS = productMinTF.getText();

        //if the product name field is blank
        if(productName.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Product");
            alert.showAndWait();
            System.out.println("Name value is blank");
            return;
        }

        //convert values to correct data types. check for incorrect entries by user.
        double productPrice = 0;
        int productInventory = 0;
        int productMin = 0;
        int productMax = 0;
        String error = "";

        try{
            error = "product Price";
            productPrice = Double.parseDouble(productPriceS);
            error = "product Inventory";
            productInventory = Integer.parseInt(productInventoryS);
            error = "product Min";
            productMin = Integer.parseInt(productMinS);
            error = "product Max";
            productMax = Integer.parseInt(productMaxS);

            //            number in stock cannot be less than minimum
            if(productMin > productInventory){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("Inventory must be >= Min");
                return;
            }
            // number in stock cannot be more than maximum
            if(productInventory > productMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("Inventory must be <= max");
                return;
            }
            // price cannot be less than zero
            if(productPrice < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Price cannot be less than zero");
                alert.showAndWait();
                System.out.println("price cannot be negative");
                return;
            }
        }
        // Handle number format exception
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            alert.setContentText("Please enter a valid value for " + error + " field" );
            alert.showAndWait();
            System.out.println(error + " " + "wrong value entered!");
            return;

        }

        Product newProduct = new Product(productID, productName, productPrice, productInventory, productMin, productMax);

        //add the associated parts to the new product
        newProduct.getAssociatedParts().addAll(associatedPartsList);

        //add new product to inventory
        Inventory.addProduct(newProduct);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("tables");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void onAddPartToProduct(ActionEvent actionEvent) {
        Part selectedPart = (Part) availablePartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            associatedPartsList.add(selectedPart);
        }
    }
    private ObservableList<Part> searchByName(String partialName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part np: allParts){
            if(np.getName().contains(partialName)){
                namedParts.add(np);
            }
        }
        return namedParts;
    }
    private Part getPartByID(int id){
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(int i = 0; i < allParts.size(); i++){
            Part idP = allParts.get(i);
            if (idP.getId() == id){
                return idP;
            }
        }

        return null;
    }
    public void onSearchAddProduct(ActionEvent actionEvent) {
        String addProductSearchTF = searchAddProduct.getText();
        ObservableList<Part> parts = searchByName(addProductSearchTF);
        availablePartsTable.setItems(parts);

        if(parts.size() == 0){
            try {
                int id = Integer.parseInt(addProductSearchTF);
                Part idP = getPartByID(id);
                if (idP != null)
                    parts.add(idP);
            }
            catch (NumberFormatException e){
                //ignore
            }
        }
        availablePartsTable.setItems(parts);
    }
}
