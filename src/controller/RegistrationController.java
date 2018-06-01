package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    private Stage registrationStage;

    @FXML private TextField emailTextfield;
    @FXML private PasswordField pswPasswordField;
    @FXML private Button registrationButton;
    @FXML private Button loginButton;
    @FXML private TextField nameTextfield;
    @FXML private TextField lastnameTextfield;
    @FXML private Label nameError;
    @FXML private Label lastnameError;
    @FXML private Label emailError;
    @FXML private Label pswError;
    private LoginController loginController;

    public RegistrationController() {}

    public void setRegistrationStage(Stage registrationStage) {
        // TODO Auto-generated method stub
        this.registrationStage = registrationStage;
    }

    @FXML
    public void keyboardEnterPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            registrationButton.fire();
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(e -> loginClick());
    }

    private void loginClick() {
        loginController.restoreScene();
    }
}
