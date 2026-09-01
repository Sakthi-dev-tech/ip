package rambo.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import rambo.Rambo;

/**
 * Controller for the main Rambo chat window.
 */
public class MainWindow {
    private static final String RAMBO_IMAGE_PATH = "/images/DaRambo.png";
    private static final String USER_IMAGE_PATH = "/images/DaUser.png";

    private Rambo rambo;

    private final Image ramboImage = new Image(getClass().getResourceAsStream(RAMBO_IMAGE_PATH));
    private final Image userImage = new Image(getClass().getResourceAsStream(USER_IMAGE_PATH));

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    /**
     * Sets up the scroll behavior after FXML loading is complete.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the chatbot used to produce responses.
     *
     * @param rambo chatbot instance
     */
    public void setRambo(Rambo rambo) {
        this.rambo = rambo;
        dialogContainer.getChildren().add(DialogBox.getRamboDialog(
                "Hello! I am Rambo.\nWhat can I do for you?", ramboImage));
    }

    /**
     * Handles user input from the text field and send button.
     */
    @FXML
    public void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }

        String response = rambo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getRamboDialog(response, ramboImage));
        userInput.clear();

        if (rambo.isExitRequested()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
