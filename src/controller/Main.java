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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application {
    private static final String LOGIN_FXML_PATH = "/view/login.fxml";

    private Stage primaryStage;
    private StoreController myController;

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

        openStore();
    }

    /**
     * Metodo che carica la scena principale del negozio.
     */
    private void openStore() {
        FXMLLoader storeLoader = new FXMLLoader();
        storeLoader.setLocation(getClass().getResource("/view/store2.fxml"));

        Parent root = null;
        try {
            root = storeLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        myController = storeLoader.getController();
        myController.setStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> closeStore(event));
        primaryStage.setResizable(false);
        primaryStage.show();
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
            loginStage.setResizable(false);

            loginStage.showAndWait();
            return myController.esitoLogin();
        } catch (IOException e) {
            System.err.println("File " + LOGIN_FXML_PATH + " non trovato");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo che permette la chiusura del programma con alert.
     * @param event click sul tasto di chiusura.
     */
    public void closeStore(Event event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Vuoi davvero uscire?");
        alert.setContentText(null);

        ButtonType si = new ButtonType("Si");
        ButtonType annulla = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(si, annulla);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == si){
        }
        else if (result.get() == annulla) {
            event.consume();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
