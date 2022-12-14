package com.example.pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    @GET("{pokeId}")
    Call<Pokemon> getPoke(@Path("pokeId") int pokeId);

}
