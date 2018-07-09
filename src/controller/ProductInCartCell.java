package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import model.Product;

import java.io.IOException;

/////////////////////////////////////
public class ProductInCartCell extends ListCell<Pair<Product, Integer>> {
    @FXML private AnchorPane cellAnchorPane;
    @FXML private ImageView prodInCartImageView;
    @FXML private Label prodInCartTitleLabel;
    @FXML private Label prodInCartArtistLabel;
    @FXML private Label prodInCartTypeLabel;
    @FXML private Label prodInCartPriceLabel;
    @FXML private ChoiceBox<?> prodInCartQuantityChoiceBox;
    @FXML private Button prodInCartRemoveButton;

    private FXMLLoader cellLoader;
    private static final String FXML_CELL_PATH = "/view/productInCart.fxml";

    @Override
    protected void updateItem(Pair<Product, Integer> prod, boolean empty) {
        super.updateItem(prod, empty);

        if(empty || prod == null) {
            setText(null);
            setGraphic(null);
            System.out.println("ciaone2");
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
            System.out.println("ciaone");
            prodInCartTitleLabel.setText(prod.getKey().getTitle());
            prodInCartArtistLabel.setText(prod.getKey().getPerformer());
            prodInCartTypeLabel.setText(prod.getKey().getType());
            prodInCartPriceLabel.setText(String.valueOf(prod.getKey().getPrice()));
            setText(null);
            setGraphic(cellAnchorPane);
        }
    }
}
