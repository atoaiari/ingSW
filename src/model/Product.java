package model;

import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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


    public Product(File product) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject prod = (JSONObject) parser.parse(new FileReader(product));

        this.id = (String) prod.get("id");
        this.title = (String) prod.get("title");
        this.performer = (String) prod.get("performer");
        this.performers = (String) prod.get("performers");
        this.type = (String) prod.get("type");
        this.category = (String) prod.get("category");
        this.price = (Double) prod.get("price");
        this.img = (String) prod.get("_attachments");
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

    public String getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getPerformer() {
        return performer;
    }

    public String getPrice() {
        return String.valueOf(price);
    }
}
