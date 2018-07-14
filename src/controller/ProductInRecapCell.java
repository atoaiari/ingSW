package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import model.Cart;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ProductInRecapCell extends ListCell<Pair<Product, Integer>> {
    @FXML private AnchorPane cellAnchorPane;
    @FXML private Label recapTitleLabel;
    @FXML private Label recapTypeLabel;
    @FXML private Label recapQuantityLabel;
    @FXML private Label recapPriceLabel;

    private FXMLLoader cellLoader;
    private static final String FXML_CELL_PATH = "/view/productInRecap.fxml";

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
                cellLoader.setLocation(ProductInRecapCell.class.getResource(FXML_CELL_PATH));
                cellLoader.setController(this);
                try {
                    cellLoader.load();
                } catch(IOException e) {
                    System.err.println("Non ho trovato il file: " + FXML_CELL_PATH);
                }
            }
            recapTitleLabel.setText(prod.getKey().getTitle());
            recapTypeLabel.setText(prod.getKey().getType());
            recapQuantityLabel.setText(String.valueOf(prod.getValue()));
            recapPriceLabel.setText(String.format("%.2f", prod.getKey().getPrice()));

            setText(null);
            setGraphic(cellAnchorPane);
        }
    }
}
