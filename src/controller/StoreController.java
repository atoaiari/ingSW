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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.util.Duration;
import model.Product;
import model.Store;
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
    @FXML private Pane cartPane;
    @FXML private Label userLabel;

    // detail panel
    @FXML private ImageView pImage;
    @FXML private Label pInsertDate;
    @FXML private Label pTitleLabel;
    @FXML private Label pArtistLabel;
    @FXML private Label pDescriptionLabel;
    @FXML private Label pPerformersLabel;
    @FXML private Label pInstrumentsLabel;
    @FXML private Label pPriceLabel;
    @FXML private Label pTypeLabel;
    @FXML private Label pGenreLabel;
    @FXML private Label pTracksLabel;
    @FXML private Button addToCartButton;
    @FXML private Button closeDetailsButton;

    // cart panel
    @FXML private Button checkoutButton;
    @FXML private Button emptyCartButton;
    @FXML private Button closeCartButton;
    @FXML private Label itemsInCart;


    // per animazione
    private TranslateTransition animDP;
    private TranslateTransition animDPclose;
    private TranslateTransition animPSP;
    private TranslateTransition animPSPclose;
    private TranslateTransition animCP;
    private TranslateTransition animCPclose;


    private boolean detailsOpened = false;
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
        prepareDetailsPaneAnimation();
        prepareCartPaneAnimation();
        closeDetailsButton.setOnAction(e -> closeDetailsPane());
    }

    private void closeDetailsPane() {
        animDPclose.setToY(-(DetailsPane.getHeight()));
        animDPclose.play();
        animPSPclose.setToY(0);
        animPSPclose.play();
        ProductScrollPane.prefHeightProperty().set(1010.0);
        detailsOpened = false;
    }

    private void prepareDetailsPaneAnimation() {
        animDP=new TranslateTransition(new Duration(350), DetailsPane);
        animDP.setToY(0);
        animDPclose=new TranslateTransition(new Duration(350), DetailsPane);
        animPSP=new TranslateTransition(new Duration(350), ProductScrollPane);
        animPSP.setToY(450);
        animPSPclose=new TranslateTransition(new Duration(350), ProductScrollPane);
//        CartButton.setOnAction(e->{
//            if(DetailsPane.getTranslateY()!=0){
//                animDP.play();
//                animPSP.play();
//                animPSP.setOnFinished(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        ProductScrollPane.prefHeightProperty().set(565.0);
//                    }
//                });
//            }else{
//                animDPclose.setToY(-(DetailsPane.getHeight()));
//                animDPclose.play();
//                animPSPclose.setToY(0);
//                animPSPclose.play();
//                ProductScrollPane.prefHeightProperty().set(1010.0);
//            }
//        });
    }

    private void prepareCartPaneAnimation() {
        animCP=new TranslateTransition(new Duration(350), cartPane);
        animCP.setToX(0);
        animCPclose=new TranslateTransition(new Duration(350), cartPane);
        CartButton.setOnAction(e->{
            if(cartPane.getTranslateX()!=0){
                animCP.play();
                ProductScrollPane.setDisable(true);
                DetailsPane.setDisable(true);
            }else{
                animCPclose.setToX((cartPane.getWidth()));
                animCPclose.play();
                ProductScrollPane.setDisable(false);
                DetailsPane.setDisable(false);
            }
        });
    }

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

        //Adding the array of products
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
            myController.setFatherController(this);
            result.add(layout);
        }
        System.out.println(result);
        return result;
    }

    private void close(){
        primaryStage.close();
    }

    public void setDetails(Product product) throws MalformedURLException {
        pTitleLabel.setText(product.getTitle());
        File im = new File("data/products/img/" + product.getImg());
        pImage.setImage(new Image(im.toURI().toURL().toExternalForm()));
        pInsertDate.setText(product.getInsertDate());
        pArtistLabel.setText(product.getPerformer());
        pInstrumentsLabel.setText(product.getMusicalInstruments());
        pPriceLabel.setText(product.getPrice());
        pPerformersLabel.setText(product.getPerformers());
        pTypeLabel.setText(product.getType());
        pGenreLabel.setText(product.getGenre());


        pDescriptionLabel.setText(product.getDescription());
        pTracksLabel.setText(product.getTracksString());

        if(!detailsOpened){
            animDP.play();
            animPSP.play();
            animPSP.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ProductScrollPane.prefHeightProperty().set(565.0); }
                });
            detailsOpened = true;
        }
    }
}
