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

    public static Store getInstance() throws ParseException, IOException, JSONException {
        if (ourInstance == null)
            ourInstance = new Store();
        return ourInstance;
    }

    private Store() throws JSONException, ParseException, IOException {
        productsSet = new TreeSet<>();
        for (File document : getProductsJson()){
            Product myProduct = new Product(document);
            productsSet.add(myProduct);
        }
    }

    private ArrayList<File> getProductsJson() {
        //TODO costruisci arraylist di document di json, leggili da disco

//        ArrayList result = new ArrayList<String>();
//        result.add("ciao");
//        result.add("cacca");
//        result.add("andrea");
//        result.add("toaiari");
//        result.add("vlad");
//        result.add("is");
//        result.add("slav");
//        result.add("gaga");
//        result.add("sgdgdg");
//        result.add("sggsg");
//        result.add("sgsgs");
//        result.add("gsgdsg");
//        result.add("dsgsdsg");
//        result.add("cjds");
//        result.add("mkmk");
//        result.add("kj");
//        result.add("po");
//        result.add("jno");

        File myDirectory = new File("data/products");
        File[] files = myDirectory.listFiles();
        ArrayList real_files = new ArrayList<File>();

        for (File file : files){
            if (file.isFile()) {
                System.out.println("" + file.getName());
                real_files.add(file);
            }
        }

        return real_files;
        //return result;
    }

    public Set<Product> getProducts(){
        return productsSet;
    }
}
