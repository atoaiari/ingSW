package model;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Cart {
    private static Cart ourInstance = null;
    // private Set<Product> productsSet;
    // private List<Pair<Product, Integer>> productsInCart;
    // private HashMap<Product, Integer> productsInCart;
    private ObservableList<Pair<Product, Integer>> productsInCart;

    public static Cart getInstance() {
        if (ourInstance == null) {
            try {
                ourInstance = new Cart();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    private Cart() throws ParseException, IOException {
        productsInCart = FXCollections.observableArrayList();
    }

    public ObservableList<Pair<Product, Integer>> getCart() {
        return productsInCart;
    }
    public void addToCart(Product product, int quantita){
        boolean added = false;
        //System.out.println("Aggiunto " + product);
        for (Pair<Product, Integer> pair: productsInCart) {
            if (pair.getKey().equals(product)) {
                productsInCart.remove(pair);
                productsInCart.add(new Pair<>(product, pair.getValue() + quantita));
                added = true;
            }
        }
        if (!added) productsInCart.add(new Pair<>(product, quantita));
        System.out.println(productsInCart);
    }

    /*public void removeFromCart(Pair<CD, Integer> selezionato) {
        giacenza.remove(selezionato);
    }*/
    /*public void rifornisci(Pair<CD, Integer> prodotto, int quantita) {
        giacenza.remove(prodotto);
        giacenza.add(new Pair<CD, Integer>(prodotto.getKey(), prodotto.getValue()+quantita));
    }*/
}
