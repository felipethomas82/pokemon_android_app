package com.example.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Pokemon {

    private int id;
    private String name;
    private Float weight;
    private JsonArray stats;
    private JsonObject sprites;
    private JsonObject species;

    public Pokemon(int id, String name, Float weight, JsonArray stats, JsonObject sprites, JsonObject species) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.stats = stats;
        this.sprites = sprites;
        this.species = species;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getWeight() {
        return weight;
    }

    public JsonArray getStats() {
        return stats;
    }

    public JsonObject getSprites() {
        return sprites;
    }

    public JsonObject getSpecies() {
        return species;
    }
}
