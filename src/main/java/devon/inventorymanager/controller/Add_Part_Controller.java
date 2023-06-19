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
            System.out.println("Name Value is Blank");
            return;
        }

//convert values entered in text fields to the proper data type. If improper data type is enter in a text field catches the exception.
        int stock = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        String companyName = "";

        try {

            stock = Integer.parseInt(invS);

            price = Double.parseDouble(priceS);

            min = Integer.parseInt(minS);

            max = Integer.parseInt(maxS);
            //            number in stock cannot be less than minimum
            if(min > stock){
                System.out.println("inv must be >= min");
                return;
            }
            // number in stock cannot be more than maximum
            if(stock > max){
                System.out.println("inv must be <= max");
                return;
            }
            // price cannot be less than zero
            if(price < 0){
                System.out.println("price cannot be negative");
                return;
            }
        }
        catch (NumberFormatException e){
            // Handle number format exception
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
                //store the machine id as int
                int machine = Integer.parseInt(machineS);
                //add in house part to inventory
                InHouse inHousePart = new InHouse(id, nameS, price, stock, min, max, machine);
                Inventory.addPart(inHousePart);
            }






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
