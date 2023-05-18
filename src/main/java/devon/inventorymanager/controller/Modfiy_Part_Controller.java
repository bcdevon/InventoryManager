package devon.inventorymanager.controller;

import devon.inventorymanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Modfiy_Part_Controller implements Initializable {
    public ToggleGroup modifypart;
    public RadioButton modifyinHouse;
    public RadioButton modifyOutsourced;
    public Label modifypartID;
    public Button modifyPartCancel;
    public TextField modPartIDTF;
    public TextField modPartNameTF;
    public TextField modPartInvTF;
    public TextField modPartPriceTF;
    public TextField modPartMaxTF;
    public TextField modMachineTF;
    public TextField modPartMinTF;
    public Button modPartSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onModifyInHouse(ActionEvent actionEvent) {
        modifypartID.setText("Machine ID");
    }

    public void onModifyOutsourced(ActionEvent actionEvent) {
        modifypartID.setText("Company Name");
    }

    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();

    }

    public void onModPartSave(ActionEvent actionEvent) throws IOException {
        String modidS = modPartIDTF.getText();
        String modnameS = modPartNameTF.getText();
        String modinvS = modPartInvTF.getText();
        String modpriceS = modPartPriceTF.getText();
        String modmaxS = modPartMaxTF.getText();
        String modmachineS = modMachineTF.getText();
        String modminS = modPartMinTF.getText();

        int id = 0;
        int stock = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        int machine = 0;
        String error = "";
        try {
            error = "id";
            id = Integer.parseInt(modidS);
            error = "stock";
            stock = Integer.parseInt(modinvS);
            error = "price";
            price = Double.parseDouble(modpriceS);
            error = "min";
            min = Integer.parseInt(modminS);
            error = "max";
            max = Integer.parseInt(modmaxS);
            error = "machine ID";
            machine = Integer.parseInt(modmachineS);
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            alert.setContentText("Please enter a valid value for each value");
            alert.showAndWait();
            System.out.println(error + " " + "value must be a number!");
            return;
        }
    }

}
