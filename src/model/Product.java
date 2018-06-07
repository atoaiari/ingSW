package model;

public class Product implements Comparable{
    //TODO costruisci la classe
    private String id;

    private String title;

    private String category;
    private String description;
    private String insertDate;
    private String tracks;
    private String musicalInstrumentals;
    private String performer;
    private String performers;
    private Double price;
    private String type;
    private String img;


    public Product(String nome){
        id = "" + nome.hashCode();
        title = nome;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public int compareTo(Object o) {
        return id.compareTo(((Product)o).getId());
    }

    private String getId() {
        return id;
    }
}
