package org.example;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pokeApi.PokeApiClient;
import pokeApi.PokeResponse;


import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        fetchPokemonInfo("sceptile", false, 0);
    }
    public static void fetchPokemonInfo(String pokemonNameOrId, boolean retrying, int gen) {
        PokeApiClient.getPokemonInfo(pokemonNameOrId, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    PokeResponse.handlePokemonInfoResponse(response.body().string(), gen);
                } else if (response.code() == 404 && !retrying) {
                    PokeResponse.fetchPokemonIdAndRetry(pokemonNameOrId);
                } else {
                    System.out.println("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}