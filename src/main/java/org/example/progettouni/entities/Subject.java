package org.example.progettouni.entities;

/**
 * TODO Da ristrutturare in base al modello di database
 */
public class Subject {
    int id_subject;
    String name, category;  //category indica la tipologia di soggettos

    Subject(int id_subject, String name, String category) {
        this.id_subject = id_subject;
        this.name = name;
        this.category = category;
    }
}
