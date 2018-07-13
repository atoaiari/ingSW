package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    //TODO costruisci la classe
    private static String userId;
    private static String name;
    private static String last;
    private static String email;
    private static String psw;
    private static String registrationDate;

    private static boolean bonus;
    private static boolean loaded = false;

    private static User ourInstance = null;

    public static User getInstance() throws UnloadedUserException {
        if (ourInstance == null && loaded) {
            ourInstance = new User();
        }
        // else throw new UnloadedUserException();
        return ourInstance;
    }

    public static void loadUser(String user) throws IOException, ParseException {
        File myUser = new File("data/users/" + user.hashCode() + ".json");

        JSONParser parser = new JSONParser();
        JSONObject u = (JSONObject) parser.parse(new FileReader(myUser));

        userId = (String) u.get("uderId");
        name = (String) u.get("name");
        last = (String) u.get("last");
        email = (String) u.get("email");
        psw = (String) u.get("psw");
        registrationDate = (String) u.get("registrationDate");
        bonus =(boolean) u.get("bonus");
        loaded = true;
    }

    private User() {
    }

    public String getUserMail() {
        return email;
    }

    public String getPsw() {
        return psw;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return last;
    }

    public static class UnloadedUserException extends Exception {
    }
}
