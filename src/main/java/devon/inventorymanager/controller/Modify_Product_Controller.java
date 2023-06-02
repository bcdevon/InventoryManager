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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

        //get values from textfields
        int productId = Integer.parseInt(productIDTF.getText());
        String productName = productNameTF.getText();
        int productInventory = Integer.parseInt(productInvTF.getText());
        double productPrice = Double.parseDouble(productPriceTF.getText());
        int productMax = Integer.parseInt(productMaxTF.getText());
        int productMin = Integer.parseInt(productMinTF.getText());

        //save the new values from the textfields
        product.setId(productId);
        product.setName(productName);
        product.setStock(productInventory);
        product.setPrice(productPrice);
        product.setMax(productMax);
        product.setMin(productMin);
        //add associated parts to product
        product.getAssociatedParts().setAll(productAssociatedParts);

        //save the associated parts to the product


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
}
