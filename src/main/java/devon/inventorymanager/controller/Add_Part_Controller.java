package devon.inventorymanager.controller;

import devon.inventorymanager.Main;
import devon.inventorymanager.model.InHouse;
import devon.inventorymanager.model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public TextField partIDTF;
    public TextField partNameTF;
    public TextField partInvTF;
    public TextField partPriceTF;
    public TextField partMaxTF;
    public TextField partMachineTF;
    public TextField partMinTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onInHouse(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
    }

    public void onOutSourced(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");
    }


    public void onpartSave(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();

        String idS = partIDTF.getText();
        String nameS = partNameTF.getText();
        String invS = partInvTF.getText();
        String priceS =partPriceTF.getText();
        String maxS = partMaxTF.getText();
        String machineS = partMachineTF.getText();
        String minS = partMinTF.getText();
        //System.out.println(idS + " " + nameS + " " + invS + " " + priceS + " " + maxS + " " + machineS + " " + minS + " ");
        // if the name value is blank then prt will not be added.
        if(nameS.isBlank()){
            System.out.println("Name Value is Blank");
            return;
        }
//convert values entered in text fields to the proper data type. If improper data type is enter in a text field catches the exception.
        int id = 0;
        try {
            id = Integer.parseInt(idS);
        }
        catch (NumberFormatException e){
            System.out.println("ID value must be a number!");
            return;
        }
        int stock = Integer.parseInt(invS);
        double price = Double.parseDouble(priceS);
        int min = Integer.parseInt(minS);
        int max = Integer.parseInt(maxS);
        int machine = Integer.parseInt(machineS);

        InHouse widget = new InHouse(id, nameS, price, stock, min, max, machine);
        Inventory.addPart(widget);
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
