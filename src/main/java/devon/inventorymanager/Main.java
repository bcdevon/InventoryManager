package devon.inventorymanager;

import devon.inventorymanager.model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/** This class is the beginning of the application*/

public class Main extends Application {

    /**This is the main method.
     * This is the first method that gets called when the application is launched*/
    public static void main(String[] args) {
        Inventory.addTestData();
        launch();
    }

    /**This is the start method.
     * This method initializes and displays the main screen.
     * @param stage This is the primary stage
     * @throws IOException If and input/output error occurs*/
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }


}