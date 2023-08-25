package devon.inventorymanager.controller;
import devon.inventorymanager.model.Inventory;
import devon.inventorymanager.Main;
import devon.inventorymanager.model.InHouse;
import devon.inventorymanager.model.OutSourced;
import devon.inventorymanager.model.Part;
import javafx.collections.ObservableList;
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

/**This is the Modify_Part_Controller class.
 * This class handles modifying parts details,
 * The user is able to modify name, inventory, price, min, max, machineID, company name*/
public class Modfiy_Part_Controller implements Initializable {
    public TextField modPartIDTF;
    public TextField modPartNameTF;
    public TextField modPartInvTF;
    public TextField modPartPriceTF;
    public TextField modPartMaxTF;
    public TextField modMachineTF;
    public TextField modPartMinTF;
    public Button modifyPartSave;
    private Part part;

    /**This is the setPart method.
     * This method populates the screen with the existing part information
     * @param part The part to be modified*/
    public void setPart(Part part) {
        //get the existing part details and set them in the modify part screen
        modPartIDTF.setText(String.valueOf((part.getId())));
        modPartNameTF.setText(part.getName());
        modPartInvTF.setText(String.valueOf(part.getStock()));
        modPartPriceTF.setText(String.valueOf(part.getPrice()));
        modPartMaxTF.setText(String.valueOf(part.getMax()));
        modPartMinTF.setText(String.valueOf(part.getMin()));

        //check if the part is InHouse or Outsourced
        if (part instanceof InHouse) {
            //if the part is InHouse set the correct radio button, label, and machine ID
            modifypart.selectToggle(modifyinHouse);
            modifypartID.setText("Machine ID");
            modMachineTF.setText(String.valueOf(((InHouse) part).getMachineID()));

          //if OutSourced part select the correct radio button, label, and company name
        } else if (part instanceof OutSourced) {
            modifypart.selectToggle(modifyOutsourced);
            modifypartID.setText("Company Name");
            modMachineTF.setText(((OutSourced) part).getCompanyName());

        }
    }

    public ToggleGroup modifypart;
    public RadioButton modifyinHouse;
    public RadioButton modifyOutsourced;
    public Label modifypartID;
    public Button modifyPartCancel;

    public Button modPartSave;

    /**This is the initialize method.
     *This method is called during initialization and sets up the initial state of a part.
     * The ID field is disabled and the radio buttons are set.
     * @param url The location of the Modify Product.fxml
     * @param resourceBundle The resource bundle used for initialization*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //disable id so it cannot be changed
        modPartIDTF.setDisable(true);

    }

    /**This is the onModifyInHouse method.
     * This method is called when the InHouse toggle button is selected.
     * When the InHouse radio button is selected the label is changed to machine ID
     * and a machine ID must be entered not a company name.
     * @param actionEvent The event triggered by selecting the InHouse radio button*/
    public void onModifyInHouse(ActionEvent actionEvent) {
        modifypartID.setText("Machine ID");
    }

    /**This is the onModifyOutsourced method.
     * This method is called when the Outsourced radio button is selected.
     * When the Outsourced radio button is selected the label is changed
     * to Company Name and a company name must be entered not a machine ID */
    public void onModifyOutsourced(ActionEvent actionEvent) {
        modifypartID.setText("Company Name");
    }

    /**This is the onModifyPartCancel method.
     * This method is called whe the user clicks the cancel button on the modify part screen.
     * when cancel is clicked changes are not saved the application goes back to the main screen.
     * @param actionEvent The event triggered by pressing the cancel button*/
    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("tables");
        stage.setScene(scene);
        stage.show();

    }

    /**This is the onSave method.
     * This method is called when the save button is clicked.
     * The modified part information is saved and updated in the inventory.
     * @param actionEvent The event triggered by clicking the save button.*/
    public void onSave(ActionEvent actionEvent) throws IOException{
        //disable Part ID modification
        modPartIDTF.setDisable(true);

        //get values from text fields
        int partID = Integer.parseInt(modPartIDTF.getText());
        String partName = modPartNameTF.getText();
        String partStockS = modPartInvTF.getText();
        String partPriceS = modPartPriceTF.getText();
        String partMaxS = modPartMaxTF.getText();
        String partMinS = modPartMinTF.getText();
        String machineS = modMachineTF.getText();


// if the name value is blank then the part will not be added.
        if(partName.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a name for the Part");
            alert.showAndWait();
            System.out.println("Name Value is Blank");
            return;
        }

        // Initialize variables
        // convert values to correct data type and catch exceptions if user entered wrong data type.
        int partStock = 0;
        double partPrice = 0;
        int partMin = 0;
        int partMax = 0;
        String companyName = "";
        String error = "";

        try {
            // convert the values entered into the text fields to the correct data type.
            // The error variable is used to keep track of which text field is currently being converted.
            // If there is an error when a text field is being converted the error is set to that field.
            error = "partStock";
            partStock = Integer.parseInt(partStockS);
            error = "partPrice";
            partPrice = Double.parseDouble(partPriceS);
            error = "partMin";
            partMin = Integer.parseInt(partMinS);
            error = "partMax";
            partMax = Integer.parseInt(partMaxS);

            //// check if number in inventory is less than minimum allowed
            if(partMin > partStock){
                //if inventory is below min display Error alert message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("inv must be >= min");
                return;
            }

            // check if number in stock is more than maximum allowed
            if(partStock > partMax){
                //display Error alert if inventory is more that max
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("inv must be <= max");
                return;
            }
            // check if price is less than zero
            if(partPrice < 0){
                //display Error alert if price is negative
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
        if (modifypart.getSelectedToggle() == modifyOutsourced){
            //store company name as a String
            companyName = machineS;
            //update the modified outsourced part
            OutSourced modifiedPart = new OutSourced(partID, partName, partPrice, partStock, partMin, partMax, companyName);
            updatePartInInventory(modifiedPart);
        }
        else{
            try{
                //store the machine ID as an int
                int machineID = Integer.parseInt(machineS);

                //Update the modified InHouse part
                InHouse modifiedPart = new InHouse(partID, partName, partPrice, partStock, partMin, partMax, machineID);
                updatePartInInventory(modifiedPart);

                //handle number format exception machine ID should be an int.
            } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid machine ID.");
                alert.showAndWait();
                System.out.println("Invalid machine ID.");
                return;
            }
        }
        // Go back to the main screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1200, 700);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**This is the updatePartInventory method.
     * This method searches for the matching part ID to the part being modified and then updates it
     * with the modifications that have been made.
     * @param modifiedPart The modified part to update in the inventory.*/
    private void updatePartInInventory(Part modifiedPart) {
        //Get the list of all parts from the inventory
        ObservableList<Part> allParts = Inventory.getAllParts();

        //search through all parts to find the one with the matching ID to the part being modified
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            //check if the part ID matches the modified part
            if (part.getId() == modifiedPart.getId()) {

                //update the part in the inventory
                allParts.set(i, modifiedPart);
                break;
            }
            }

    }

}
