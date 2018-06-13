package controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.util.Duration;
import model.Product;
import model.Store;
import org.json.JSONException;
import org.json.simple.parser.ParseException;


public class StoreController implements Initializable {
    @FXML private Button logoutButton;
    @FXML private TextField searchTextField;
    @FXML private CheckBox CDCheckBox;
    @FXML private CheckBox DVDCheckBox;
    @FXML private ChoiceBox<?> GenereChoiceBox;
    @FXML private ChoiceBox<?> orderByChoiceBox;
    @FXML private Button FilterResetButton;
    @FXML private TilePane productsTilePane;
    @FXML private Pane DetailsPane;
    @FXML private AnchorPane FirstAnchorPane;
    @FXML private ScrollPane ProductScrollPane;
    @FXML private Button CartButton;

    private boolean detailsOpened = false;
    private Stage primaryStage;
    //private Store store;


    public StoreController(){
        //store = Store.getStore();
    }


    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
//        DetailsPane.setVisible(false);
//        DetailsPane.setManaged(false);
//        FirstAnchorPane.setTopAnchor(ProductScrollPane, 0.0);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Product> products = null;
        try {
            products = Store.getInstance().getProducts();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTilePaneChildren(products);

        logoutButton.setOnAction(e -> close());
        //CartButton.setOnAction(e -> bella());
        prepareDetailPaneAnimation();

    }

    private void prepareDetailPaneAnimation() {
        TranslateTransition animDP=new TranslateTransition(new Duration(350), DetailsPane);
        animDP.setToY(0);
        TranslateTransition animDPclose=new TranslateTransition(new Duration(350), DetailsPane);
        TranslateTransition animPSP=new TranslateTransition(new Duration(350), ProductScrollPane);
        animPSP.setToY(450);
        TranslateTransition animPSPclose=new TranslateTransition(new Duration(350), ProductScrollPane);
        CartButton.setOnAction(e->{
            if(DetailsPane.getTranslateY()!=0){
                animDP.play();
                animPSP.play();
                animPSP.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ProductScrollPane.prefHeightProperty().set(565.0);
                    }
                });
            }else{
                animDPclose.setToY(-(DetailsPane.getHeight()));
                animDPclose.play();
                animPSPclose.setToY(0);
                animPSPclose.play();
                ProductScrollPane.prefHeightProperty().set(1010.0);
            }
        });
    }

//    private void bella() {
//        if(detailsOpened){
//            DetailsPane.setVisible(false);
//            DetailsPane.setManaged(false);
//            FirstAnchorPane.setTopAnchor(ProductScrollPane, 0.0);
//            detailsOpened = false;
//        }
//        else {
//            DetailsPane.setVisible(true);
//            DetailsPane.setManaged(true);
//            FirstAnchorPane.setTopAnchor(ProductScrollPane, null);
//            detailsOpened = true;
//        }
//    }

    private void setTilePaneChildren(Set<Product> productsObj){
        ObservableList<AnchorPane> products = null;
        try {
            products = getProductsLayout(productsObj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Setting the orientation for the Tile Pane
        productsTilePane.setOrientation(Orientation.HORIZONTAL);

        //Setting the alignment for the Tile Pane
        productsTilePane.setTileAlignment(Pos.CENTER_LEFT);

        //Setting the preferred columns for the Tile Pane
        // productsTilePane.setPrefRows(3);

        //Retrieving the observable list of the Tile Pane
        ObservableList list = productsTilePane.getChildren();

        //Adding the array of buttons to the pane
        list.addAll(products);
    }

    private ObservableList getProductsLayout(Set<Product> prodotti) throws IOException {
        ObservableList result = FXCollections.observableArrayList();
        for(Product product : prodotti){
            FXMLLoader productLoader = new FXMLLoader(Main.class.getResource("/view/product.fxml"));
            AnchorPane layout = productLoader.load();
            // ottieni controller
            ProductController myController = (ProductController) productLoader.getController();
            myController.setProduct(product);
            // controller.setProduct(product)
            result.add(layout);
        }
        System.out.println(result);
        return result;
    }

    private void close(){
        primaryStage.close();
    }

}
