package com.example.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Pokemon {

    private int id;
    private String name;
    private Float weight;
    private Float height;
    private JsonArray stats;
    private JsonObject sprites;
    private JsonObject species;
    private JsonArray types;

    public Pokemon(int id, String name, Float weight, JsonArray stats, JsonObject sprites, JsonObject species, JsonArray types, Float height) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.stats = stats;
        this.sprites = sprites;
        this.species = species;
        this.types = types;
        this.height = height;
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

    public JsonArray getTypess() {
        return types;
    }

    public Float getHeight() {
        return height;
    }

    public Float getStat(String stat) {
        for (int i = 0; i < this.stats.size(); i++) {
            String statName = this.stats.get(i)
                    .getAsJsonObject()
                    .getAsJsonObject("stat")
                    .getAsJsonPrimitive("name")
                    .getAsString();

            if (statName.equals(stat)) {
                return this.stats.get(i)
                        .getAsJsonObject()
                        .getAsJsonPrimitive("base_stat")
                        .getAsFloat();
            }
        }
        return 0.0F;
    }
}
