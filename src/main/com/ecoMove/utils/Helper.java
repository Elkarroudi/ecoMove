package main.com.ecoMove.utils;

import java.util.UUID;

public class Helper {

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

}
