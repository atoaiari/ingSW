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

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String REGISTRAZIONE_FXML_PATH = "/view/registration.fxml";
    private static final String LOGIN_FXML_PATH = "/view/login.fxml";

    private boolean logged = false;
    private Stage loginStage;
    private Scene basckupScene = null;

    @FXML private TextField emailTextfield;
    @FXML private PasswordField pswPasswordField;
    @FXML private Label forgetpswLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label emailError;
    @FXML private Label pswError;

    public LoginController(){
    }

    public boolean esitoLogin() {
        // TODO Auto-generated method stub
        return logged;
    }

    public void setLoginStage(Stage loginStage) {
        // TODO Auto-generated method stub
        this.loginStage = loginStage;
    }

    public String getMail(){
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
    public void registrationClick() {
        try {
            FXMLLoader registrationLoader = new FXMLLoader(Main.class.getResource(REGISTRAZIONE_FXML_PATH));
            AnchorPane registrationRoot = registrationLoader.load();

            //creo stage e scene
            loginStage.setTitle("Registrazione");
            Scene registrationMainScene = new Scene(registrationRoot);
            basckupScene = loginStage.getScene();
            loginStage.setScene(registrationMainScene);

            //Assegno controller
            RegistrationController myController = (RegistrationController) registrationLoader.getController();
            System.out.println(myController);
            myController.setRegistrationStage(loginStage);
            myController.setLoginController(this);
        } catch (IOException e) {
            System.err.println("File " + REGISTRAZIONE_FXML_PATH + " non trovato");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreScene(){
        loginStage.setTitle("Login");
        loginStage.setScene(basckupScene);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerButton.setOnAction(e -> registrationClick());
    }
}
