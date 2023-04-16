package devon.inventorymanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML
      @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void onDeleteProduct(ActionEvent actionEvent) {
    }

    public void onModifyProduct(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {
    }

    public void onaddProduct(ActionEvent actionEvent) {
    }
}