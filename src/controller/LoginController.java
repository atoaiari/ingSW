package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {

    private boolean logged = false;
    private Stage loginStage;

    @FXML private TextField emailTextfield;
    @FXML private PasswordField pswPasswordField;
    @FXML private Label forgetpswLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label emailError;
    @FXML private Label pswError;

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

    private String getMail(){
        return emailTextfield.getText();
    }

    private String getPassword(){
        return pswPasswordField.getText();
    }

    @FXML
    public void loginClick() {
        if (getMail() == null || getMail().length() == 0)
            emailError.setText("Inserire email");
        else if (getPassword() == null || getPassword().length() == 0)
            pswError.setText("Inserisci password");
        else {
            logged=true;
            loginStage.close();
        }
    }

    @FXML
    public void noErrorMail() {
        if (getMail() != null || getMail().length() != 0)
            emailError.setText("");
    }

    @FXML
    public void noErrorPsw() {
        if (getPassword() != null || getPassword().length() != 0)
            pswError.setText("");
    }

    @FXML
    public void keyboardEnterPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            loginButton.fire();
    }

}
