package model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Store {
    private static Store ourInstance = null;
    private Set<Product> productsSet;

    public static Store getInstance() {
        if (ourInstance == null)
            ourInstance = new Store();
        return ourInstance;
    }

    private Store() {
        productsSet = new TreeSet<>();
        for (String document : getProductsJson()){
            // if json.type == 'CD' else ...
            Product myProduct = new Product(document);
            productsSet.add(myProduct);
        }
    }

    private ArrayList<String> getProductsJson() {
        //TODO costruisci arraylist di document di json, leggili da disco
        ArrayList result = new ArrayList<String>();
        result.add("ciao");
        result.add("cacca");
        return result;
    }

    public Set<Product> getProducts(){
        return productsSet;
    }
}
