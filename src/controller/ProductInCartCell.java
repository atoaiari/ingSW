package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import model.Cart;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/////////////////////////////////////
public class ProductInCartCell extends ListCell<Pair<Product, Integer>> {
    @FXML private AnchorPane cellAnchorPane;
    @FXML private ImageView prodInCartImageView;
    @FXML private Label prodInCartTitleLabel;
    @FXML private Label prodInCartArtistLabel;
    @FXML private Label prodInCartTypeLabel;
    @FXML private Label prodInCartPriceLabel;
    @FXML private ComboBox<Integer> prodInCartQuantityComboBox;
    @FXML private Button prodInCartRemoveButton;

    private FXMLLoader cellLoader;
    private static final String FXML_CELL_PATH = "/view/productInCart.fxml";
    private ObservableList<Integer> quantity = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        prodInCartQuantityComboBox.setOnAction(event -> Cart.getInstance().updateQuantity(this.getItem(), prodInCartQuantityComboBox.getValue()));
    }*/

    @Override
    protected void updateItem(Pair<Product, Integer> prod, boolean empty) {
        super.updateItem(prod, empty);

        if(empty || prod == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if(cellLoader == null) {
                cellLoader = new FXMLLoader();
                cellLoader.setLocation(ProductInCartCell.class.getResource(FXML_CELL_PATH));
                cellLoader.setController(this);
                try {
                    cellLoader.load();
                } catch(IOException e) {
                    System.err.println("Non ho trovato il file: " + FXML_CELL_PATH);
                }
            }
            prodInCartTitleLabel.setText(prod.getKey().getTitle());
            prodInCartArtistLabel.setText(prod.getKey().getPerformer());
            prodInCartTypeLabel.setText(prod.getKey().getType());
            prodInCartPriceLabel.setText(String.format("%.2f", prod.getKey().getPrice()));
            prodInCartQuantityComboBox.setItems(quantity);

            File im = new File("data/products/img/" + prod.getKey().getImg());
            try {
                prodInCartImageView.setImage(new Image(im.toURI().toURL().toExternalForm()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            /*if (prodInCartQuantityComboBox.getValue() != prod.getValue()) {
                prodInCartQuantityComboBox.setPromptText(String.valueOf(prod.getValue()));
            }*/

            //prodInCartQuantityComboBox.setPromptText(String.valueOf(prod.getValue()));
            // prodInCartQuantityComboBox.setOnAction(event -> Cart.getInstance().updateQuantity(prod, prodInCartQuantityComboBox.getValue()));
            prodInCartQuantityComboBox.setOnAction(event -> Cart.getInstance().addToCart(prod.getKey(), prodInCartQuantityComboBox.getValue() - prod.getValue()));
            //prodInCartQuantityChoiceBox.setValue(prod.getValue());;

            // prodInCartQuantityComboBox.setPromptText(String.valueOf(Cart.getInstance().getValueOfProduct(prod.getKey())));
            prodInCartRemoveButton.setOnMouseClicked(event -> Cart.getInstance().removeFromCart(prod));
            prodInCartQuantityComboBox.setValue(prod.getValue());

            setText(null);
            setGraphic(cellAnchorPane);
        }
    }
}
