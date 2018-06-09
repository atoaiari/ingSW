package model;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Store {
    private static Store ourInstance = null;
    private Set<Product> productsSet;

    public static Store getInstance() throws ParseException, IOException {
        if (ourInstance == null)
            ourInstance = new Store();
        return ourInstance;
    }

    private Store() throws ParseException, IOException {
        productsSet = new TreeSet<>();
        for (File document : getProductsJson()){
            Product myProduct = new Product(document);
            productsSet.add(myProduct);
        }
    }

    private ArrayList<File> getProductsJson() {
        //TODO costruisci arraylist di document di json, leggili da disco

        File myDirectory = new File("data/products");
        File[] files = myDirectory.listFiles();
        ArrayList real_files = new ArrayList<File>();

        for (File file : files){
            if (file.isFile()) {
                real_files.add(file);
            }
        }

        return real_files;
    }

    public Set<Product> getProducts(){
        return productsSet;
    }
}
