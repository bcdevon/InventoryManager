package devon.inventorymanager.controller;

import devon.inventorymanager.Main;
import devon.inventorymanager.model.InHouse;
import devon.inventorymanager.model.OutSourced;
import devon.inventorymanager.model.Part;
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
}
