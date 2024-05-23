package pokeApi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;

public class Pokemon {
    //  Valores
    private String name;
    private List<String> types;
    private int gen;


    public Pokemon(String name, List<String> types, int gen) {
        this.name = name;
        this.types = types;
        this.gen = gen;
    }

    public String getName() {
        return name;
    }

    public List<String> getTypes() {
        return types;
    }

    public int getGen() {
        return gen;
    }

    public static Pokemon fromJson(JsonObject jsonObject) {
        String name = jsonObject.get("name").getAsString();
        int gen = getGeneration(jsonObject);

        JsonArray typesArray = jsonObject.getAsJsonArray("types");
        List<String> types = new ArrayList<>();
        for (int i = 0; i < typesArray.size(); i++) {
            JsonObject typeObject = typesArray.get(i).getAsJsonObject();
            String typeName = typeObject.getAsJsonObject("type").get("name").getAsString();
            types.add(typeName);
        }
        if (types.size() == 1) {
            types.add("None");
        }

        return new Pokemon(name, types, gen);
    }

    public static int getGeneration(JsonObject jsonObject) {
        JsonObject gameIndicesArray = jsonObject.getAsJsonObject("generation");
        String versionUrl = gameIndicesArray.get("url").getAsString();
        int lastSlashIndex = versionUrl.lastIndexOf('/');
        String versionNumberString = versionUrl.substring(lastSlashIndex - 1, lastSlashIndex);
        int versionNumber = Integer.parseInt(versionNumberString);

        if (versionNumber >= 1 && versionNumber <= 26) {
            if (versionNumber <= 3) return 1;  // Gen I
            if (versionNumber <= 6) return 2;  // Gen II
            if (versionNumber <= 11) return 3; // Gen III
            if (versionNumber <= 16) return 4; // Gen IV
            if (versionNumber <= 20) return 5; // Gen V
            if (versionNumber <= 24) return 6; // Gen VI
            if (versionNumber <= 26) return 7; // Gen VII
            return 8; // Gen VIII
        } else {
            return 0; // No generation found
        }
    }

}
