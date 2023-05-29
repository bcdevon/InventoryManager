package devon.inventorymanager.controller;
import devon.inventorymanager.model.Inventory;
import devon.inventorymanager.Main;
import devon.inventorymanager.model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableParts = Inventory.getAllParts();
        availablePartsTable.setItems(availableParts);


        availablePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsList = FXCollections.observableArrayList();
        associatedParts.setItems(associatedPartsList);

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    

    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = associatedParts.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            associatedPartsList.remove(selectedPart);
        }
    }
    

    public void onAddProductCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
    }

    public void onaddProductSave(ActionEvent actionEvent) {
    }

    public void onAddPartToProduct(ActionEvent actionEvent) {
        Part selectedPart = (Part) availablePartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null){
            associatedPartsList.add(selectedPart);
        }
    }
}
