package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import model.Store;


public class StoreController implements Initializable {
    @FXML private Button LogoutButton;
    @FXML private TextField searchTextField;
    @FXML private CheckBox CDCheckBox;
    @FXML private CheckBox DVDCheckBox;
    @FXML private ChoiceBox<?> GenereChoiceBox;
    @FXML private Button FilterResetButton;
    @FXML private TilePane productsTilePane;

    private Stage primaryStage;
    //private Store store;


    public StoreController(){
        //store = Store.getStore();
    }


    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }







    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<AnchorPane> products = null;
        try {
            products = getProductsLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Setting the orientation for the Tile Pane
        productsTilePane.setOrientation(Orientation.HORIZONTAL);

        //Setting the alignment for the Tile Pane
        productsTilePane.setTileAlignment(Pos.CENTER_LEFT);

        //Setting the preferred columns for the Tile Pane
        productsTilePane.setPrefRows(4);

        //Retrieving the observable list of the Tile Pane
        ObservableList list = productsTilePane.getChildren();

        //Adding the array of buttons to the pane
        list.addAll(products);

    }

    private ObservableList getProductsLayout() throws IOException {
        ObservableList result = FXCollections.observableArrayList();
        for(product : Store.getInstance().getProducts()){
            FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("/view/product.fxml"));
            AnchorPane layout = loginLoader.load();
            // ottieni controller

            // controller.setProduct(product)
            result.add(layout);
        }
        return result;
    }
}
