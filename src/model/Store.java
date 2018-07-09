package model;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Store {
    private static Store ourInstance = null;
    private List<Product> productsList;
    private List<Product> iterableProductList;

    public static Store getInstance() {
        if (ourInstance == null) {
            try {
                ourInstance = new Store();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    private Store() throws ParseException, IOException {
        productsList = new ArrayList<>();
        for (File document : getProductsJson()){
            Product myProduct = new Product(document);
            productsList.add(myProduct);
        }
        iterableProductList = new ArrayList<>(productsList);
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

    public List<Product> getProducts(){
        return productsList;
    }

    public List<Product> getFilteredProducts(String searchText, String orderByValue, String genreValue, Boolean CD, Boolean DVD) {
        List<Product> filteredProducts = new ArrayList<>(productsList);

        if(searchText != "") {
            for (Product prod : iterableProductList) {
                if (!(prod.getTitle().toLowerCase().startsWith(searchText.toLowerCase()))) {
                    filteredProducts.remove(prod);
                }
            }
        }

        if(genreValue != "Tutti"){
            for(Product prod:iterableProductList){
                // if(genreValue.compareTo(prod.getGenre()) != 0 && filteredProducts.contains(prod)){
                if(!(prod.getGenre().toLowerCase().contains(genreValue.toLowerCase())) && filteredProducts.contains(prod)){
                    filteredProducts.remove(prod);
                }
            }
        }

        if(!CD && !DVD)
            filteredProducts = null;
        else if(!CD){
            for(Product prod:iterableProductList){
                if(prod.getType().compareTo("CD") == 0 && filteredProducts.contains(prod))
                    filteredProducts.remove(prod);
            }
        }
        else if(!DVD){
            for(Product prod:iterableProductList){
                if(prod.getType().compareTo("DVD") == 0 && filteredProducts.contains(prod))
                    filteredProducts.remove(prod);
            }
        }

        switch (orderByValue) {
            case "Titolo":
                filteredProducts.sort(Comparator.comparing(Product::getTitle));
                break;
            case "Artista":
                filteredProducts.sort(Comparator.comparing(Product::getPerformer));
                break;
            case "Prezzo crescente":
                filteredProducts.sort(Comparator.comparing(Product::getPrice));
                break;
            case "Prezzo descrescente":
                filteredProducts.sort(Comparator.comparing(Product::getNegativePrice));
        }

        return filteredProducts;
    }
}
