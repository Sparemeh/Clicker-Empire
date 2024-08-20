package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import model.Player;
import model.PopulationBuilding;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            Player player = new Player();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPlayer() {
        try {
            Player player = new Player();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayer.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayer.json");
            player = reader.read();
            assertEquals(0, player.getPopulationCount());
            assertEquals(0, player.getIncome());
            assertEquals(0, player.getTreasury());
            assertEquals(0, player.getPopulationTick());
            assertEquals(0, player.getPopBuildingList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlayer() {
        try {
            Player player = new Player(1, 2, 3, 4);
            player.addPopBuilding(new PopulationBuilding("test1", 1, 2, 3));
            player.addPopBuilding(new PopulationBuilding("test2", 4, 5, 6));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlayer.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlayer.json");
            player = reader.read();
            assertEquals(1, player.getPopulationCount());
            assertEquals(2, player.getTreasury());
            assertEquals(3, player.getIncome());
            assertEquals(4, player.getPopulationTick());
            List<PopulationBuilding> popBuildings = player.getPopBuildingList();
            assertEquals(2, popBuildings.size());
            checkPopBuilding("test1", 1, 2, 3, popBuildings.get(0));
            checkPopBuilding("test2", 4, 5, 6, popBuildings.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
