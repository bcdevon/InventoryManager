package devon.inventorymanager.controller;

import devon.inventorymanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Add_Part_Controller implements Initializable {
    public RadioButton InHouse;
    public RadioButton OutSourced;
    public ToggleGroup Part;
    public Label MachineIDLabel;
    public Button partSave;
    public Button partCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onInHouse(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
    }

    public void onOutSourced(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");
    }

    public void onpartSave(ActionEvent actionEvent) {
    }

    public void onpartCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
    }
}
