package model;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
            } catch (User.UnloadedUserException e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    private Cart() throws User.UnloadedUserException {
        productsInCart = FXCollections.observableArrayList();

        /*File c = new File("data/cart/" + User.getInstance().getID() + ".json");
        if (c.exists()){
            JSONParser parser = new JSONParser();
            JSONObject prod = null;
            try {
                prod = (JSONObject) parser.parse(new FileReader(c));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Pair<Product, Integer>> p = new Gson().fromJson(prod, List<Pair<Product,Integer>>);
            for (Pair<Product, Integer> pair : p){
                System.out.println(pair);
                productsInCart.add(pair);
            }
        }*/

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
        if (!added) Platform.runLater(() -> productsInCart.add(new Pair<>(product, quantita)));
        else {
            int finalIndexOfPairToUpdate = indexOfPairToUpdate;
            int finalOldQ = oldQ;
            Platform.runLater(() -> productsInCart.set(finalIndexOfPairToUpdate, new Pair<>(product, finalOldQ + quantita)));
//            productsInCart.remove(oldP);
//            productsInCart.add(newP);
        }
        /*try {
            saveCart();
        } catch (User.UnloadedUserException e) {
            e.printStackTrace();
        }*/
        System.out.println(productsInCart);
    }

    public void removeFromCart(Pair<Product, Integer> product) {
        // Pair<Product, Integer> pairToDelete = null;
        // List<Pair<Product, Integer>> backup = new ArrayList<>();
        // System.out.println("backup_pre : " + backup);
        int oldValue = 0;
        int indexOfPairToRemove = 0;
        for (Pair<Product, Integer> pair: productsInCart) {
            if (pair.getKey().equals(product.getKey())) {
                indexOfPairToRemove = productsInCart.indexOf(pair);
                oldValue = pair.getValue();
                // pairToDelete = pair;
            }
        }
        if ((oldValue-1) > 0) {
            int finalIndexOfPairToRemove = indexOfPairToRemove;
            int finalOldValue = oldValue;
            Platform.runLater(() -> productsInCart.set(finalIndexOfPairToRemove, new Pair<>(product.getKey(), finalOldValue - 1)));
        }
        else
            Platform.runLater(() -> productsInCart.remove(product));

        /*try {
            saveCart();
        } catch (User.UnloadedUserException e) {
            e.printStackTrace();
        }*/
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

    public float getCartTotal() {
        float total = 0;
        for (Pair<Product, Integer> pair: productsInCart){
            total += pair.getKey().getPrice() * pair.getValue();
        }
        return total;
    }

    public int getTotItems() {
        int total = 0;
        for (Pair<Product, Integer> pair: productsInCart){
            total += pair.getValue();
        }
        return total;
    }

    /*public void removeFromCart(Pair<CD, Integer> selezionato) {
        giacenza.remove(selezionato);
    }*/
    /*public void rifornisci(Pair<CD, Integer> prodotto, int quantita) {
        giacenza.remove(prodotto);
        giacenza.add(new Pair<CD, Integer>(prodotto.getKey(), prodotto.getValue()+quantita));
    }*/
}
