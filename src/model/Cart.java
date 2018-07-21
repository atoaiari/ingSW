package model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class Cart {
    private static Cart ourInstance = null;
    // il carrello è organizzato come una lista di coppie <prodotto, quantità>
    private ObservableList<Pair<Product, Integer>> productsInCart;

    // metodo per gestire la classe singleton
    public static Cart getInstance() {
        if (ourInstance == null) {
            ourInstance = new Cart();
        }
        return ourInstance;
    }

    private Cart() {
        productsInCart = FXCollections.observableArrayList();
    }

    /**
     * Metodo che restituisce la lista dei prodotti nel carrello.
     * @return productsInCart prodotti nel carrello.
     */
    public ObservableList<Pair<Product, Integer>> getCart() {
        return productsInCart;
    }

    /**
     * Metodo che aggiunge una nuova coppia <prodotto, quantità> al carrello.
     * @param product prodotto da aggiungere.
     * @param quantita quantità del prodotto.
     */
    public void addToCart(Product product, int quantita){
        boolean added = false;
        int indexOfPairToUpdate = 0;
        int oldQ = 0;

        for (Pair<Product, Integer> pair: productsInCart) {
            if (pair.getKey().equals(product)) {
                indexOfPairToUpdate = productsInCart.indexOf(pair);
                oldQ = pair.getValue();
                added = true;
            }
        }
        if (!added) Platform.runLater(() -> productsInCart.add(new Pair<>(product, quantita)));
        else {
            int finalIndexOfPairToUpdate = indexOfPairToUpdate;
            int finalOldQ = oldQ;
            // operazione asincrona
            Platform.runLater(() -> productsInCart.set(finalIndexOfPairToUpdate, new Pair<>(product, finalOldQ + quantita)));
        }
        System.out.println(productsInCart);
    }

    /**
     * Metodo che rimuove una singola unità di un prodotto dal carrello.
     * @param product prodotto da rimuovere.
     */
    public void removeFromCart(Pair<Product, Integer> product) {
        int oldValue = 0;
        int indexOfPairToRemove = 0;
        for (Pair<Product, Integer> pair: productsInCart) {
            if (pair.getKey().equals(product.getKey())) {
                indexOfPairToRemove = productsInCart.indexOf(pair);
                oldValue = pair.getValue();
            }
        }
        if ((oldValue-1) > 0) {
            int finalIndexOfPairToRemove = indexOfPairToRemove;
            int finalOldValue = oldValue;
            // operazione asincrona
            Platform.runLater(() -> productsInCart.set(finalIndexOfPairToRemove, new Pair<>(product.getKey(), finalOldValue - 1)));
        }
        else
            // operazione asincrona
            Platform.runLater(() -> productsInCart.remove(product));
    }

    /*private void saveCart() throws User.UnloadedUserException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File out = new File("data/cart/" + User.getInstance().getID() + ".json");

        JSONObject obj = new JSONObject();

        obj.put("userId", User.getInstance().getID());
        obj.put("products", new Gson().toJson(Cart.getInstance().getCart()));
        obj.put("update", String.valueOf(timestamp));
        obj.put("total", Cart.getInstance().getCartTotal());

        System.out.print(obj);
        try (FileWriter file = new FileWriter(out)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Metodo che restituisce il prezzo totale dei prodotti nel carrello.
     * @return total costo totale.
     */
    public float getCartTotal() {
        float total = 0;
        for (Pair<Product, Integer> pair: productsInCart){
            total += pair.getKey().getPrice() * pair.getValue();
        }
        return total;
    }

    /**
     * Metodo che restituisce il numero totale di prodotti nel carrello.
     * @return total prodotti nel carrello.
     */
    public int getTotItems() {
        int total = 0;
        for (Pair<Product, Integer> pair: productsInCart){
            total += pair.getValue();
        }
        return total;
    }
}
