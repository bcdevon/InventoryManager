package devon.inventorymanager;

import devon.inventorymanager.model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/** This class creates an application*/

public class Main extends Application {

    /**This is the main method.
     This is the first method that gets called and launches the application.*/
    public static void main(String[] args) {
        Inventory.addTestData();
        launch();
    }

    /**This is the start screen. This is the first screen that is displayed when the program is run.*/
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }


}