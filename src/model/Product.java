package model;

import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Product implements Comparable{
    //TODO costruisci la classe
    private String id;

    private String title;

    private String description;
    private String insertDate;
    private String musicalInstruments;
    private String performer;
    private String performers;
    private Double price;
    private String type;
    private String img;
    private String genre;
    private ArrayList<String> tracks;


    public Product(File product) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject prod = (JSONObject) parser.parse(new FileReader(product));

        this.id = (String) prod.get("id");
        this.title = (String) prod.get("title");
        this.performer = (String) prod.get("performer");
        this.performers = (String) prod.get("performers");
        this.type = (String) prod.get("type");
        this.price = (Double) prod.get("price");
        this.img = (String) prod.get("_attachments");
        this.description = (String) prod.get("description");
        this.insertDate = (String) prod.get("insertDate");
        this.musicalInstruments = (String) prod.get("musicalInstruments");
        this.genre = (String) prod.get("category");
        this.tracks = (ArrayList) prod.get("tracks");

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

    public String getPerformer() {
        return performer;
    }


    public String getPerformers() {
        return performers;
    }

    public String getDescription() {
        return description;
    }

    public String getInsertDate() { return insertDate; }

    public String getMusicalInstruments() {
        return musicalInstruments;
    }

    public ArrayList<String> getTracks() {
        return tracks;
    }

    public String getTracksString(){
        String tracksS = "";
        for(String track : tracks){
            tracksS += track + "\n";
        }
        return tracksS;
    }

    public String getGenre() { return genre; }

    public Double getPrice() {
        return price;
    }

    public Double getNegativePrice() {
        return price * (-1);
    }

    /*@Override
    public String toString() {
        return "Articolo: " + title;
    }*/
}
