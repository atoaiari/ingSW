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

    // metodo statico per classe singleton
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

    /**
     * Costruttore che carica tutti i prodotti da file.
     */
    private Store() throws ParseException, IOException {
        productsList = new ArrayList<>();
        for (File document : getProductsJson()){
            Product myProduct = new Product(document);
            productsList.add(myProduct);
        }
        iterableProductList = new ArrayList<>(productsList);
    }

    /**
     * Metodo che cerca i file nella cartella products.
     * @return real_files file json contenenti i prodotti.
     */
    private ArrayList<File> getProductsJson() {
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

    /**
     * Metodo che restituisce la lista dei prodotti.
     * @return productsList lista dei prodotti.
     */
    public List<Product> getProducts(){
        return productsList;
    }

    /**
     * Metodo che riceve in input vari parametri di filtraggio e restituisce la lista dei prodotti filtrata.
     * @return filteredProducts lista dei prodotti filtrati.
     */
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

        // ordinamento con compare
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
