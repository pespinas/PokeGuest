package pokeApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import java.io.IOException;

import static org.example.Main.fetchPokemonInfo;

public class PokeResponse {
    public static void handlePokemonInfoResponse(String responseData, int gen) {
        JsonObject jsonObject = new Gson().fromJson(responseData, JsonObject.class);
        Pokemon pokemon = Pokemon.fromJson(jsonObject, gen);

        // Accede a los atributos del Pokémon y muestra la información
        System.out.println("Name: " + pokemon.getName());
        System.out.println("Types: " + pokemon.getTypes());
        System.out.println("Generation: " + pokemon.getGen());
    }
    public static void fetchPokemonIdAndRetry(String pokemonName) {
        PokeApiClient.getPokemonId(pokemonName, new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseData, JsonObject.class);
                    int id = jsonObject.get("id").getAsInt();
                    int generation = Pokemon.getGenerationByID(jsonObject);
                    fetchPokemonInfo(String.valueOf(id), true, generation);
                } else {
                    System.out.println("Error fetching ID: " + response.code());
                }
            }
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
