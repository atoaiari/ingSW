package controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.util.Pair;
import model.Cart;
import model.Product;
import model.Store;
import model.User;
import org.json.simple.JSONObject;


public class StoreController implements Initializable {
    @FXML private Button logoutButton;
    @FXML private TextField searchTextField;
    @FXML private CheckBox CDCheckBox;
    @FXML private CheckBox DVDCheckBox;
    @FXML private ChoiceBox<String> genreChoiceBox;
    @FXML private ChoiceBox<String> orderByChoiceBox;
    @FXML private Button filterResetButton;
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
    @FXML private Button closeDetailsButton;

    // cart panel
    @FXML private ListView<Pair<Product, Integer>> cartListview;
    @FXML private Button checkoutButton;
    @FXML private Button emptyCartButton;
    @FXML private Button closeCartButton;
    @FXML private Label itemsInCart;
    @FXML private Label cartTotLabel;

    // checkout panel
    @FXML private Pane checkoutPane;
    @FXML private Button buyButton;
    @FXML private Button backToCartButton;
    @FXML private Label checkoutTotLabel;
    @FXML private Label checkoutNameLabel;
    @FXML private Label checkoutLastLabel;
    @FXML private Label checkoutMailLabel;
    @FXML private TextField whereTextField;
    @FXML private ListView<Pair<Product, Integer>> recapListview;
    @FXML private ImageView cardImageView;
    @FXML private TextField cardNumberTextField;
    @FXML private TextField cardCodTextField;
    @FXML private ComboBox<Integer> cardYearCombo;
    @FXML private ComboBox<Integer> cardMonthCombo;
    @FXML private Label cardNumberError;
    @FXML private Label cardCodError;
    @FXML private Label whereError;
    @FXML private RadioButton corriereRadio;
    @FXML private RadioButton corriereERadio;
    @FXML private RadioButton postaRadio;
    @FXML private RadioButton ccRadio;
    @FXML private RadioButton bonificoRadio;
    @FXML private RadioButton paypalRadio;

    // per animazione
    private TranslateTransition animDP;
    private TranslateTransition animDPclose;
    private TranslateTransition animPSP;
    private TranslateTransition animPSPclose;
    private TranslateTransition animCP;
    private TranslateTransition animCPclose;

    private ObservableList<String> genreList = FXCollections.observableArrayList("Tutti", "Rock", "House", "Classica", "Pop", "Soul", "Latino", "Funk", "Folk");
    private ObservableList<String> orderByList = FXCollections.observableArrayList("Titolo", "Artista", "Prezzo crescente", "Prezzo descrescente");

    private boolean detailsOpened = false;
    private Stage primaryStage;

    // private String userMail;
    //private Store store;

    public StoreController(){ }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // Set<Product> products = Store.getInstance().getProducts();
        ArrayList<Product> products = new ArrayList(Store.getInstance().getProducts());
        products.sort(Comparator.comparing(Product::getTitle));
        setTilePaneChildren(products);
        genreChoiceBox.setItems(genreList);
        genreChoiceBox.setValue("Tutti");
        orderByChoiceBox.setItems(orderByList);
        orderByChoiceBox.setValue("Titolo");
        CDCheckBox.setSelected(true);
        DVDCheckBox.setSelected(true);

        logoutButton.setOnAction(e -> closeStore(e));
        prepareDetailsPaneAnimation();
        prepareCartPaneAnimation();
        closeDetailsButton.setOnAction(e -> closeDetailsPane());
        searchTextField.setOnKeyReleased(e -> filterProducts());
        orderByChoiceBox.setOnAction(e -> filterProducts());
        genreChoiceBox.setOnAction(e -> filterProducts());
        CDCheckBox.setOnAction(e -> filterProducts());
        DVDCheckBox.setOnAction(e -> filterProducts());
        filterResetButton.setOnAction(e -> filterReset());

        System.out.println(Cart.getInstance().getCart());
        cartListview.setItems(Cart.getInstance().getCart());
        cartListview.setCellFactory(cartListview -> new ProductInCartCell());
        cartListview.setManaged(true);

        recapListview.setItems(Cart.getInstance().getCart());
        recapListview.setCellFactory(recapListview -> new ProductInRecapCell());
        recapListview.setManaged(true);

        try {
            userLabel.setText(User.getInstance().getName() + " " + User.getInstance().getLastName() );
        } catch (User.UnloadedUserException e) {
            e.printStackTrace();
        }

        Cart.getInstance().getCart().addListener((ListChangeListener<Pair<Product, Integer>>) c ->
                cartTotLabel.setText(String.format("%.2f", Cart.getInstance().getCartTotal())));

        Cart.getInstance().getCart().addListener((ListChangeListener<Pair<Product, Integer>>) c ->
                itemsInCart.setText(String.valueOf(Cart.getInstance().getTotItems())));

        Cart.getInstance().getCart().addListener((ListChangeListener<Pair<Product, Integer>>) c ->
                CartButton.setText(String.valueOf(Cart.getInstance().getTotItems())));

        // cart
        emptyCartButton.setOnAction(event -> Cart.getInstance().getCart().clear());
        closeCartButton.setOnAction(event -> closeCartPane());

        // checkout
        checkoutPane.setVisible(false);
        checkoutPane.setManaged(false);
        checkoutButton.setOnAction(event -> {
            try {
                checkout();
            } catch (User.UnloadedUserException e) {
                e.printStackTrace();
            }
        });

        /*cardNumberTextField.setOnKeyTyped(event -> {
            try {
                changeCardImage();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });*/


        ObservableList<Integer> months = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        ObservableList<Integer> years = FXCollections.observableArrayList(18, 19, 20, 21, 22, 23, 24, 25, 26, 27);

        cardMonthCombo.setItems(months);
        cardMonthCombo.setValue(1);
        cardYearCombo.setItems(years);
        cardYearCombo.setValue(18);

        final ToggleGroup groupSpedizione = new ToggleGroup();
        corriereRadio.setToggleGroup(groupSpedizione);
        corriereRadio.setSelected(true);
        corriereERadio.setToggleGroup(groupSpedizione);
        postaRadio.setToggleGroup(groupSpedizione);

        final ToggleGroup groupPagamento = new ToggleGroup();
        ccRadio.setToggleGroup(groupPagamento);
        ccRadio.setSelected(true);
        bonificoRadio.setToggleGroup(groupPagamento);
        paypalRadio.setToggleGroup(groupPagamento);

    }

    private void closeCartPane() {
        animCPclose.setToX((cartPane.getWidth()));
        animCPclose.play();
        ProductScrollPane.setDisable(false);
        DetailsPane.setDisable(false);
    }

    private void checkout() throws User.UnloadedUserException {
        if (Cart.getInstance().getTotItems() != 0) {
            checkoutPane.setVisible(true);
            checkoutPane.setManaged(true);
            CartButton.setDisable(true);
            checkoutNameLabel.setText(User.getInstance().getName());
            checkoutLastLabel.setText(User.getInstance().getLastName());
            checkoutMailLabel.setText(User.getInstance().getPsw());
            checkoutTotLabel.setText(cartTotLabel.getText());

            buyButton.setOnAction(event -> {
                try {
                    buy();
                } catch (User.UnloadedUserException e) {
                    e.printStackTrace();
                }
            });
            backToCartButton.setOnAction(event -> backToCart());
        }
    }

    private void buy() throws User.UnloadedUserException {
        if (whereTextField.getText() == null || whereTextField.getText().length() == 0)
            whereError.setText("Inserire un indirizzo");
        else if ((cardNumberTextField.getText() == null || cardNumberTextField.getText().length() != 16) && ccRadio.isSelected())
            cardNumberError.setText("Inserire il numero della carta (16 cifre)");
        else if ((cardCodTextField.getText() == null || cardCodTextField.getText().length() != 3) && ccRadio.isSelected())
            cardCodError.setText("Inserire il codice a 3 cifre");
        else {
            System.out.println("Acquista");

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            File out = new File("data/shop/" + User.getInstance().getID() + "_" + timestamp.getTime() + ".json");

            JSONObject obj = new JSONObject();

            obj.put("userId", User.getInstance().getID());
            obj.put("products", Cart.getInstance().getCart());
            obj.put("timestamp", timestamp);
            obj.put("total", Cart.getInstance().getCartTotal());
            obj.put("address", whereTextField.getText());
            if (ccRadio.isSelected()) {
                obj.put("payment_method", "Carta di credito");
                obj.put("card_number", cardNumberTextField.getText());
            }
            else if (bonificoRadio.isSelected())
                obj.put("payment_method", "Bonifico bancario");
            else if (paypalRadio.isSelected())
                obj.put("payment_method", "Paypal");

            if (corriereRadio.isSelected())
                obj.put("spedition_method", "Corriere (standard)");
            else if (corriereERadio.isSelected())
                obj.put("spedition_method", "Corriere (express)");
            else if (postaRadio.isSelected())
                obj.put("spedition_method", "Posta");

            System.out.print(obj);
            try (FileWriter file = new FileWriter(out)) {
                file.write(obj.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText("Acquisto effettuato!\nNumero di articoli acquistati: " + Cart.getInstance().getTotItems() + "\nCosto totale: € " + Cart.getInstance().getCartTotal());

            alert.showAndWait();

            Cart.getInstance().getCart().clear();

            checkoutPane.setVisible(false);
            checkoutPane.setManaged(false);
            CartButton.setDisable(false);
            closeCartPane();
        }
    }

    @FXML
    public void noErrorWhere() {
        if (whereTextField.getText() != null || whereTextField.getText().length() != 0)
            whereError.setText("");
    }

    @FXML
    public void noCardNumberError() {
        if (cardNumberTextField.getText() != null || cardNumberTextField.getText().length() != 0)
            cardNumberError.setText("");
        try {
            changeCardImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void noCardCodError() {
        if (cardCodTextField.getText() != null || cardCodTextField.getText().length() != 0)
            cardCodError.setText("");
    }

    private void changeCardImage() throws MalformedURLException {
        if (cardNumberTextField.getText().startsWith("4")){
            File im = new File("src/view/visa.png");
            cardImageView.setImage(new Image(im.toURI().toURL().toExternalForm()));
        }
        else if (cardNumberTextField.getText().startsWith("5")){
            File im = new File("src/view/mastercard.png");
            cardImageView.setImage(new Image(im.toURI().toURL().toExternalForm()));
        }
        else if(cardNumberTextField.getText().startsWith("3")){
            File im = new File("src/view/americanexpress.png");
            cardImageView.setImage(new Image(im.toURI().toURL().toExternalForm()));
        }
        else{
            File im = new File("src/view/creditcard.png");
            cardImageView.setImage(new Image(im.toURI().toURL().toExternalForm()));
        }

    }

    private void backToCart() {
        checkoutPane.setVisible(false);
        checkoutPane.setManaged(false);
        CartButton.setDisable(false);
    }



    //////////////////////////////////////////

    private void filterReset() {
        searchTextField.setText("");
        orderByChoiceBox.setValue("Titolo");
        genreChoiceBox.setValue("Tutti");
        CDCheckBox.setSelected(true);
        DVDCheckBox.setSelected(true);
        filterProducts();
    }

    private void filterProducts(){
        clearCds();
        ArrayList<Product> filteredProducts = new ArrayList<>(Store.getInstance().getFilteredProducts(searchTextField.getText(), orderByChoiceBox.getValue(), genreChoiceBox.getValue(), CDCheckBox.isSelected(), DVDCheckBox.isSelected()));
        setTilePaneChildren(filteredProducts);
    }

    private void clearCds() {
        productsTilePane.getChildren().clear();
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

    private void setTilePaneChildren(List<Product> productsObj){
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

    private ObservableList getProductsLayout(List<Product> products) throws IOException {
        ObservableList result = FXCollections.observableArrayList();
        for(Product product : products){
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
        pPriceLabel.setText(String.valueOf(product.getPrice()));
        pPerformersLabel.setText(product.getPerformers());
        pTypeLabel.setText(product.getType());
        pGenreLabel.setText(product.getGenre());


        pDescriptionLabel.setText(product.getDescription());
        pTracksLabel.setText(product.getTracksString());

        if(!detailsOpened){
            animDP.play();
            animPSP.play();
            animPSP.setOnFinished(event -> ProductScrollPane.prefHeightProperty().set(565.0));
            detailsOpened = true;
        }
    }

    public void disableCC(ActionEvent actionEvent) {
        cardNumberTextField.setDisable(true);
        cardYearCombo.setDisable(true);
        cardMonthCombo.setDisable(true);
        cardCodTextField.setDisable(true);
        cardImageView.setDisable(true);
    }

    public void enableCC(ActionEvent actionEvent) {
        cardNumberTextField.setDisable(false);
        cardYearCombo.setDisable(false);
        cardMonthCombo.setDisable(false);
        cardCodTextField.setDisable(false);
        cardImageView.setDisable(false);
    }

    public void closeStore(Event event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Vuoi davvero uscire?");
        alert.setContentText(null);

        ButtonType si = new ButtonType("Si");
        ButtonType annulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(si, annulla);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == si){
            close();
        }
        else if (result.get() == annulla) {
            event.consume();
        }
    }
    /*public void setUserMail(String userMail) {
        userLabel.setText(userMail);
    }*/
}

