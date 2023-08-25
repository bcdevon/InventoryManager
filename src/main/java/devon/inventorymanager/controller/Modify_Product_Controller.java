package devon.inventorymanager.controller;
import devon.inventorymanager.Main;
import devon.inventorymanager.model.Inventory;
import devon.inventorymanager.model.Part;
import devon.inventorymanager.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/**This is the Modify_Product_Controller class.
 * This class handles modifying product details.
 * The user is able to modify name, inventory, price, min, max, and associated parts*/
public class Modify_Product_Controller implements Initializable {
    public Button addModifyProduct;
    public Button removeModifyProduct;
    public Button saveModifyProduct;
    public Button cancelModifyProduct;
    public TextField productIDTF;
    public TextField productNameTF;
    public TextField productInvTF;
    public TextField productPriceTF;
    public TextField productMaxTF;
    public TextField productMinTF;
    public TableView productAvailablePartsTable;
    public TableColumn productAvailablePartIDCol;
    public TableColumn productAvailablePartNameCol;
    public TableColumn productAvailablePartInvCol;
    public TableColumn productAvailablePartPriceCol;
    public TableView productAssociatedPartsTable;
    public TableColumn productAssociatedPartIDCol;
    public TableColumn productAssociatedPartNameCol;
    public TableColumn productAssociatedPartInvCol;
    public TableColumn productAssociatedPartPriceCol;
    public ObservableList<Part> productAvailableParts;
    public ObservableList<Part> productAssociatedParts;
    public TextField searchModifyProduct;
    private Product product;

    /**This is the setProduct Method.
     * This method populates the screen with the existing product information.
     * @param product The product to be modified.*/
    @FXML
    public void setProduct(Product product) {
        this.product = product;

        //check if the product has associated parts and update the associated parts list
        if (product != null){
            productAssociatedParts.setAll(product.getAssociatedParts());
        }
    }

    /**This is the initialize method.
     * This method is called during initialization and sets up the initial state of the product.
     * The ID field is disabled and search bar is set with a default message.
     * @param url The location of the modify Product.xml
     * @param resourceBundle The resource bundle used for initialization*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //disable id so it cannot be changed
        productIDTF.setDisable(true);
        //default message in search bar
        searchModifyProduct.setPromptText("Search by Name or ID");

        //get all parts from the inventory
        productAvailableParts = Inventory.getAllParts();

        //Set the available parts.
        productAvailablePartsTable.setItems(productAvailableParts);

        //Set up columns for available parts table
        productAvailablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productAvailablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAvailablePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productAvailablePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //create a list for associated parts
        productAssociatedParts = FXCollections.observableArrayList();

        //set the associated parts in the table view
        productAssociatedPartsTable.setItems(productAssociatedParts);

        //set up columns for associated parts
        productAssociatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productAssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productAssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**This is the onAddModifyProduct method.
     *This method is called when the add button is clicked.
     * It adds the selected part from the available parts and adds it to
     * the list of associated parts for the product being modified.
     * @param actionEvent The event triggered by clicking the add button.*/
    public void onAddModifyProduct(ActionEvent actionEvent) {
        //Get the selected part from the available parts
        Part selectedPart = (Part) productAvailablePartsTable.getSelectionModel().getSelectedItem();

        //check if a part is selected
        if (selectedPart != null){
            //add the selected part to the list of associated parts for the product
            productAssociatedParts.add(selectedPart);
        }
    }

    /**This is the onRemoveModifyProduct method.
     * This is an event handler method that is called when the user clicks remove associated part.
     * This method removes the selected part from the products associated parts list*/
    public void onRemoveModifyProduct(ActionEvent actionEvent) {
        //get the selected part from the associated parts
        Part selectedPart = (Part )productAssociatedPartsTable.getSelectionModel().getSelectedItem();

        //check if a part is selected
        if(selectedPart != null) {

            //confirm the user wants to remove the part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Associated Part");
            alert.setContentText("Do you want to remove this part from the product");

            // if confirmed remove the associate part
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                productAssociatedParts.remove(selectedPart);
            }
        }
    }

    /**This is the onSaveModifyProduct method.
     * This is an event handler method that is called when the user clicks the save button after modifying a product.
     * It gets values from the text fields converts them and checks for correct data type adds or removes associated parts
     * then updates the inventory.
     * @param actionEvent The event triggered by clicking the save button*/
    public void onSaveModifyProduct(ActionEvent actionEvent) {
        //disable productID modification
        productIDTF.setDisable(true);

        //get values from text fields
        int productId = Integer.parseInt(productIDTF.getText());
        String productName = productNameTF.getText();
        String productInventoryS = productInvTF.getText();
        String productPriceS = productPriceTF.getText();
        String productMaxS = productMaxTF.getText();
        String productMinS = productMinTF.getText();

        //check if the product name is blank.
        if(productName.isBlank()){
            //if the name is blank display an alert error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Part");
            alert.showAndWait();
            System.out.println("Name Value is Blank");
            return;
        }

        //Initialize variables
        int productInventory = 0;
        double productPrice = 0;
        int productMax = 0;
        int productMin = 0;
        String error = "";

        try{
            // convert the values entered into the text fields to the correct data type.
            // The error variable is used to keep track of which text field is currently being converted.
            // If there is an error when a text field is being converted the error is set to that field
            error = "product Inventory";
            productInventory = Integer.parseInt(productInventoryS);
            error = "product Price";
            productPrice = Double.parseDouble(productPriceS);
            error = "product max";
            productMax = Integer.parseInt(productMaxS);
            error = "product min";
            productMin = Integer.parseInt(productMinS);

            // check if number in inventory is less than minimum allowed
            if(productMin >productInventory){
                //if inventory is below min display error alert message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("product inventory must be >= prodouctmin");
                return;
            }
            // check if number in stock is more than maximum allowed
            if(productInventory > productMax){
                //display Error alert if inventory is more that max
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("productInventory must be <= productmax");
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

        //handle number format exception
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            alert.setContentText("Please enter a valid value for " + error + " field" );
            alert.showAndWait();
            System.out.println(error + " " + "wrong value entered!");
            return;
        }

        //save the new values from the text fields
        product.setId(productId);
        product.setName(productName);
        product.setStock(productInventory);
        product.setPrice(productPrice);
        product.setMax(productMax);
        product.setMin(productMin);

        //add associated parts to product
        product.getAssociatedParts().setAll(productAssociatedParts);

        //return to main screen
        try{
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

   /**This is the onCancelModifyProduct method.
    * This is an event handler method that is called when the cancel button is clicked.
    * The changes to the product will not be saved and the application returns to the main screen.
    * @param actionEvent The event triggered when the cancel button is clicked*/
    public void onCancelModifyProduct(ActionEvent actionEvent) throws IOException {
        //go back to the main screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
    }

    /**This is the searchByName method.
     * This method takes a partial name as input searches through all parts in the inventory.
     * If any part names contain the partial name they are added to a list of named parts
     * @param partialName The partial name to search for.
     * @return ObservableList of parts that contain the partial name being searched for.*/
    private ObservableList<Part> searchByName(String partialName){
        //Observable list to store parts that match the search criteria
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        //Get a list of all parts from the inventory
        ObservableList<Part> allParts = Inventory.getAllParts();

        //Iterate through all parts to find matches for the partial name
        for(Part np: allParts){

            //check if the part name contains the partial name
            if(np.getName().contains(partialName)){

                //if a match is found add part to list of named parts
                namedParts.add(np);
            }
        }
        //return the named parts list
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

        //search through all parts to find the one with the matching ID
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

    /**This is the onModifySearch method.
     *This method is called when a user performs a search for a part to add to a product.
     * It searches by ID or partial name and updates the available parts table with matches.
     * @param actionEvent The event triggered by the search action*/
    public void onModifySearch(ActionEvent actionEvent) {
        //get the text from the search text field
        String modifyProductSearchTF = searchModifyProduct.getText();

        //search for parts by partial name call the searchByName method
        ObservableList<Part> parts = searchByName(modifyProductSearchTF);

        //update available parts table with parts that match
        productAvailablePartsTable.setItems(parts);

        //if no matching parts are found search by ID
        if(parts.size() == 0){
            try {

                //convert search text to integer to check if it's a valid ID
                int id = Integer.parseInt(modifyProductSearchTF);

                //search for a part by ID call getPartByID method
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
        productAvailablePartsTable.setItems(parts);

    }


}
