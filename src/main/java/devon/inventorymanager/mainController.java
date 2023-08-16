package devon.inventorymanager;
import devon.inventorymanager.controller.Modfiy_Part_Controller;
import devon.inventorymanager.controller.Modify_Product_Controller;
import devon.inventorymanager.model.*;
import devon.inventorymanager.model.Part;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the Main controller class for the application.*
 *This class handles buttons for adding, deleting, and modifying.
 *It also manages the display of parts and products in table views.
 */
public class mainController implements Initializable {
    public Button addPart;                  //Button to add new part
    public Button deletePart;               //Button to delete selected part
    public Button modifyPart;               //Button to modify a selected part
    public Button deleteProduct;            //Button to delete a selected product
    public Button modifyProduct;            //Button to modify a selected product
    public Button exit;                     //Button to exit the application
    public Button addProduct;               //Button to add a new product
    public TableColumn partIdCol;           //Column for displaying part IDs
    public TableColumn partNameCol;         //Column for displaying part names
    public TableColumn partInventoryCol;    //Column for displaying number of parts in inventory
    public TableColumn partPriceCol;        //Column for displaying part prices
    public TableColumn productIdCol;        //Column for displaying product IDs
    public TableColumn productNameCol;      //Column for displaying product names
    public TableColumn productInventoryCol; //Column for displaying number of products in inventory
    public TableColumn productPriceCol;     //Column for displaying product prices
    public TableView productsTable;         //TableView for displaying products
    public TableView partsTable;            //Tableview for displaying parts
    public TextField partSearch;            //Text field for searching parts
    public TextField productSearch;         //Text field for searching products

/**Initializes the main view and sets up the tables with data.
 * This method is called during initialization it prepares the main view and fills
 * the tables with data from the inventory.
 * It also sets up the columns in the tables to display the correct information.
 * @param url The location of Main.fxml.
 * @param resourceBundle resources used for initialization.*/

    @FXML
      @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set prompt for search fields
        partSearch.setPromptText("Search by ID or Name");
        productSearch.setPromptText("Search by ID or Name");

        //Populate parts table with all parts from the inventory
        partsTable.setItems(Inventory.getAllParts());

        //set the values for the parts table columns
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //populate products table with all products from the inventory
        productsTable.setItems(Inventory.getAllProducts());

        //set the values for the products table columns
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

/**When Add Part button is clicked the Add Part screen is loaded.
 * When someone clicks the Add Part button on the screen, this method get called.
 * It loads the Add Part screen in the same window replacing what was there.
 * @param actionEvent The event generated by clicking the add part button.
 * @throws IOException If and input/output error occurs during scene loading.*/
    public void onAddPartClicked(ActionEvent actionEvent) throws IOException {
        //Load the Add Part fxml and create a new scene
        Parent add_part_parent = FXMLLoader.load(getClass().getResource("Add Part.fxml"));
        Scene add_part_scene = new Scene(add_part_parent);

        //Get the current window and set the scene to the add part scene
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(add_part_scene);
        app_stage.show();

    }
    /**When the Delete Part button is clicked the selected part is deleted.
     * When someone clicks the Delete Part button this method is called.
     * This method checks if a part is selected in the parts table. If no part is selected
     * nothing happens. If a part is selected, it shows a confirmation popup asking
     * if you want to delete the part. If the user confirms, the selected part is deleted.
     * @param actionEvent The event generated by clicking the delete part button.*/
    public void ondeletePart(ActionEvent actionEvent) {

        //Get the selected part from the parts table
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        //If no part is selected, return and do nothing
        if (selectedPart == null){
            return;
        }
        //Display a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Do you want to delete this part");

        //Wait for response and check if they clicked ok
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            //Delete part from inventory if ok clicked
            Inventory.deletePart(selectedPart);
        }
    }

    /**Handles actions when the modify part button is clicked.
     * When modify part button is clicked and a part is selected the modify part screen is loaded.
     * The modify part screen is loaded and filled with the selected part data.
     * @param actionEvent the event generated by clicking the modify part button.
     * @throws IOException If an input/output error occurs during scene loading.*/
    public void onModifyPart(ActionEvent actionEvent) throws IOException {

        //Get the selected part from the parts table
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        //If no part is selected, return and do nothing
        if (selectedPart == null){
            return;
        }

        //Load the modify part screen and set the selected parts information
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modify Part.fxml"));
        Parent modifyPartParent = fxmlLoader.load();
        Modfiy_Part_Controller modifyPartController = fxmlLoader.getController();
        modifyPartController.setPart(selectedPart);

        //Get the current application stage with any parts modifications and refresh the displayed
        //content with the updated scene
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene modifyPartScene = new Scene(modifyPartParent);
        appStage.setScene(modifyPartScene);
        appStage.show();
    }

    /**When the delete product button is pressed the selected product is deleted.\
     * @param actionEvent The event generated by clicking the delete product button.
     * @throws IOException if an input/output error occurs during scene loading.*/
    public void onDeleteProduct(ActionEvent actionEvent) throws IOException {
        //Get the selected product from the products table
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        //If no product is selected return and do nothing
        if (selectedProduct == null) {
            return;
        }

        //Check if there are any associated parts with the selected product
        if (selectedProduct.getAssociatedParts().size() > 0){
            //Show and error alert if the product has associated parts
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Product Deletion Error");
            alert.setContentText("Cannot delete a product with associated parts.");
            alert.showAndWait();
            return;
        }

        //Confirm this is the correct product to be deleted
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Deleting Product");
        alert.setContentText("Are you sure you want to delete this Product?");

        //Wait for response and check if they clicked ok
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Delete the selected product from the inventory if ok is clicked.
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /**This method handles when the Modify product button is clicked to edit a product.
     * This method gets activated when someone clicks the modify product button.
     *The button click is represented by the action event parameter. When this method is called it
     *makes the app show a screen where you can change the details of a product.
     * @param actionEvent the event generated by clicking the modify product button.
     * @throws IOException if an input/output error occurs during scene loading.*/
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        //Get the currently selected product from the products table
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        //If no product is selected return and do nothing
        if (selectedProduct == null) {
            return;
        }

        //Load the modify product screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modify Product.fxml"));
        Parent modifyProductParent = fxmlLoader.load();

        //Set the selected product in the controller
        Modify_Product_Controller modifyProductController = fxmlLoader.getController();
        modifyProductController.setProduct(selectedProduct);

        //Set the text fields with the values from the selected product
        modifyProductController.productIDTF.setText(String.valueOf(selectedProduct.getId()));
        modifyProductController.productNameTF.setText(selectedProduct.getName());
        modifyProductController.productPriceTF.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductController.productInvTF.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductController.productMinTF.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductController.productMaxTF.setText(String.valueOf(selectedProduct.getMax()));

        //Get the current application stage and update the scene to the Modify Product screen
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene modifyProductScene = new Scene(modifyProductParent);
        appStage.setScene(modifyProductScene);
        appStage.show();
    }

    /**This is the Exit method.
     *This method is called when the exit button is clicked on the user interface.
     * It obtains the current application stage and closes it shutting down the application.
     * @param actionEvent The event generated by clicking the exit button.*/
    public void onExit(ActionEvent actionEvent) {
        //Get the current application stage and close it
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**This is the addProduct method.
     * This method is called when the Add Product button is clicked.
     * It loads the Add Product screen so the user can create a new product. */
    public void onaddProduct(ActionEvent actionEvent) throws IOException {
        //Load the Add Product fxml
        Parent add_product_parent = FXMLLoader.load(getClass().getResource("Add Product.fxml"));

        //create a scene with the loaded fxml
        Scene add_product_scene = new Scene(add_product_parent);

        //get the current application stage
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //set the scene to the add product scene
        app_stage.setScene(add_product_scene);

        //show the updated scene
        app_stage.show();
    }

    /**This is the search byByName method.
     *This method searches for a part by a name or partial name.
     * @param partialName the name to search for.
     * @return ObservableList of parts with that match the name or partial name being searched for. */
    //loop through parts table search for parts with names that match entry in the
    //search text field and display them in the parts tableview
    private ObservableList<Part> searchByName(String partialName){
        //a list to store the parts that match the search
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        //get a list of all the parts from the inventory
        ObservableList<Part> allParts = Inventory.getAllParts();

        //loop through each part in the allParts list
        for(Part np: allParts){
            //check if the current part name matches the partial name being searched for.
            if(np.getName().contains(partialName)){
                //if the name matches add the part to the list of matching parts
                namedParts.add(np);
            }
        }
        //return the list of parts that match the search criteria
        return namedParts;
    }
/**left of here 8/15/2023*/
    ///Search products in table by name return parts to table view that match.
    private ObservableList<Product> searchByProductName(String partialName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product nProduct: allProducts){
            if(nProduct.getName().contains(partialName)){
                namedProducts.add(nProduct);
            }
        }
        return namedProducts;
    }

    public void onPartsSearch(ActionEvent actionEvent) throws IOException{
        String partSearchTF = partSearch.getText();
        ObservableList<Part> parts = searchByName(partSearchTF);

        if(parts.size() == 0){
            try {
                int id = Integer.parseInt(partSearchTF);
                Part idP = getPartByID(id);
                if (idP != null)
                    parts.add(idP);
            }
            catch (NumberFormatException e){
                //ignore
            }

        }

        partsTable.setItems(parts);
    }

    ///search by part id return parts that match the ID to the parts table view///
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

    public void onProductSearch(ActionEvent actionEvent) throws IOException{
        String productSearchTF = productSearch.getText();
        ObservableList<Product> products = searchByProductName(productSearchTF);
        productsTable.setItems(products);

        if(products.size() == 0){
            try {
                int id = Integer.parseInt(productSearchTF);
                Product idProd = getProductByID(id);
                if(idProd != null)
                    products.add(idProd);
            }
            catch (NumberFormatException e){
                //ignore
            }
        }
        productsTable.setItems(products);

    }

    private Product getProductByID(int id){
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(int i = 0; i < allProducts.size(); i++){
            Product idProd = allProducts.get(i);
            if(idProd.getId() == id){
                return idProd;
            }
        }
        return null;
    }
}