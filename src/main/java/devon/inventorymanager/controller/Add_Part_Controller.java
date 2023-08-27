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

/** This is the Add_Part_Controller class.
 * This class handles InHouse and Outsourced parts, save or cancel when a part is added.
 * It also manages the values entered in the text fields for added parts and checks the
 * values in each field are the correct data type.*/
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

    /** This is the Initialize method.
     * This method is called during initialization it sets up default values.
     * @param url The location of the Add Part.fxml
     * @param resourceBundle  resources used for initialization*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set default value for ID text field
        partIDTF.setText("ID will be auto generated");

        //disable ID text field
        partIDTF.setDisable(true);

        //In-House selected by default
        ToggleGroupPart.selectToggle(InHouse);
    }

    /** This is the onInHouse method.
     * This is an event handler method that is called when the In House radio button is selected.
     * it Updates the label to prompt for a machine ID not a company name.
     * @param actionEvent trigger by selecting the In House radio button*/
    public void onInHouse(ActionEvent actionEvent) {
        MachineIDLabel.setText("Machine ID");
    }

    /** This is the onOutSourced method.
     * This is an event handler method that is called when the OutSourced radio button is selected.
     * It updates the label to prompt for a Company name not a machine ID
     * @param actionEvent The event triggered by selecting the OutSourced radio button*/
    public void onOutSourced(ActionEvent actionEvent) {
        MachineIDLabel.setText("Company Name");
    }

    /** LOGICAL ERROR. C:\Users\brady\OneDrive\Desktop\Software1\InventoryManager\src\main\java\devon\inventorymanager\controller\Add_Part_Controller.java:178:90
     java: incompatible types: java.lang.String cannot be converted to int.
     This error was fixed by parsing the machineID to an int. int machine = Integer.parseInt(machineS);
     * This is the onPartSave method.
     * This is an event handler method that is called when the save button is clicked.
     * This method handles user inputs, validates data, and adds the new part to the inventory.
     * @param actionEvent The event triggered by clicking the save button */
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


        // check if the part name is blank
        if(nameS.isBlank()){

            //If the part name is blank show an error alert stating to enter a part name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Part");
            alert.showAndWait();
            System.out.println("Name Value is Blank");

            //return without adding the part
            return;
        }

        //Initialize variables for part
        int stock = 0;
        double price = 0;
        int min = 0;
        int max = 0;
        String companyName = "";
        String error = "";

        try {
            // convert the values entered into the text fields to the correct data type.
            // The error variable is used to keep track of which text field is currently being converted.
            // If there is an error when a text field is being converted the error is set to that field.
            error = "stock";
            stock = Integer.parseInt(invS);
            error = "price";
            price = Double.parseDouble(priceS);
            error = "min";
            min = Integer.parseInt(minS);
            error = "max";
            max = Integer.parseInt(maxS);

            // check if the number of parts in inventory is less than the minimum allowed.
            if(min > stock){
                //Error alert if inventory is less than minimum
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("inv must be >= min");
                return;
            }

            // check if number of parts in inventory is greater than maximum allowed.
            if(stock > max){
                //Error alert if inventory is greater than maximum
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("inv must be <= max");
                return;
            }
            // Check if price is negative
            if(price < 0){
                //Error alert if price of part is less than zero
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Price cannot be less than zero");
                alert.showAndWait();
                System.out.println("price cannot be negative");
                return;
            }
        }
        // Handle any conversion errors by displaying an appropriate error message.
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error dialog");
            //If there is an error when text fields are being converted the name will be shown here.
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
                    //handle invalid machine ID error display error message
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

    /** This is the onpartCancel method.
     * this is an event handler method that is called when the cancel button is clicked
     * The part will not be added and the application goes back to the main screen view
     * @param actionEvent The event triggered by clicking the cancel button*/
    public void onpartCancel(ActionEvent actionEvent) throws IOException {
        //return to the main screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();
    }
}
