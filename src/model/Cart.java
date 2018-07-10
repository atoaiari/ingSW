package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.Comparator;

public class Cart {
    private static Cart ourInstance = null;
    // private Set<Product> productsSet;
    // private List<Pair<Product, Integer>> productsInCart;
    // private HashMap<Product, Integer> productsInCart;
    private ObservableList<Pair<Product, Integer>> productsInCart;

    public static Cart getInstance() {
        if (ourInstance == null) {
            ourInstance = new Cart();
        }
        return ourInstance;
    }

    private Cart() {
        productsInCart = FXCollections.observableArrayList();
    }

    public ObservableList<Pair<Product, Integer>> getCart() {
        return productsInCart;
    }

    public void addToCart(Product product, int quantita){
        boolean added = false;
        Pair<Product, Integer> newP = null;
        Pair<Product, Integer> oldP = null;
        int indexOfPairToUpdate = 0;
        int oldQ = 0;
        
        //System.out.println("Aggiunto " + product);
        for (Pair<Product, Integer> pair: productsInCart) {
            if (pair.getKey().equals(product)) {
                /*productsInCart.remove(pair);
                productsInCart.add(new Pair<>(product, pair.getValue() + quantita));
                added = true;*/
                indexOfPairToUpdate = productsInCart.indexOf(pair);
//                oldP = pair;
//                newP = new Pair<>(product, pair.getValue() + quantita);
                oldQ = pair.getValue();
                added = true;
            }
        }
        if (!added) productsInCart.add(new Pair<>(product, quantita));
        else {
            productsInCart.set(indexOfPairToUpdate, new Pair<>(product, oldQ + quantita));
//            productsInCart.remove(oldP);
//            productsInCart.add(newP);
        }
        System.out.println(productsInCart);
    }

    public void removeFromCart(Product product) {
        // Pair<Product, Integer> pairToDelete = null;
        ObservableList<Pair<Product, Integer>> backup = FXCollections.observableArrayList();
        FXCollections.copy(backup, productsInCart);
        System.out.println("backup_pre : " + backup);
        int oldValue = 0;
        int indexOfPairToRemove = 0;
        for (Pair<Product, Integer> pair: backup) {
            if (pair.getKey().equals(product)) {
                indexOfPairToRemove = backup.indexOf(pair);
                oldValue = pair.getValue();
                // pairToDelete = pair;
            }
        }
        if ((oldValue-1) > 0)
            backup.set(indexOfPairToRemove, new Pair<>(product, oldValue - 1));
        else
            backup.remove(indexOfPairToRemove);

        //FXCollections.copy(productsInCart, backup);
        System.out.println("backup_post: " + backup);
        // productsInCart.remove(pairToDelete);
        System.out.println("prodincart: " + productsInCart);
    }

    public void updateQuantity(Pair<Product, Integer> pair, Integer newValue) {
        // Pair<Product, Integer> newP = new Pair<>(pair.getKey(), newValue);
        productsInCart.set(productsInCart.indexOf(pair), new Pair<>(pair.getKey(), newValue));
        System.out.println(productsInCart);
    }

    public Integer getValueOfProduct(Product product) {
        for (Pair<Product, Integer> pair: productsInCart) {
            if (pair.getKey().equals(product)) {
                return pair.getValue();
            }
        }
        return null;
    }

    /*public void removeFromCart(Pair<CD, Integer> selezionato) {
        giacenza.remove(selezionato);
    }*/
    /*public void rifornisci(Pair<CD, Integer> prodotto, int quantita) {
        giacenza.remove(prodotto);
        giacenza.add(new Pair<CD, Integer>(prodotto.getKey(), prodotto.getValue()+quantita));
    }*/
}
