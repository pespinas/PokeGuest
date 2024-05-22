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

//  Generaciones
    private static final Map<Character, Integer> romanToIntegerMap = new HashMap<>();
    static {
        romanToIntegerMap.put('i', 1);
        romanToIntegerMap.put('v', 5);
        romanToIntegerMap.put('x', 10);
    }

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
        if(types.size() == 1){
            types.add("None");
        }

        return new Pokemon(name, types, gen);
    }
    public static int getGeneration(JsonObject jsonObject){
        JsonObject versionsObject = jsonObject.getAsJsonObject("sprites").getAsJsonObject("versions");

        Set<Map.Entry<String, JsonElement>> entrySet = versionsObject.entrySet();
        String firstVersionObject = null;
        if (!entrySet.isEmpty()) {
            Map.Entry<String, JsonElement> firstEntry = entrySet.iterator().next();
            firstVersionObject = firstEntry.getKey();
        }
        if (firstVersionObject != null) {
            int parseGen = firstVersionObject.lastIndexOf("-");
            String subcadena = firstVersionObject.substring(parseGen + 1);
            int result = 0;
            int prevValue = 0;
            for (int i = subcadena.length() - 1; i >= 0; i--) {
                int value = romanToIntegerMap.get(subcadena.charAt(i));

                if (value < prevValue) {
                    result -= value;
                } else {
                    result += value;
                }
                prevValue = value;
            }
            return result;

        } else {
            return 0;
        }
    }

}
