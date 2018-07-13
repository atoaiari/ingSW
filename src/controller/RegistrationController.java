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
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    private Stage registrationStage;

    @FXML private TextField emailTextfield;
    @FXML private PasswordField pswField;
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
        registrationButton.setOnAction(e -> registrationClick());
    }

    private void registrationClick() {
        // inserisco entry nel database
        System.out.println("Registrazione");
        int res;
        File out = new File("data/users/" + String.valueOf(emailTextfield.getText().hashCode()) + ".json");
        if (out.exists()) {
            System.out.println("Utente già registrato!");
            res = 1;
        }
        else{
            JSONObject obj = new JSONObject();

            obj.put("userId", String.valueOf(emailTextfield.getText().hashCode()));
            obj.put("name", nameTextfield.getText());
            obj.put("last", lastnameTextfield.getText());
            obj.put("email", emailTextfield.getText());
            obj.put("psw", String.valueOf(pswField.getText().hashCode()));
            LocalDate localDate = LocalDate.now();
            obj.put("registrationDate", DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));
            obj.put("bonus", false);

            System.out.print(obj);
            try (FileWriter file = new FileWriter(out)) {
                file.write(obj.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = 2;
        }

        loginController.restoreScene(res);
    }

    private void loginClick() {
        loginController.restoreScene(3);
    }
}
