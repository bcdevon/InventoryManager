package devon.inventorymanager.controller;
import devon.inventorymanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Modify_Product_Controller implements Initializable {
    public Button addModifyProduct;
    public Button removeModifyProduct;
    public Button saveModifyProduct;
    public Button cancelModifyProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAddModifyProduct(ActionEvent actionEvent) {
    }

    public void onRemoveModifyProduct(ActionEvent actionEvent) {
    }

    public void onSaveModifyProduct(ActionEvent actionEvent) {
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
