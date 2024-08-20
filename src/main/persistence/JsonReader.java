package persistence;

import model.Player;
import model.PopulationBuilding;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Reads JSON data on file and parses the player, referencing JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        int popCount = jsonObject.getInt("popCount");
        int treasury = jsonObject.getInt("treasury");
        int income = jsonObject.getInt("income");
        int popPerTick = jsonObject.getInt("popTick");
        Player player = new Player(popCount, treasury, income, popPerTick);
        addBuildings(player, jsonObject);

        return player;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // MODIFIES: player
    // EFFECTS: parses popBuildings from JSON object and adds them to Player
    private void addBuildings(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("popBuilding");
        for (Object json : jsonArray) {
            JSONObject nextBuilding = (JSONObject) json;
            addBuilding(player, nextBuilding);
        }
    }

    // MODIFIES: player
    // EFFECTS: parses popBuilding from JSON object and adds it to Player
    private void addBuilding(Player player, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int price = jsonObject.getInt("price");
        int value = jsonObject.getInt("value");
        int popPerTick = jsonObject.getInt("popPerTick");
        PopulationBuilding building = new PopulationBuilding(name, price, popPerTick, value);
        player.addPopBuilding(building);
    }
}
