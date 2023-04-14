package devon.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Add_Part_Controller implements Initializable {
    public RadioButton InHouse;
    public RadioButton OutSourced;
    public ToggleGroup Part;
    public Label MachineIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onInHouse(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
    }

    public void onOutSourced(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");
    }
}
