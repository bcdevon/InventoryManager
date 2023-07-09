package devon.inventorymanager.controller;
import devon.inventorymanager.model.OutSourced;
import devon.inventorymanager.model.Part;
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
    public ToggleGroup ToggleGroupPart;
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
        //set default for part id text field
        partIDTF.setText("ID will be auto generated");
        //In-House selected by default
        ToggleGroupPart.selectToggle(InHouse);
    }

    public void onInHouse(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
    }

    public void onOutSourced(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");

    }


    public void onpartSave(ActionEvent actionEvent) throws IOException {
        // Generate a new ID
        int id = Part.generateNewId();
        //get input from each text field
        String nameS = partNameTF.getText();
        String invS = partInvTF.getText();
        String priceS =partPriceTF.getText();
        String maxS = partMaxTF.getText();
        String machineS = partMachineTF.getText();
        String minS = partMinTF.getText();


        // if the name value is blank then part will not be added.
        if(nameS.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Part");
            alert.showAndWait();
            System.out.println("Name Value is Blank");
            return;
        }

//convert values entered in text fields to the proper data type. If incorrect data type is enter in a text field catches the exception.
        int stock = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        String companyName = "";
        String error = "";

        try {
            error = "stock";
            stock = Integer.parseInt(invS);
            error = "price";
            price = Double.parseDouble(priceS);
            error = "min";
            min = Integer.parseInt(minS);
            error = "max";
            max = Integer.parseInt(maxS);
            //            number in stock cannot be less than minimum
            if(min > stock){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("inv must be >= min");
                return;
            }
            // number in stock cannot be more than maximum
            if(stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("inv must be <= max");
                return;
            }
            // price cannot be less than zero
            if(price < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Price cannot be less than zero");
                alert.showAndWait();
                System.out.println("price cannot be negative");
                return;
            }
        }
        // Handle number format exception
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            alert.setContentText("Please enter a valid value for " + error + " field" );
            alert.showAndWait();
            System.out.println(error + " " + "wrong value entered!");
            return;

        }

            // check if outsourced is selected
            if (ToggleGroupPart.getSelectedToggle() == OutSourced) {
                //store the company name as string
                companyName = machineS;
                //add outsourced part to the inventory
                OutSourced outSourcedPart = new OutSourced(id, nameS, price, stock, min, max, companyName);
                Inventory.addPart(outSourcedPart);
            }
            else {
                try {
                    //store the machine id as int
                    int machine = Integer.parseInt(machineS);
                    //add in house part to inventory
                    InHouse inHousePart = new InHouse(id, nameS, price, stock, min, max, machine);
                    Inventory.addPart(inHousePart);

                } catch(NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Please enter a valid machine ID.");
                    alert.showAndWait();
                    System.out.println("Invalid machine ID.");
                    return;
                }

            }
// go back to main screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
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
