package com.example.pokemon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    ArrayList<Pokemon> pokemons;
    Context context;

    public PokemonAdapter(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_item, parent, false);

        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.tvName.setText( pokemon.getName() );
        holder.tvWeight.setText( "" + pokemon.getWeight() );
        String urlImage = pokemon.getSprites()
                .getAsJsonObject("other")
                .getAsJsonObject("official-artwork")
                .getAsJsonPrimitive("front_default")
                .getAsString();

        Picasso.get().load(urlImage).into(holder.ivPokeImage);
        changeColorCardView(holder, urlImage);

        holder.cvPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                PokemonDetail pokemonDetail = PokemonDetail.newInstance(pokemon.getId(), holder.cvPokemon.getCardBackgroundColor().getDefaultColor());
                fragmentTransaction.replace(R.id.fragmentContainerView, pokemonDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvWeight;
        final ImageView ivPokeImage;
        final CardView cvPokemon;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvPokeName);
            tvWeight = (TextView) view.findViewById(R.id.tvPokeWeight);
            ivPokeImage = (ImageView) view.findViewById(R.id.pokeImage);
            cvPokemon = (CardView) view.findViewById(R.id.cardPokemon);
        }
    }

    //método para trocar a cor de fundo do CardView.Usados os seguintes exemplos
    //https://stackoverflow.com/questions/20181491/use-picasso-to-get-a-callback-with-a-bitmap
    //https://developer.android.com/training/material/palette-colors
    private void changeColorCardView(ViewHolder holder, String urlImage) {
        Picasso.get()
                .load(urlImage)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette palette = Palette.from(bitmap).generate();
                        int color = palette.getDominantColor( context.getResources().getColor( R.color.purple_200 ) );
                        holder.cvPokemon.setCardBackgroundColor(color);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
