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

    public void setPart(Part part) {
        modPartIDTF.setText(String.valueOf((part.getId())));
        modPartNameTF.setText(part.getName());
        modPartInvTF.setText(String.valueOf(part.getStock()));
        modPartPriceTF.setText(String.valueOf(part.getPrice()));
        modPartMaxTF.setText(String.valueOf(part.getMax()));
        modPartMinTF.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            modifypart.selectToggle(modifyinHouse);
            modifypartID.setText("Machine ID");
            modMachineTF.setText(String.valueOf(((InHouse) part).getMachineID()));
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


    public void onSave(ActionEvent actionEvent) throws IOException{
        //disable Part ID modification
        modPartIDTF.setDisable(true);
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

        // convert values to correct data type and catch exceptions if user entered wrong data type.
        int partStock = 0;
        double partPrice = 0;
        int partMin = 0;
        int partMax = 0;
        String companyName = "";
        String error = "";

        try {
            error = "partStock";
            partStock = Integer.parseInt(partStockS);
            error = "partPrice";
            partPrice = Double.parseDouble(partPriceS);
            error = "partMin";
            partMin = Integer.parseInt(partMinS);
            error = "partMax";
            partMax = Integer.parseInt(partMaxS);

            //            number in stock cannot be less than minimum
            if(partMin > partStock){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be less than min.");
                alert.showAndWait();
                System.out.println("inv must be >= min");
                return;
            }

            // number in stock cannot be more than maximum
            if(partStock > partMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Number in inventory cannot be more than max.");
                alert.showAndWait();
                System.out.println("inv must be <= max");
                return;
            }
            // price cannot be less than zero
            if(partPrice < 0){
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
            //update outsourced part
            OutSourced modifiedPart = new OutSourced(partID, partName, partPrice, partStock, partMin, partMax, companyName);
            updatePartInInventory(modifiedPart);
        }
        else{
            try{
                //store the machine ID as an int
                int machineID = Integer.parseInt(machineS);
                InHouse modifiedPart = new InHouse(partID, partName, partPrice, partStock, partMin, partMax, machineID);
                updatePartInInventory(modifiedPart);

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
    // updates the modified part by comparing partIDs
    private void updatePartInInventory(Part modifiedPart) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == modifiedPart.getId()) {
                allParts.set(i, modifiedPart);
                break;
            }
            }

    }

}
