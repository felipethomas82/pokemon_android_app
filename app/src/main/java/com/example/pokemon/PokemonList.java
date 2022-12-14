package com.example.pokemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokemonList extends Fragment {

    ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
    RecyclerView rvPokemons;
    PokemonAdapter pokemonAdapter;

    public PokemonList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        rvPokemons = getActivity().findViewById(R.id.rvPokemons);
        pokemonAdapter = new PokemonAdapter(pokemons);
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPokemons.setLayoutManager(layout);
        rvPokemons.setAdapter(pokemonAdapter);

        getPokemonsFromApi();
    }

    private void getPokemonsFromApi() {

        for (int i=0; i<10; i++) {
            int pokeId = generateRandom();
            Call<Pokemon> call = RetrofitClient.getInstance().getMyApi().getPoke(pokeId);
            call.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    Pokemon poke = response.body();
                    pokemons.add(poke);
                    pokemonAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Pokemon> call, Throwable t) {
                    Log.d("TESTE", t.toString());
                }
            });
        }

    }

    private int generateRandom() {
        int min = 0;
        int max = 800;

        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}