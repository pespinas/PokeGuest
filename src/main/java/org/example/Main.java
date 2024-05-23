package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pokeApi.PokeApiClient;
import pokeApi.Pokemon;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PokeApiClient.getPokemonInfo("gholdengo", new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseData, JsonObject.class);
                    Pokemon pokemon = Pokemon.fromJson(jsonObject);

                    // Accede a los atributos del Pokémon y muestra la información
                    System.out.println("Name: " + pokemon.getName());
                    System.out.println("Types: " + pokemon.getTypes());
                    System.out.println("Generation: " + pokemon.getGen());
                } else {
                    System.out.println("Error: " + response.code());
                }
            }
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}