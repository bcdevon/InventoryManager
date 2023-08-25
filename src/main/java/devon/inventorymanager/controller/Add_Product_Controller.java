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

    /**This is the initialize method.
     * this method is called during initialization and sets up default values of the ID text field
     * and populate the available parts table.
     * @param url The location of the Add Product.fxml
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

        //create a list of associated parts
        associatedPartsList = FXCollections.observableArrayList();

        //set the associated parts in the table view
        associatedParts.setItems(associatedPartsList);

        //Set up columns for associated parts table
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**This is the onRemoveAssociatedPart method.
     * This is and event handler method that is called when the user clicks remove associated part button.
     * Removes a selected part from the products associated parts list.
     * When a part is removed from a product the user must click ok to confirm.
     * @param actionEvent The event triggered by the action event*/
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        //get the selected part from the associated parts table
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

    /**This is the onAddProductCancel method.
     * This is an event handler method that is called if the user clicks the cancel button when on the add product screen.
     * The product will not be added and the application returns to the main screen.
     * @param actionEvent The event triggered when the cancel button is clicked.*/
    public void onAddProductCancel(ActionEvent actionEvent) throws IOException {
        //go back to the main screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
    }

    /**This is the onaddProductSave method.
     * This is an event handler method that is called when the user clicks the save button after adding a new product.
     * It retrieves values from the text fields converts them and checks for correct data types
     * and then adds them to the inventory it also adds associated parts to the product.
     * @param actionEvent The event triggered by clicking the save button.*/
    public void onaddProductSave(ActionEvent actionEvent) {
        //generate a product ID
        int productID = Product.generateNewProductId();

        //Get values from text fields
        String productName = productNameTF.getText();
        String productInventoryS = productInvTF.getText();
        String productPriceS = productPriceTF.getText();
        String productMaxS = productMaxTF.getText();
        String productMinS = productMinTF.getText();

        //check if the product name field is blank
        if(productName.isBlank()){
            //if the name field is blank display an alert error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Product");
            alert.showAndWait();
            System.out.println("Name value is blank");
            return;
        }

        //Initialize variables.
        double productPrice = 0;
        int productInventory = 0;
        int productMin = 0;
        int productMax = 0;
        String error = "";

        try{
            // convert the values entered into the text fields to the correct data type.
            // The error variable is used to keep track of which text field is currently being converted.
            // If there is an error when a text field is being converted the error is set to that field.
            error = "product Price";
            productPrice = Double.parseDouble(productPriceS);
            error = "product Inventory";
            productInventory = Integer.parseInt(productInventoryS);
            error = "product Min";
            productMin = Integer.parseInt(productMinS);
            error = "product Max";
            productMax = Integer.parseInt(productMaxS);

            // check if number in inventory is less than minimum allowed
            if(productMin > productInventory){
                //if inventory is below min display Error alert message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("Inventory must be >= Min");
                return;
            }
            // check if number in stock is more than maximum allowed
            if(productInventory > productMax){
                //display Error alert if inventory is more that max
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("Inventory must be <= max");
                return;
            }
            // check if price is less than zero
            if(productPrice < 0){
                //display Error alert if price is negative
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

        //Create a new product with the user entered information
        Product newProduct = new Product(productID, productName, productPrice, productInventory, productMin, productMax);

        //add the associated parts to the new product
        newProduct.getAssociatedParts().addAll(associatedPartsList);

        //add new product to inventory
        Inventory.addProduct(newProduct);

        try {
            //return to the main screen after saving
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

    /**This is the onAddPartToProduct method.
     * This method adds the selected parts to the associated parts list of the product
     * When the add button is clicked this method is called it adds the selected part from the available parts
     * table to the associated parts list of the product being created.
     * @param actionEvent The event triggered by clicking the add button.*/
    public void onAddPartToProduct(ActionEvent actionEvent) {
        //Get the selected part from the available parts
        Part selectedPart = (Part) availablePartsTable.getSelectionModel().getSelectedItem();

        //check if a part is selected
        if (selectedPart != null){
            //add the selected part to the list of associated parts for the product
            associatedPartsList.add(selectedPart);
        }
    }

    /**This is the searchByName method.
     * This method takes a partial name as input searches through all parts in the inventory.
     * If any part names contain the partial name they are added to a list of named parts
     * @param partialName The partial name to search for.
     * @return ObservableList of parts that contain the partial name being searched for.*/
    private ObservableList<Part> searchByName(String partialName){
        //Observable list to store parts that match search criteria
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        //Get a list of allParts from the inventory
        ObservableList<Part> allParts = Inventory.getAllParts();

        //Iterate through all parts to find matches for the partial name
        for(Part np: allParts){

            //check if the part name contains the partial name
            if(np.getName().contains(partialName)){

                //if a match is found add part to list of named parts
                namedParts.add(np);
            }
        }
        //return namedParts list
        return namedParts;
    }

    /**This is the getPartByID method.
     * This method is called when user searches for a part by its ID to add to a product.
     *This method takes an ID as input searches through the inventory to find the matching ID.
     * If any part matches the ID being searched for it is returned ID's are unique should only be one.
     * @param id The ID of the part searched for
     *@return The part with the matching ID*/
    private Part getPartByID(int id){
        //get the list of all parts
        ObservableList<Part> allParts = Inventory.getAllParts();

        //Search through all parts to find the one with matching ID
        for(int i = 0; i < allParts.size(); i++){
            Part idP = allParts.get(i);

            //check if ID matches ID being searched for
            if (idP.getId() == id){
                //if it matches return
                return idP;
            }
        }
        //if no match found return null
        return null;
    }

    /**This is the onSearchAddProduct method.
     *This method is called when a user performs a search for a part to add to a product.
     * It searches by ID or partial name and updates the available parts table with matches.
     * @param actionEvent The event triggered by the search action*/
    public void onSearchAddProduct(ActionEvent actionEvent) {
        //get the text from the search text field
        String addProductSearchTF = searchAddProduct.getText();

        //search for parts by partial name calls search by name method
        ObservableList<Part> parts = searchByName(addProductSearchTF);

        //update available parts table with parts that match
        availablePartsTable.setItems(parts);

        //if no matching parts are found search by ID
        if(parts.size() == 0){
            try {
                //covert search text to integer to check if it's a valid ID
                int id = Integer.parseInt(addProductSearchTF);

                //search for a part by the ID calls getPartByID method
                Part idP = getPartByID(id);

                //If a part with the searched ID is found add it to the search results
                if (idP != null)
                    parts.add(idP);
            }
            catch (NumberFormatException e){
                //ignore
            }
        }
        //update the available parts table with the search results
        availablePartsTable.setItems(parts);
    }
}
