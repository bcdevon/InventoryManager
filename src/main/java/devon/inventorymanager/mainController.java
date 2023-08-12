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

/**The Main controller class for the application*/
public class mainController implements Initializable {
    public Button addPart;
    public Button deletePart;
    public Button modifyPart;
    public Button deleteProduct;
    public Button modifyProduct;
    public Button exit;
    public Button addProduct;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryCol;
    public TableColumn partPriceCol;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryCol;
    public TableColumn productPriceCol;
    public TableView productsTable;
    public TableView partsTable;
    public TextField partSearch;
    public TextField productSearch;

/**Initializes the main view and populates the table view.
 * @param url The location of the root object.
 * @param resourceBundle resources used for initialization.*/

    @FXML
      @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set prompt for search fields
        partSearch.setPromptText("Search by ID or Name");
        productSearch.setPromptText("Search by ID or Name");

        partsTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productsTable.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

/**When Add Part button is clicked the Add Part scene is loaded.
 * @param actionEvent The event generated by clicking the add part button.
 * @throws IOException If and input/output error occurs during scene loading.*/
    public void onAddPartClicked(ActionEvent actionEvent) throws IOException {
        Parent add_part_parent = FXMLLoader.load(getClass().getResource("Add Part.fxml"));
        Scene add_part_scene = new Scene(add_part_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(add_part_scene);
        app_stage.show();

    }
    public void ondeletePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Do you want to delete this part");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deletePart(selectedPart);
        }
    }

    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modify Part.fxml"));
        Parent modifyPartParent = fxmlLoader.load();
        Modfiy_Part_Controller modifyPartController = fxmlLoader.getController();
        modifyPartController.setPart(selectedPart);

        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene modifyPartScene = new Scene(modifyPartParent);
        appStage.setScene(modifyPartScene);
        appStage.show();
    }

    public void onDeleteProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }
        //check if there are any associated parts
        if (selectedProduct.getAssociatedParts().size() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Product Deletion Error");
            alert.setContentText("Cannot delete a product with associated parts.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Deleting Product");
        alert.setContentText("Are you sure you want to delete this Product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
    }

    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modify Product.fxml"));
        Parent modifyProductParent = fxmlLoader.load();

        Modify_Product_Controller modifyProductController = fxmlLoader.getController();
        modifyProductController.setProduct(selectedProduct); // Set the selected product in the controller

        // Set the text fields with the values from the selected product
        modifyProductController.productIDTF.setText(String.valueOf(selectedProduct.getId()));
        modifyProductController.productNameTF.setText(selectedProduct.getName());
        modifyProductController.productPriceTF.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductController.productInvTF.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductController.productMinTF.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductController.productMaxTF.setText(String.valueOf(selectedProduct.getMax()));

        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene modifyProductScene = new Scene(modifyProductParent);
        appStage.setScene(modifyProductScene);
        appStage.show();




    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    public void onaddProduct(ActionEvent actionEvent) throws IOException {
        Parent add_product_parent = FXMLLoader.load(getClass().getResource("Add Product.fxml"));
        Scene add_product_scene = new Scene(add_product_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(add_product_scene);
        app_stage.show();

        //Update availablePartsTable with all the parts



    }
    //loop through parts table search for parts with names that match entry in the
    //search text field and display them in the parts tableview
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