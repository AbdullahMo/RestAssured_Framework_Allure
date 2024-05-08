package utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    static Faker faker = new Faker();

    public static String generateName(){
        return faker.regexify("Playlist_" +"^[a-zA-Z0-9]{10}$");
    }

    public static String generateDescription(){
        return faker.regexify("Description_" + "^[a-zA-Z0-9]{10}$");
    }

    public static boolean generatePublic(){
        return faker.random().nextBoolean();
    }

    public static String emptyString(){
        return "";
    }
}
