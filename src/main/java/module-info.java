module devon.inventorymanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens devon.inventorymanager to javafx.fxml;
    exports devon.inventorymanager;
    exports devon.inventorymanager.controller;
    opens devon.inventorymanager.controller to javafx.fxml;
    opens devon.inventorymanager.model to javafx.fxml;
    exports devon.inventorymanager.model;
}