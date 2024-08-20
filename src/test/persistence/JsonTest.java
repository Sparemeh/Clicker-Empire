package persistence;

import model.PopulationBuilding;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPopBuilding(String name, int price, int popPerTick, int value, PopulationBuilding building) {
        assertEquals(name, building.getName());
        assertEquals(price, building.getPrice());
        assertEquals(popPerTick, building.getPopPerTick());
        assertEquals(value, building.getValue());
    }
}
