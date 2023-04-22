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
        int stock = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        int machine = 0;
        String error = "";
        try {
            error = "id";
            id = Integer.parseInt(idS);
            error = "stock";
            stock = Integer.parseInt(invS);
            error = "price";
            price = Double.parseDouble(priceS);
            error = "min";
            min = Integer.parseInt(minS);
            error = "max";
            max = Integer.parseInt(maxS);
            error = "machine ID";
            machine = Integer.parseInt(machineS);

//            min <= stock stock <= max
            if(min > stock){
                System.out.println("inv must be >= min");
                return;
            }
            if(stock > max){
                System.out.println("inv must be <= max");
                return;
                }
            if(price < 0){
                System.out.println("price cannot be negative");
                return;
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
            System.out.println(error + " " + "value must be a number!");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();


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
