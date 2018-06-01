package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private boolean logged = false;
    private Stage loginStage;

    @FXML private TextField emailTextfield;
    @FXML private PasswordField pswPasswordField;
    @FXML private Label forgetpswLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    public LoginController(){
        //loginButton.setOnAction(e -> loginClick());
    }

    public boolean esitoLogin() {
        // TODO Auto-generated method stub
        return logged;
    }

    public void setLoginStage(Stage loginStage) {
        // TODO Auto-generated method stub
        this.loginStage = loginStage;
    }

    private void loginClick() {
        loginStage.close();
    }

    public String getUsername(){
        return emailTextfield.getText();
    }

}
