package pokeApi;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.util.Random;

public class PokeApiClient {
    static OkHttpClient client = new OkHttpClient();

    public static void apiCall(String urlP, Callback callback) {
        Request request = new Request.Builder()
                
                .url(urlP)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public static void getPokemonInfo(String poke, Callback callback) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + poke;
        apiCall(url, callback);
    }

    public static void getPokemonId(String poke, Callback callback) {
        String url = "https://pokeapi.co/api/v2/pokemon-species/" + poke;
        apiCall(url, callback);
    }
    public static void getRandomPokemon(Callback callback) {
        Random random = new Random();
        int numR = random.nextInt(1300) + 1;

        String url = "https://pokeapi.co/api/v2/pokemon?limit=1&offset=" + numR;
        apiCall(url, callback);
    }
}
