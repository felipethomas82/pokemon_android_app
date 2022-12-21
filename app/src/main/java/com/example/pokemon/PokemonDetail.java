package com.example.pokemon;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POKE_ID = "pokeId";
    private static final String ARG_COLOR_CV = "colorCardView";

    // TODO: Rename and change types of parameters
    private int pokeId;
    private int colorCardView;

    public PokemonDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pokeId Parameter 1.
     * @param colorCardView Parameter 2.
     * @return A new instance of fragment PokemonDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static PokemonDetail newInstance(int pokeId, int colorCardView) {
        PokemonDetail fragment = new PokemonDetail();
        Bundle args = new Bundle();
        args.putInt(ARG_POKE_ID, pokeId);
        args.putInt(ARG_COLOR_CV, colorCardView);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokeId = getArguments().getInt(ARG_POKE_ID);
            colorCardView = getArguments().getInt(ARG_COLOR_CV);
        }

        renderPokemon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
    }

    private void renderPokemon() {
        Call<Pokemon> call = RetrofitClient.getInstance().getMyApi().getPoke(pokeId);
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon poke = response.body();
                TextView tvName = getActivity().findViewById(R.id.tvPokeNameDetail);
                TextView tvWeight = getActivity().findViewById(R.id.tvPokeWeightDetail);
                TextView tvHeight = getActivity().findViewById(R.id.tvPokeHeightDetail);
                TextView tvHP = getActivity().findViewById(R.id.tvPokeHP);
                TextView tvAttack = getActivity().findViewById(R.id.tvPokeAttack);
                TextView tvDefense = getActivity().findViewById(R.id.tvPokeDefense);
                TextView tvSpDefense = getActivity().findViewById(R.id.tvPokeSpDefense);
                TextView tvApAttack = getActivity().findViewById(R.id.tvPokeSpAttack);
                TextView tvSpeed = getActivity().findViewById(R.id.tvPokeSpeed);
                ImageView ivPokeImage = getActivity().findViewById(R.id.pokeImageDetail);
                CardView cvPokemon = getActivity().findViewById(R.id.cardPokemonDetail);
                LinearLayout llFakeBackground = getActivity().findViewById(R.id.backFakeCardView);

                cvPokemon.setCardBackgroundColor(colorCardView);
                llFakeBackground.setBackgroundColor(colorCardView);

                tvName.setText(poke.getName());
                tvWeight.setText(poke.getWeight().toString());
                tvHeight.setText(poke.getHeight().toString());
                tvHP.setText(poke.getStat("hp").toString());
                tvAttack.setText(poke.getStat("attack").toString());
                tvDefense.setText(poke.getStat("defense").toString());
                tvSpDefense.setText(poke.getStat("special-defense").toString());
                tvApAttack.setText(poke.getStat("special-attack").toString());
                tvSpeed.setText(poke.getStat("speed").toString());

                String urlImage = poke.getSprites()
                        .getAsJsonObject("other")
                        .getAsJsonObject("official-artwork")
                        .getAsJsonPrimitive("front_default").getAsString();

                Picasso.get().load(urlImage).into(ivPokeImage);

            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.d("TESTE", t.toString());
            }
        });
    }
}