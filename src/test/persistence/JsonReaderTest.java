package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import model.Player;
import model.PopulationBuilding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Player player = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlayer() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayer.json");
        try {
            Player player = reader.read();
            assertEquals(0, player.getPopulationCount());
            assertEquals(0, player.getIncome());
            assertEquals(0, player.getTreasury());
            assertEquals(0, player.getPopulationTick());
            assertEquals(0, player.getPopBuildingList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlayer() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayer.json");
        try {
            Player player = reader.read();
            assertEquals(1, player.getPopulationCount());
            assertEquals(2, player.getTreasury());
            assertEquals(3, player.getIncome());
            assertEquals(4, player.getPopulationTick());
            List<PopulationBuilding> popBuildings = player.getPopBuildingList();
            assertEquals(2, popBuildings.size());
            checkPopBuilding("test1", 1, 2, 3, popBuildings.get(0));
            checkPopBuilding("test2", 4, 5, 6, popBuildings.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
