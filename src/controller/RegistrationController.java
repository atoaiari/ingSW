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
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @FXML private TextField cityTextField;
    @FXML private TextField telTextField;
    @FXML private Label cityError;
    @FXML private Label telError;
    @FXML private TextField codFiscaleTextField;
    @FXML private Label codFiscaleError;
    private LoginController loginController;

    public RegistrationController() {}

    /**
     * Metodo che setta lo stage corrente.
     * @param registrationStage stage della registrazione.
     */
    public void setRegistrationStage(Stage registrationStage) {
        // TODO Auto-generated method stub
        this.registrationStage = registrationStage;
    }

    @FXML
    public void keyboardEnterPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            registrationButton.fire();
    }

    /**
     * Metodo che setta il login controller.
     * @param loginController controller del login.
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(e -> loginClick());
        registrationButton.setOnAction(e -> registrationClick());
    }

    /**
     * Metodo che effettua la registrazione su file json di un nuovo utente dopo aver controllato che non esista già
     * e che tutti i dati inseriti siano corretti.
     */
    private void registrationClick() {
        Pattern p = Pattern.compile("^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})|([0-9]{11})$");
        Matcher cf = p.matcher(codFiscaleTextField.getText());
        p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher mail = p.matcher(emailTextfield.getText());
        p = Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE);
        Matcher phone = p.matcher(telTextField.getText());
        if (codFiscaleTextField.getText() == null || codFiscaleTextField.getText().length() != 16 || !cf.matches())
            codFiscaleError.setText("Inserire codice fiscale");
        else if (nameTextfield.getText() == null || nameTextfield.getText().length() == 0)
            nameError.setText("Inserire nome");
        else if (lastnameTextfield.getText() == null || lastnameTextfield.getText().length() == 0)
            lastnameError.setText("Inserire cognome");
        else if (emailTextfield.getText() == null || emailTextfield.getText().length() == 0 || !mail.matches())
            emailError.setText("Inserire email");
        else if (pswField.getText() == null || pswField.getText().length() == 0)
            pswError.setText("Inserire password");
        else if (cityTextField.getText() == null || cityTextField.getText().length() == 0)
            cityError.setText("Inserire città");
        else if (telTextField.getText() == null || telTextField.getText().length() == 0 || !phone.matches())
            telError.setText("Inserire telefono");
        else {
            System.out.println("Registrazione");
            int res;
            File out = new File("data/users/" + String.valueOf(emailTextfield.getText().hashCode()) + ".json");
            if (out.exists()) {
                System.out.println("Utente già registrato!");
                res = 1;
            } else {
                JSONObject obj = new JSONObject();

                obj.put("userId", String.valueOf(emailTextfield.getText().hashCode()));
                obj.put("cf", codFiscaleTextField.getText());
                obj.put("name", nameTextfield.getText());
                obj.put("last", lastnameTextfield.getText());
                obj.put("email", emailTextfield.getText());
                obj.put("psw", String.valueOf(pswField.getText().hashCode()));
                LocalDate localDate = LocalDate.now();
                obj.put("registrationDate", DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));
                obj.put("city", cityTextField.getText());
                obj.put("tel", telTextField.getText());

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
    }

    /**
     * Metodo per la gestione della label di errore per il nome.
     */
    @FXML
    public void noErrorName() {
        if (nameTextfield.getText() != null || nameTextfield.getText().length() != 0)
            nameError.setText("");
    }

    /**
     * Metodo per la gestione della label di errore per il cognome.
     */
    @FXML
    public void noErrorLastName() {
        if (lastnameTextfield.getText() != null || lastnameTextfield.getText().length() != 0)
            lastnameError.setText("");
    }

    /**
     * Metodo per la gestione della label di errore per la email.
     */
    @FXML
    public void noErrorMail() {
        if (emailTextfield.getText() != null || emailTextfield.getText().length() != 0)
            emailError.setText("");
    }

    /**
     * Metodo per la gestione della label di errore per la password.
     */
    @FXML
    public void noErrorPsw() {
        if (pswField.getText() != null || pswField.getText().length() != 0)
            pswError.setText("");
    }

    /**
     * Metodo per la gestione della label di errore per il numero di telefono.
     */
    @FXML
    public void noErrorTel() {
        if (telTextField.getText() != null || telTextField.getText().length() != 0)
            telError.setText("");
    }

    /**
     * Metodo per la gestione della label di errore per la città.
     */
    @FXML
    public void noErrorCity() {
        if (cityTextField.getText() != null || cityTextField.getText().length() != 0)
            cityError.setText("");
    }

    /**
     * Metodo per la gestione della label di errore per il codice fiscale.
     */
    @FXML
    public void noErrorCF() {
        if (codFiscaleTextField.getText() != null || codFiscaleTextField.getText().length() != 0)
            codFiscaleError.setText("");
    }

    /**
     * Metodo che ripristina la scena di login.
     */
    private void loginClick() {
        loginController.restoreScene(3);
    }
}
