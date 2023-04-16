package devon.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Modfiy_Part_Controller implements Initializable {
    public ToggleGroup modifypart;
    public RadioButton modifyinHouse;
    public RadioButton modifyOutsourced;
    public Label modifypartID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onModifyInHouse(ActionEvent actionEvent) {
        modifypartID.setText("Machine ID");
    }

    public void onModifyOutsourced(ActionEvent actionEvent) {
        modifypartID.setText("Company Name");
    }
}
