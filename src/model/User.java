package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private static String userId;
    private static String name;
    private static String last;
    private static String email;
    private static String psw;
    private static String registrationDate;

    private static boolean bonus;
    private static boolean loaded = false;

    private static User ourInstance = null;

    public static User getInstance() {
        if (ourInstance == null && loaded) {
            ourInstance = new User();
        }
        return ourInstance;
    }

    /**
     * Metodo statico che carica un utente se lo trova registrato.
     * @param user indica l'utente.
     */
    public static void loadUser(String user) throws IOException, ParseException {
        File myUser = new File("data/users/" + user.hashCode() + ".json");

        JSONParser parser = new JSONParser();
        JSONObject u = (JSONObject) parser.parse(new FileReader(myUser));

        userId = (String) u.get("userId");
        name = (String) u.get("name");
        last = (String) u.get("last");
        email = (String) u.get("email");
        psw = (String) u.get("psw");
        registrationDate = (String) u.get("registrationDate");
        bonus = (boolean) u.get("bonus");
        loaded = true;
    }

    private User() {
    }

    // metodi get dei vari attributi

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

    public String getID() {
        return userId;
    }
}
