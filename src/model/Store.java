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
            Product myProduct = new Product(document);
            productsSet.add(myProduct);
        }
    }

    private ArrayList<String> getProductsJson() {
        //TODO costruisci arraylist di document di json, leggili da disco
        ArrayList result = new ArrayList<String>();
        result.add("ciao");
        result.add("cacca");
        result.add("andrea");
        result.add("toaiari");
        result.add("vlad");
        result.add("is");
        result.add("slav");
        result.add("gaga");
        result.add("sgdgdg");
        result.add("sggsg");
        result.add("sgsgs");
        result.add("gsgdsg");
        result.add("dsgsdsg");
        result.add("cjds");
        result.add("mkmk");
        result.add("kj");
        result.add("po");
        result.add("jno");
        return result;
    }

    public Set<Product> getProducts(){
        return productsSet;
    }
}
