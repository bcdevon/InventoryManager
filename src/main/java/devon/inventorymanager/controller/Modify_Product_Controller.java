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
import java.util.ResourceBundle;

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
    @FXML
    public void setProduct(Product product) {
        this.product = product;
        //check if the product has associated parts
        if (product != null){
            productAssociatedParts.setAll(product.getAssociatedParts());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productAvailableParts = Inventory.getAllParts();

        productAvailablePartsTable.setItems(productAvailableParts);
        productAvailablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productAvailablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAvailablePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productAvailablePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productAssociatedParts = FXCollections.observableArrayList();
        productAssociatedPartsTable.setItems(productAssociatedParts);
        productAssociatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productAssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productAssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    public void onAddModifyProduct(ActionEvent actionEvent) {
        Part selectedPart = (Part) productAvailablePartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            productAssociatedParts.add(selectedPart);
        }
    }

    public void onRemoveModifyProduct(ActionEvent actionEvent) {
        Part selectedPart = (Part )productAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            productAssociatedParts.remove(selectedPart);
        }
    }

    public void onSaveModifyProduct(ActionEvent actionEvent) {
        //disable productID modification
        productIDTF.setDisable(true);
        //get values from textfields
        int productId = Integer.parseInt(productIDTF.getText());
        String productName = productNameTF.getText();
        String productInventoryS = productInvTF.getText();
        String productPriceS = productPriceTF.getText();
        String productMaxS = productMaxTF.getText();
        String productMinS = productMinTF.getText();

        //if the name value is blank then the product will not be added
        if(productName.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Part");
            alert.showAndWait();
            System.out.println("Name Value is Blank");
            return;
        }

        // convert values to correct data type and catch exceptions if user entered wrong data type.
        int productInventory = 0;
        double productPrice = 0;
        int productMax = 0;
        int productMin = 0;
        String error = "";

        try{
            error = "productInventory";
            productInventory = Integer.parseInt(productInventoryS);
            productPrice = Double.parseDouble(productPriceS);
            productMax = Integer.parseInt(productMaxS);
            productMin = Integer.parseInt(productMinS);

            // number of product in inventory cannot be less than minimum
            if(productMin >productInventory){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("product inventory must be >= prodouctmin");
                return;
            }
            // number in stock cannot be more than maximum
            if(productInventory > productMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("productInventory must be <= productmax");
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

        //handle number format exception
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            alert.setContentText("Please enter a valid value for " + error + " field" );
            alert.showAndWait();
            System.out.println(error + " " + "wrong value entered!");
            return;
        }
        //save the new values from the textfields
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

    public void onCancelModifyProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
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

    public void onModifySearch(ActionEvent actionEvent) {
        String modifyProductSearchTF = searchModifyProduct.getText();
        ObservableList<Part> parts = searchByName(modifyProductSearchTF);
        productAvailablePartsTable.setItems(parts);

        if(parts.size() == 0){
            try {
                int id = Integer.parseInt(modifyProductSearchTF);
                Part idP = getPartByID(id);
                if (idP != null)
                    parts.add(idP);
            }
            catch (NumberFormatException e){
                //ignore
            }
        }
        productAvailablePartsTable.setItems(parts);

    }


}
