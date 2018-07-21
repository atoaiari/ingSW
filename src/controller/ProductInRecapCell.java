package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import model.Product;

import java.io.IOException;

public class ProductInRecapCell extends ListCell<Pair<Product, Integer>> {
    @FXML private AnchorPane cellAnchorPane;
    @FXML private Label recapTitleLabel;
    @FXML private Label recapTypeLabel;
    @FXML private Label recapQuantityLabel;
    @FXML private Label recapPriceLabel;

    private FXMLLoader cellLoader;
    private static final String FXML_CELL_PATH = "/view/productInRecap.fxml";


    /**
     * Metodo per la generazione di una cella nella lista dei prodotti nel riepilogo ordine.
     * @param prod istanza di un prodotto con la relativa quantità nel carrello.
     */
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
