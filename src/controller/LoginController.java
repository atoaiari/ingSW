package controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.User;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
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
        // System.out.println(emailTextfield.getText().hashCode());
        return emailTextfield.getText();
    }

    private String getPassword(){
        return pswPasswordField.getText();
    }

    @FXML
    public void loginClick() throws User.UnloadedUserException {
        if (getMail() == null || getMail().length() == 0)
            emailError.setText("Inserire email");
        else if (getPassword() == null || getPassword().length() == 0)
            pswError.setText("Inserisci password");
        else {
            try {
                User.loadUser(getMail());
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Errore");
                alert1.setHeaderText(null);
                alert1.setContentText("Utente non registrato!");

                alert1.showAndWait();
            }
            System.out.println("mail: " + User.getInstance().getUserMail());
            System.out.println("psw: " + User.getInstance().getPsw());
            System.out.println("psw da controllare: " + String.valueOf(getPassword().hashCode()));
            if (User.getInstance().getPsw().equals(String.valueOf(getPassword().hashCode()))) {
                logged = true;
                System.out.println("Logged");
                loginStage.close();
            }
            else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Errore");
                alert1.setHeaderText(null);
                alert1.setContentText("Password errata!");

                alert1.showAndWait();
            }

            /*logged = true;
            System.out.println("Logged");
            loginStage.close();*/
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

    public void restoreScene(int res){
        loginStage.setTitle("Login");
        loginStage.setScene(basckupScene);
        switch (res){
            case 1:
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Info");
                alert1.setHeaderText(null);
                alert1.setContentText("Utente già registrato! Necessario effettuare login per accedere.");

                alert1.showAndWait();
                break;
            case 2:
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Info");
                alert2.setHeaderText(null);
                alert2.setContentText("Registrazione avvenuta con successo! Effettuare login per accedere.");

                alert2.showAndWait();
                break;
            case 3:
                break;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerButton.setOnAction(e -> registrationClick());
    }
}
