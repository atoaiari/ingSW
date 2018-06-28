package model;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Store {
    private static Store ourInstance = null;
    // private Set<Product> productsSet;
    private List<Product> productsList;

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

//    public Set<Product> getProducts(){
//        return productsSet;
//    }
    public List<Product> getProducts(){
        return productsList;
    }

//    public List<Product> getFilteredProducts(String text) {
//        if(text == "")
//            actualProductList = productsList;
//
//        List<Product> filteredProducts = new ArrayList<>();
//        for(Product prod:actualProductList){
//            if(prod.getTitle().toLowerCase().startsWith(text.toLowerCase()) || text == ""){
//                filteredProducts.add(prod);
//            }
//        }
//
//        actualProductList = filteredProducts;
//        filteredProducts.sort(Comparator.comparing(Product::getTitle));
//        return filteredProducts;
//    }
//
//    public List<Product> getGenreFilteredProducts(String value) {
//        if(value == "Tutti")
//            actualProductList = productsList;
//
//        List<Product> genreFilteredProducts = new ArrayList<>();
//        for(Product prod:actualProductList){
//            if(value.compareTo(prod.getGenre()) == 0 || value == "Tutti"){
//                genreFilteredProducts.add(prod);
//            }
//        }
//
//        actualProductList = genreFilteredProducts;
//        genreFilteredProducts.sort(Comparator.comparing(Product::getTitle));
//        return genreFilteredProducts;
//    }

    public List<Product> getFilteredProducts(String searchText, String orderByValue, String genreValue) {
        List<Product> filteredProducts = productsList;

        System.out.println("Inizio filtraggio");
        for(Product p:filteredProducts)
            System.out.println(p);

        for(Product prod:productsList){
            if(!(prod.getTitle().toLowerCase().startsWith(searchText.toLowerCase()))){
                filteredProducts.remove(prod);
            }
        }

        System.out.println("Dopo search");
        for(Product p:filteredProducts)
            System.out.println(p);

        if(genreValue != "Tutti"){
            for(Product prod:productsList){
                if(genreValue.compareTo(prod.getGenre()) != 0 && filteredProducts.contains(prod)){
                    filteredProducts.remove(prod);
                }
            }
        }

        System.out.println("Dopo genere");
        for(Product p:filteredProducts)
            System.out.println(p);

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
