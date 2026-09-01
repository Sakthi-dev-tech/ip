package rambo.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rambo.Rambo;

/**
 * A GUI for Rambo using FXML.
 */
public class Main extends Application {

    private final Rambo rambo = new Rambo();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            scene.getStylesheets().addAll(
                    Main.class.getResource("/css/main.css").toExternalForm(),
                    Main.class.getResource("/css/dialog-box.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Rambo");
            stage.setMinHeight(420);
            stage.setMinWidth(360);
            fxmlLoader.<MainWindow>getController().setRambo(rambo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
