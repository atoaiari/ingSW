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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    private static final String REGISTRAZIONE_FXML_PATH = "/view/registration.fxml";

    private boolean logged = false;
    private Stage loginStage;
    private Scene backupScene = null;

    @FXML private TextField emailTextfield;
    @FXML private PasswordField pswPasswordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label emailError;
    @FXML private Label pswError;

    public LoginController(){
    }

    /**
     * Metodo che restituisce l'esito del login.
     * @return true se l'utente inserisce username e password validi.
     */
    public boolean esitoLogin() {
        // TODO Auto-generated method stub
        return logged;
    }

    /**
     * Metodo che setta lo stage del login.
     * @param loginStage stage da settare.
     */
    public void setLoginStage(Stage loginStage) {
        // TODO Auto-generated method stub
        this.loginStage = loginStage;
    }

    /**
     * Metodo che ritorna la email inserita dall'utente.
     * @return email inserita dall'utente.
     */
    public String getMail(){
        return emailTextfield.getText();
    }

    /**
     * Metodo che ritorna la password inserita dall'utente.
     * @return password inserita dall'utente.
     */
    private String getPassword(){
        return pswPasswordField.getText();
    }

    /**
     * Metodo che controlla se lo user è già registrato e se i dati inseriti sono corretti.
     */
    @FXML
    public void loginClick() {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher mail = p.matcher(emailTextfield.getText());
        if (getMail() == null || getMail().length() == 0 || !mail.matches())
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
                return;
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

    /**
     * Metodo che carica la pagina di registrazione.
     */
    @FXML
    public void registrationClick() {
        try {
            FXMLLoader registrationLoader = new FXMLLoader(Main.class.getResource(REGISTRAZIONE_FXML_PATH));
            AnchorPane registrationRoot = registrationLoader.load();

            //creo stage e scene
            loginStage.setTitle("Registrazione");
            Scene registrationMainScene = new Scene(registrationRoot);
            backupScene = loginStage.getScene();
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

    /**
     * Metodo che ripristana la scena di login dopo la registrazione, segnalando anche eventuali errori con un alert.
     * @param res esito della registrazione
     */
    public void restoreScene(int res){
        loginStage.setTitle("Login");
        loginStage.setScene(backupScene);
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

    /**
     * Metodo che gestisce il messaggio di errore della mail.
     */
    @FXML
    public void noErrorMail() {
        if (getMail() != null || getMail().length() != 0)
            emailError.setText("");
    }

    /**
     * Metodo che gestisce il messaggio di errore della password.
     */
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
