package org.example.progettouni.entities;

/**
 * TODO Da ristrutturare in base al modello di database
 */
public class Location {
    int id_location;
    float latitude, longitude;
    String name;

    Location(int id_location, float latitude, float longitude, String name) {
        this.id_location = id_location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }
}
