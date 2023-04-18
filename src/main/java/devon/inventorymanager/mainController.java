package devon.inventorymanager;
import devon.inventorymanager.model.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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



    @FXML
      @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void onAddPartClicked(ActionEvent actionEvent) throws IOException {
        Parent add_part_parent = FXMLLoader.load(getClass().getResource("Add Part.fxml"));
        Scene add_part_scene = new Scene(add_part_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(add_part_scene);
        app_stage.show();

    }
    public void ondeletePart(ActionEvent actionEvent) {
    }

    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Parent modify_part_parent = FXMLLoader.load(getClass().getResource("Modify Part.fxml"));
        Scene modify_part_scene = new Scene(modify_part_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(modify_part_scene);
        app_stage.show();
    }

    public void onDeleteProduct(ActionEvent actionEvent) throws IOException {
    }

    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Parent modify_product_parent = FXMLLoader.load(getClass().getResource("Modify Product.fxml"));
        Scene modify_product_scene = new Scene(modify_product_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(modify_product_scene);
        app_stage.show();
    }

    public void onExit(ActionEvent actionEvent) {
    }

    public void onaddProduct(ActionEvent actionEvent) throws IOException {
        Parent add_product_parent = FXMLLoader.load(getClass().getResource("Add Product.fxml"));
        Scene add_product_scene = new Scene(add_product_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(add_product_scene);
        app_stage.show();
    }

}