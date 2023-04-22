package devon.inventorymanager;
import devon.inventorymanager.model.*;

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
    public TextField partSearch;


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
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    public void onaddProduct(ActionEvent actionEvent) throws IOException {
        Parent add_product_parent = FXMLLoader.load(getClass().getResource("Add Product.fxml"));
        Scene add_product_scene = new Scene(add_product_parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(add_product_scene);
        app_stage.show();
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

}