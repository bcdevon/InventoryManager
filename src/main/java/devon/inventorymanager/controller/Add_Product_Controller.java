package devon.inventorymanager.controller;

import devon.inventorymanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    

    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
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
    }
}
