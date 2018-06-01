package controller;

import java.io.IOException;
import java.util.Optional;


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


public class Main extends Application {
    private static final String LOGIN_FXML_PATH = "/view/login.fxml";
    private static final String REGISTRAZIONE_FXML_PATH = "/view/registrazione.fxml";
    private static final String NEGOZIO_FXML_PATH = "/view/negozio.fxml";

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Virtuoso Music Store");

        /*
         * Apro la finestra di login.
         * Se l'utente inserisce username e password validi,
         * si prosegue all'apertura della finestra principale del programma
         */

        if (! eseguitoLogin())
            return;


        //showMagazzino();
    }

    /**
     * Metodo che richiama la finestra di login.
     * @return true se l'utente inserisce username e password validi.
     */
    private boolean eseguitoLogin() {
        try {
            FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource(LOGIN_FXML_PATH));
            AnchorPane loginRoot = loginLoader.load();

            // creo stage e scene
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.initOwner(primaryStage);
            Scene loginMainScene = new Scene(loginRoot);
            loginStage.setScene(loginMainScene);

            // Assegno controller
            LoginController myController = (LoginController) loginLoader.getController();
            System.out.println(myController);
            myController.setLoginStage(loginStage);

            loginStage.showAndWait();
            return myController.esitoLogin();
        } catch (IOException e) {
            System.err.println("File " + LOGIN_FXML_PATH + " non trovato");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


//    private boolean registration() {
//        try {
//            FXMLLoader registrationLoader = new FXMLLoader(Main.class.getResource(REGISTRAZIONE_FXML_PATH));
//            AnchorPane registrationRoot = registrationLoader.load();
//
//            //creo stage e scene
//            Stage registrationStage = new Stage();
//            registrationStage.setTitle("Registrazione");
//            registrationStage.initModality(Modality.APPLICATION_MODAL);
//            registrationStage.initOwner(primaryStage);
//            Scene registrationMainScene = new Scene(registrationRoot);
//            registrationStage.setScene(registrationMainScene);
//
//            //Assegno controller
//            RegistrationController myController = (RegistrationController) registrationLoader.getController();
//            System.out.println(myController);
//            myController.setRegistrationStage(registrationStage);
//
//            registrationStage.showAndWait();
//            //return myController.esitoLogin();
//        } catch (IOException e) {
//            System.err.println("File " + LOGIN_FXML_PATH + " non trovato");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
